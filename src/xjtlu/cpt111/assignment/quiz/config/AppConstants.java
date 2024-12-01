//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xjtlu.cpt111.assignment.quiz.config;

import javax.xml.namespace.QName;

public interface AppConstants {
    boolean IS_DEBUG = false;
    boolean IS_VERBOSE = false;
    boolean THROW_INVALID_EXCEPTION = false;
    String QUESTION_OPTION_INDENTATOR = "  ";

    public interface Model {
        QName TAG_DIFFICULTY = new QName("difficulty");
        QName TAG_ANSWER = new QName("answer");
    }
}
