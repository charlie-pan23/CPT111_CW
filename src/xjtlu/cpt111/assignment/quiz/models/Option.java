package xjtlu.cpt111.assignment.quiz.models;

public class Option {
    // Fields
    private String answer;
    private boolean isCorrectAnswer;

    // Constructors
    /**
     * Construct an option with the answer string provided.
     *
     * @param answer answer string
     */
    public Option(String answer) {
        this.answer = answer;
        this.isCorrectAnswer = false; // default value
    }

    /**
     * Construct an option with the answer string and correctness flag provided.
     *
     * @param answer answer string
     * @param isCorrectAnswer true if the option is a correct answer; false otherwise
     */
    public Option(String answer, boolean isCorrectAnswer) {
        this.answer = answer;
        this.isCorrectAnswer = isCorrectAnswer;
    }

    // Static factory method
    /**
     * Create a new instance of an option with the information specified.
     *
     * @param answer answer string
     * @param isCorrectAnswer true if the option is a correct answer; false otherwise
     * @return an instance of option with the information provided
     */
    public static final Option newInstance(String answer, boolean isCorrectAnswer) {
        return new Option(answer, isCorrectAnswer);
    }

    // Getter methods
    /**
     * Return the option answer as a string.
     *
     * @return option answer string
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Return whether the current option is a correct answer.
     *
     * @return true if the option is a correct answer; false otherwise
     */
    public boolean isCorrectAnswer() {
        return isCorrectAnswer;
    }

    // Setter methods
    /**
     * Set the option answer.
     *
     * @param answer option answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Set whether the current option is a correct answer.
     *
     * @param isCorrectAnswer whether the current option is a correct answer
     */
    public void setCorrectAnswer(boolean isCorrectAnswer) {
        this.isCorrectAnswer = isCorrectAnswer;
    }

    // toString methods
    /**
     * A string representation of the option.
     *
     * @return String representation of the option
     */
    @Override
    public String toString() {
        return answer + (isCorrectAnswer ? " (Correct)" : "");
    }

    /**
     * A string representation of the option with a prefix.
     *
     * @param prefix prefix string to be added
     * @return String representation of the option
     */
    public String toString(String prefix) {
        return prefix + toString();
    }
}
