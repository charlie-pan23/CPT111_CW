package xjtlu.cpt111.assignment.quiz.UI_utils;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ButtonFactory {
    public static Button createButton(String text, double minWidth, double minHeight, double maxWidth, double maxHeight, VBox container) {
        Button button = new Button(text);
        // 设置最小宽度为10个字符的宽度，这里假设一个字符的宽度大约为8像素

        minWidth = 1;

        // 设置按钮的最小尺寸
        button.setMinWidth(minWidth);
        button.setMinHeight(minHeight);

        // 如果提供了最大宽度和高度，则设置最大尺寸
        if (maxWidth != 0) button.setMaxWidth(maxWidth);
        if (maxHeight != 0) button.setMaxHeight(maxHeight);

        // 绑定按钮的最小宽度到VBox的宽度，确保按钮宽度一致
        if (container != null) {
            button.minWidthProperty().bind(container.widthProperty());
        }
        return button;
    }

    public static Button createButton(String text, VBox container) {
        return createButton(text, 0, 0, 0, 0, container);
    }

    public static Button createButtonWithAction(String text, VBox container, ButtonAction action) {
        Button button = createButton(text, 0, 0, 0, 0, container);
        button.setOnAction(event -> action.execute());
        return button;
    }
}

