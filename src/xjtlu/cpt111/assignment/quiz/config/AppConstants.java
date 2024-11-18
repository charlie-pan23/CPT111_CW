package xjtlu.cpt111.assignment.quiz.config;

public interface AppConstants {
    // Fields
    /**
     * Whether the application is currently in debug mode.
     */
    static final boolean IS_DEBUG = true;

    /**
     * Whether the application is currently in verbose mode.
     */
    static final boolean IS_VERBOSE = false;

    /**
     * String used to indent question options.
     */
    static final String QUESTION_OPTION_INDENTATOR = "    ";

    /**
     * Whether an exception should be thrown in case of a problem in the question.
     */
    static final boolean THROW_INVALID_EXCEPTION = true;

    // Nested interface for document object model, if needed
    static interface Model {
        // Additional fields or methods specific to document models
    }
}
