package xjtlu.cpt111.assignment.quiz;
import xjtlu.cpt111.assignment.quiz.models.Difficulty;
import xjtlu.cpt111.assignment.quiz.models.Option;
import xjtlu.cpt111.assignment.quiz.models.Question;
import xjtlu.cpt111.assignment.quiz.utils.IOUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class questionBank {
    private static List<Integer> indexes = new ArrayList<>();
    private static int index = 0;
    private final Question[] allQuestions;
    private String[] topics;

    public questionBank() {
        //read all question from
        String directoryPath = "resources/questionsBank";
        try {
            allQuestions = IOUtilities.readQuestions(directoryPath);
            extractTopics();
        } catch (Exception e) {
            //throw IO error
            throw new RuntimeException(e);
        }
    }
    //get topic
    private void extractTopics() {
        List<String> topicList = new ArrayList<>();
        //traversal all questions to get topic
        for (Question question : allQuestions) {
            String topic = question.getTopic();
            if (!topicList.contains(topic)) {
                topicList.add(topic);
            }
        }
        topics = topicList.toArray(new String[0]); //turn to array
    }
    //Get a group of legal questions on a specific topic
    private static Question[] getSpecificTopic(Question[] questions, String topic) {
        List<Question> filteredQuestions = new ArrayList<>();
        //traversal all the question from input
        for (Question question : questions) {
            int correctOptionNum = 0;
            Option[] options = question.getOptions();
            //traversal all options from this problem
            for (Option option : options) {
                //Determine how many are correct and count
                if (option.isCorrectAnswer()){
                    correctOptionNum++;
                }
            }
            //Check whether it matches the topic and the number of options is greater than 1 and there is only one correct number of options and all part of a question exist.
            if (question.getTopic().equals(topic) && options.length>1 && correctOptionNum==1 && question.getQuestionStatement()!=null && question.getTopic()!=null && question.getDifficulty()!=null) {
                //Add legal questions to the problem group
                filteredQuestions.add(question);
            }
        }
        return filteredQuestions.toArray(new Question[0]);
    }
    //get specific difficulty question
    private static Question getSpecificDifficulty(Question[] questions, Difficulty difficulty) {
        List<Question> filteredQuestions = new ArrayList<>();
        for (Question question : questions) {
            if (question.getDifficulty().equals(difficulty)) {
                filteredQuestions.add(question);
            }
        }
        //disorganize indexes
        if (index == 0) {
            indexes = new ArrayList<>();
            for (int i = 0; i < filteredQuestions.size(); i++) {
                indexes.add(i);
            }
            Collections.shuffle(indexes);
        }
        //reload indexes
        if (index < indexes.size()) {
            int randomIndex = indexes.get(index);
            return filteredQuestions.get(randomIndex);
        } else {
            index = 0;
            Collections.shuffle(indexes);
            return getSpecificDifficulty(questions, difficulty);
        }
    }

    public Question[] getAllQuestions() {
        return allQuestions;
    }

    public String[] getTopics() {
        return topics;
    }

    public Question[] getQuestions(String topic, int questionNum) {
        questionBank questionBank = new questionBank();
        Question[] allQuestions = questionBank.getAllQuestions();
        Question[] specificTopicQuestions = getSpecificTopic(allQuestions, topic);
        System.out.printf("%d legal question\n",specificTopicQuestions.length);
        //get final questions
        if (specificTopicQuestions.length < questionNum) {
            return specificTopicQuestions;
        } else {
            Question[] finalQuestions = new Question[questionNum];
            int questionsAssigned = 0;
            // Assign each difficulty to an equal number of problems
            for (Difficulty difficulty : Difficulty.values()) {
                int baseCount = questionNum / Difficulty.values().length;
                for (int i = 0; i < baseCount && questionsAssigned < questionNum; i++) {
                    finalQuestions[questionsAssigned++] = getSpecificDifficulty(specificTopicQuestions, difficulty);
                }
            }

            // Randomly fill in random difficulty questions for questions can not be divided equally
            int remainingQuestions = questionNum - questionsAssigned;
            for (int i = 0; i < remainingQuestions; i++) {
                Difficulty randomDifficulty = Difficulty.values()[(int) (Math.random() * Difficulty.values().length)];
                finalQuestions[questionsAssigned++] = getSpecificDifficulty(specificTopicQuestions, randomDifficulty);
            }

            // disorganize the final array of questions
            List<Question> finalQuestionsList = new ArrayList<>();
            for (Question q : finalQuestions) {
                finalQuestionsList.add(q);
            }
            Collections.shuffle(finalQuestionsList);

            // turn list to array
            for (int i = 0; i < finalQuestions.length; i++) {
                finalQuestions[i] = finalQuestionsList.get(i);
            }


            return finalQuestions;
        }
    }


    public boolean isUserAnswerCorrect(String userAnswer, Question question) {
        try {
            //creat indexes
            int index = Integer.parseInt(userAnswer) - 1;
            Option[] options = question.getOptions();
            //check input
            if (index < 0 || index >= options.length) {
                System.out.println("Invalid answer. Please enter a number between 1 and " + options.length);
                throw new RuntimeException("Invalid answer.");
            }
            // check whether the user answer correct
            return options[index].isCorrectAnswer();
            //throw err
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}