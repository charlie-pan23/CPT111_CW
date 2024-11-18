package xjtlu.cpt111.assignment.quiz.models;
import xjtlu.cpt111.assignment.quiz.config.AppConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Question implements AppConstants {
    // Fields
    private String topic;
    private String questionStatement;
    private List<Option> options;
    private Difficulty difficulty;
    public static final Difficulty DEFAULT_DIFFICULTY = Difficulty.MEDIUM; // 假设默认难度为 MEDIUM

    // Constructors
    /**
     * Construct a question with the topic, question statement, and options array.
     */
    public Question(String topic, String questionStatement, Option... options) {
        this(topic, DEFAULT_DIFFICULTY, questionStatement, options);
    }

    /**
     * Construct a question with the topic, question statement, and options collection.
     */
    public Question(String topic, String questionStatement, Collection<Option> options) {
        this(topic, DEFAULT_DIFFICULTY, questionStatement, options);
    }

    /**
     * Construct a question with the topic, difficulty, question statement, and options array.
     */
    public Question(String topic, Difficulty difficulty, String questionStatement, Option... options) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.questionStatement = questionStatement;
        this.options = new ArrayList<>();
        for (Option option : options) {
            this.options.add(option);
        }
    }

    /**
     * Construct a question with the topic, difficulty, question statement, and options collection.
     */
    public Question(String topic, Difficulty difficulty, String questionStatement, Collection<Option> options) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.questionStatement = questionStatement;
        this.options = new ArrayList<>(options);
    }

    // Static factory methods
    public static final Question newInstance(String topic, Difficulty difficulty, String questionStatement, Option... options) {
        return new Question(topic, difficulty, questionStatement, options);
    }

    public static final Question newInstance(String topic, Difficulty difficulty, String questionStatement, Collection<Option> options) {
        return new Question(topic, difficulty, questionStatement, options);
    }

    // Getter and Setter methods
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuestionStatement() {
        return questionStatement;
    }

    public void setQuestionStatement(String questionStatement) {
        this.questionStatement = questionStatement;
    }

    public Option[] getOptions() {
        return options.toArray(new Option[0]);
    }

    public void setOptions(Collection<Option> options) {
        this.options = new ArrayList<>(options);
    }

    public void addOption(Option option) {
        options.add(option);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    // toString methods
    @Override
    public String toString() {
        return toString("", false);
    }

    public String toString(String prefix, boolean showErrorMessage) {
        StringBuilder sb = new StringBuilder(prefix + "Topic: " + topic + "\n" + prefix + "Question: " + questionStatement + "\n");
        for (int i = 0; i < options.size(); i++) {
            sb.append(prefix).append(AppConstants.QUESTION_OPTION_INDENTATOR).append("Option ").append(i + 1).append(": ").append(options.get(i)).append("\n");
        }
        if (showErrorMessage) {
            sb.append(prefix).append("Error: Invalid question format.\n");
        }
        return sb.toString();
    }
}
