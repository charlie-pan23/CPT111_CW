package xjtlu.cpt111.assignment.quiz;
import java.io.IOException;
public class testmain {
    public static void main(String[] args) {
        String[] topic_list = { "cs", "ee", "english", "mathematics" };
        try{
            userBank userBank_test = new userBank(topic_list);
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }    
}
