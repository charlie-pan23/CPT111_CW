package xjtlu.cpt111.assignment.quiz;

import xjtlu.cpt111.assignment.quiz.model.Question;
import xjtlu.cpt111.assignment.quiz.util.IOUtilities;

public class ReadQuestions {

	private static final String RESOURCES_PATH = "src/main/resources/";
	private static final String QUESTIONS_BANK_PATH = "resources/questionsBank/";
	private static final String EMPTY_FOLDER = "resources/questionsBank/emptyFolder";

	public static void main(String... arguments) {
		String filename = RESOURCES_PATH + "questions.xml";
		filename = QUESTIONS_BANK_PATH + "questions.xml";
		// filename = RESOURCES_PATH;
		filename = QUESTIONS_BANK_PATH;
		// filename = EMPTY_FOLDER;

		try {
			System.out.println("===\n=== read questions - started\n===");
			Question[] questions = IOUtilities.readQuestions(filename);
			if (null == questions || questions.length == 0) {
				System.out.println("Questions is empty!");
			} else {
				int numQuestions = 0;
				for (Question question : questions) {
					System.out.println("Question #" + (++numQuestions) + " " + question.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("===\n=== read questions - complete\n===");
		}

	}

}
