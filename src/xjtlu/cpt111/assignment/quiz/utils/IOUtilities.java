package xjtlu.cpt111.assignment.quiz.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import xjtlu.cpt111.assignment.quiz.config.AppConstants;
import xjtlu.cpt111.assignment.quiz.config.XmlInputStream;
import xjtlu.cpt111.assignment.quiz.models.Difficulty;
import xjtlu.cpt111.assignment.quiz.models.Option;
import xjtlu.cpt111.assignment.quiz.models.Question;

public class IOUtilities implements AppConstants {
    private static final IOUtilities INSTANCE = new IOUtilities();
    private static final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

    private static final void flushOutput() {
        System.out.flush();
        System.err.flush();
    }

    public static Question[] readQuestions(String path) {
        if (null != path && !path.isBlank()) {
            return readQuestions(Paths.get(path));
        } else {
            throw new IllegalArgumentException("Input path is null or empty!");
        }
    }

    public static Question[] readQuestions(File file) {
        if (null == file) {
            throw new IllegalArgumentException("Input file is null!");
        } else {
            return readQuestions(file.toPath());
        }
    }

    public static Question[] readQuestions(Path path) {
        if (null == path) {
            throw new IllegalArgumentException("Input path is null!");
        } else {
            return INSTANCE.readQuestionsFromXml(path);
        }
    }

    private IOUtilities() {
    }

    private Question[] readQuestionsFromXml(Path path) {
        if (!Files.exists(path, new LinkOption[0])) {
            logErrorMessage("Path \"" + path + "\" does not exist!");
            return null;
        } else {
            List<Path> filesToRead = new ArrayList<>();
            if (Files.isDirectory(path, new LinkOption[0])) {
                try {
                    PathMatcher xmlFileMatcher = path.getFileSystem().getPathMatcher("glob:**/*.xml");
                    Stream<Path> stream = Files.list(path).filter(Files::isRegularFile);
                    filesToRead = stream.filter(xmlFileMatcher::matches).collect(Collectors.toList());
                } catch (IOException e) {
                    flushOutput();
                    e.printStackTrace();
                }
            } else if (Files.isRegularFile(path, new LinkOption[0])) {
                filesToRead.add(path);
            }

            if (filesToRead.isEmpty()) {
                logErrorMessage("NO files to read!");
                return null;
            } else {
                List<Question> questions = new ArrayList<>();
                for (Path p : filesToRead) {
                    logMeesage("read questions from file \"" + p + "\"");

                    try (InputStream ins = Files.newInputStream(p)) {
                        List<Question> newQuestions = readQuestionsFromXml(ins);
                        if (newQuestions != null && !newQuestions.isEmpty()) {
                            logMeesage("-- " + newQuestions.size() + " new questions are found!");
                            questions.addAll(newQuestions);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return questions.toArray(new Question[0]);
            }
        }
    }

    private List<Question> readQuestionsFromXml(InputStream ins) throws XMLStreamException {
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new XmlInputStream(ins));
        List<Question> newQuestions = new ArrayList<>();
        String topic = null;
        Difficulty difficulty = Question.DEFAULT_DIFFICULTY;
        String questionString = null;
        List<Option> options = null;

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "question":
                        topic = null;
                        difficulty = Question.DEFAULT_DIFFICULTY;
                        questionString = null;
                        options = new ArrayList<>();
                        Attribute questionDifficulty = startElement.getAttributeByName(Model.TAG_DIFFICULTY);
                        if (questionDifficulty != null) {
                            try {
                                difficulty = Difficulty.valueOf(questionDifficulty.getValue().toUpperCase());
                            } catch (Exception ignored) {
                            }
                        }
                        break;
                    case "topic":
                        topic = reader.nextEvent().asCharacters().getData();
                        break;
                    case "questionString":
                        questionString = reader.nextEvent().asCharacters().getData();
                        break;
                    case "option":
                        String optionStr = reader.nextEvent().asCharacters().getData();
                        Attribute correctAnswer = startElement.getAttributeByName(Model.TAG_ANSWER);
                        boolean isCorrectAnswer = "true".equalsIgnoreCase(correctAnswer != null ? correctAnswer.getValue() : "false");
                        Option option = Option.newInstance(optionStr, isCorrectAnswer);
                        options.add(option);
                        break;
                }
            } else if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if ("question".equals(endElement.getName().getLocalPart())) {
                    Question newQuestion = Question.newInstance(topic, difficulty, questionString, options);
                    newQuestions.add(newQuestion);
                }
            }
        }

        return newQuestions.isEmpty() ? null : newQuestions;
    }

    private void logMeesage(String message) {
        flushOutput();
        System.out.println(message);
        flushOutput();
    }

    private void logErrorMessage(String message) {
        flushOutput();
        System.err.println(message);
        flushOutput();
    }
}