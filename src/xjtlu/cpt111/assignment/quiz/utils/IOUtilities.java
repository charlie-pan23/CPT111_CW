package xjtlu.cpt111.assignment.quiz.utils;

import xjtlu.cpt111.assignment.quiz.models.Difficulty;
import xjtlu.cpt111.assignment.quiz.models.Option;
import xjtlu.cpt111.assignment.quiz.models.Question;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.io.File;
import java.util.ArrayList;

public class IOUtilities {
    public static Question[] readQuestions(String directoryPath) {
        System.out.println("Reading questions from directory: " + directoryPath);
        File directory = new File(directoryPath);
        List<Question> questionList = new ArrayList<>();

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("The provided path is not a directory: " + directoryPath);
        }

        // 遍历目录下的所有文件
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
        if (files != null) {
            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    String topic = "";
                    String questionText = "";
                    String difficulty = "";
                    List<String> options = new ArrayList<>();
                    int correctOptionIndex = -1;
                    int optionIndex = 0;

                    while ((line = reader.readLine()) != null) {
                        line = line.trim();
                        if (line.startsWith("<topic>")) {
                            topic = line.substring(7, line.length() - 8);
                        } else if (line.startsWith("<questionString>")) {
                            questionText = line.substring(16, line.length() - 17);
                        } else if (line.startsWith("<option")) {
                            boolean isCorrect = line.contains("answer=\"true\"");
                            String optionText = line.replaceAll("<.*?>", "").trim();
                            options.add(optionText);
                            if (isCorrect) {
                                correctOptionIndex = optionIndex;
                            }
                            optionIndex++;
                        } else if (line.startsWith("</question>")) {
                            // 将 difficulty 转换为 Difficulty 枚举
                            Difficulty questionDifficulty = Difficulty.valueOf(difficulty.toUpperCase());

                            // 将字符串选项转换为 Option 对象列表
                            List<Option> optionList = new ArrayList<>();
                            for (int i = 0; i < options.size(); i++) {
                                boolean isCorrect = (i == correctOptionIndex);
                                optionList.add(new Option(options.get(i), isCorrect));
                            }

                            // 使用新的参数类型创建 Question 对象
                            Question question = new Question(topic, questionDifficulty, questionText, optionList);
                            questionList.add(question);

                            // 清空选项列表和索引
                            options.clear();
                            optionIndex = 0;
                        } else if (line.startsWith("<question difficulty=")) {
                            difficulty = line.split("\"")[1];
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error reading questions from file: " + file.getName(), e);
                }
            }
        }

        return questionList.toArray(new Question[0]);
    }

}