package xjtlu.cpt111.assignment.quiz;
import java.io.IOException;

import xjtlu.cpt111.assignment.quiz.utils.userBank;
public class testmain {
    public static void main(String[] args) {
        String[] topic_list = { "cs", "ee", "english", "mathematics" };
        try{
            userBank userBank_test = new userBank(topic_list);
            userBank_test.write_user("lx","12345");
            System.out.println(userBank_test.check_user("kanae"));
            System.out.println(userBank_test.check_user("kanae","12345"));
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }    
}
