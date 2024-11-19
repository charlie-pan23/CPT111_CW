package xjtlu.cpt111.assignment.quiz;

import xjtlu.cpt111.assignment.quiz.models.Difficulty;
import xjtlu.cpt111.assignment.quiz.models.Question;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        questionBank qp = new questionBank();
        Question[] questions = qp.getQuestions("Computer Science", 4);

        String[] topList = qp.getTopics();
        for (int i = 0; i < topList.length; i++) {
            System.out.println(topList[i]);
        }
        System.out.println(Arrays.toString(questions));
    }

}
