package xjtlu.cpt111.assignment.quiz.utils;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InfoDialog {

    public static void show_Info(String title, String message) {
        Stage errorStage = new Stage();
        errorStage.initStyle(StageStyle.UTILITY);
        errorStage.setTitle(title);
        VBox vbox = new VBox(10, new Label(message));
        vbox.setPadding(new Insets(10));
        Scene scene = new Scene(vbox);
        errorStage.setScene(scene);
        errorStage.showAndWait();
    }
}