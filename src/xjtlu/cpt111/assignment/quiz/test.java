package xjtlu.cpt111.assignment.quiz;
import xjtlu.cpt111.assignment.quiz.models.Question;

import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        questionBank qp = new questionBank();
        Question[] questions = qp.getQuestions("Computer Science", 8);
        //具体实现例子
        for (int a = 0; a < 5; a++) {


            for (int i = 0; i < questions.length; i++) {
                try {
                    Question firstQuestion = questions[i];
                    System.out.println(firstQuestion);

                    System.out.print("Enter your answer: ");
                    System.out.println(i);
//                Scanner scanner = new Scanner(System.in);
//                String userAnswer = scanner.nextLine();
//                boolean isCorrect = qp.isUserAnswerCorrect(userAnswer, firstQuestion);
//                if (isCorrect) {
//                    System.out.println("Correct!");
//                } else {
//                    System.out.println("Incorrect!");
                    //}
                } catch (Exception e) {
                    System.out.println("Invalid answer. Please try again.");
                    i--;
                }
            }
        }
    }

}
