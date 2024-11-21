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
        //构造读取
        String directoryPath = "resources/questionsBank/";
        try {
            allQuestions = IOUtilities.readQuestions(directoryPath);
            extractTopics();
        } catch (Exception e) {
            //IO错误抛出
            throw new RuntimeException(e);
        }
    }
    //获取主题
    private void extractTopics() {
        List<String> topicList = new ArrayList<>();
        //遍历问题获取主题
        for (Question question : allQuestions) {
            String topic = question.getTopic();
            if (!topicList.contains(topic)) {
                topicList.add(topic);
            }
        }
        topics = topicList.toArray(new String[0]); // 将列表转换为数组
    }
    //获取特定主题问题组
    public static Question[] getSpecificTopic(Question[] questions, String topic) {
        List<Question> filteredQuestions = new ArrayList<>();
        for (Question question : questions) {
            if (question.getTopic().equals(topic)) {
                filteredQuestions.add(question);
            }
        }
        return filteredQuestions.toArray(new Question[0]);
    }
    //获取特定难度问题
    public static Question getSpecificDifficulty(Question[] questions, Difficulty difficulty) {
        List<Question> filteredQuestions = new ArrayList<>();
        for (Question question : questions) {
            if (question.getDifficulty().equals(difficulty)) {
                filteredQuestions.add(question);
            }
        }
        //打乱索引
        if (index == 0) {
            indexes = new ArrayList<>();
            for (int i = 0; i < filteredQuestions.size(); i++) {
                indexes.add(i);
            }
            Collections.shuffle(indexes);
        }
        //重置索引
        if (index < indexes.size()) {
            int randomIndex = indexes.get(index++);
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
        //获取最终问题组
        if (specificTopicQuestions.length < questionNum) {
            return specificTopicQuestions;
        } else {
            Question[] finalQuestions = new Question[questionNum];
            int questionsAssigned = 0;

            // 分配每个难度可平分数量的问题
            for (Difficulty difficulty : Difficulty.values()) {
                int baseCount = questionNum / Difficulty.values().length;
                for (int i = 0; i < baseCount && questionsAssigned < questionNum; i++) {
                    finalQuestions[questionsAssigned++] = getSpecificDifficulty(specificTopicQuestions, difficulty);
                }
            }

            // 随机填入随机难度问题
            int remainingQuestions = questionNum - questionsAssigned;
            for (int i = 0; i < remainingQuestions; i++) {
                Difficulty randomDifficulty = Difficulty.values()[(int) (Math.random() * Difficulty.values().length)];
                finalQuestions[questionsAssigned++] = getSpecificDifficulty(specificTopicQuestions, randomDifficulty);
            }

            // 打乱最终的问题数组
            List<Question> finalQuestionsList = new ArrayList<>();
            for (Question q : finalQuestions) {
                finalQuestionsList.add(q);
            }
            Collections.shuffle(finalQuestionsList);

            // 将打乱后的List转回数组
            for (int i = 0; i < finalQuestions.length; i++) {
                finalQuestions[i] = finalQuestionsList.get(i);
            }


            return finalQuestions;
        }
    }

    public boolean isUserAnswerCorrect(String userAnswer, Question question) {
        try {
            //创建索引
            int index = Integer.parseInt(userAnswer) - 1;
            Option[] options = question.getOptions();
            //确认输入
            if (index < 0 || index >= options.length) {
                System.out.println("Invalid answer. Please enter a number between 1 and " + options.length);
                throw new RuntimeException("Invalid answer.");
            }
            // 检查用户选择的选项是否正确
            return options[index].isCorrectAnswer();
        //抛出错误
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}