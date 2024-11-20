package xjtlu.cpt111.assignment.quiz;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import xjtlu.cpt111.assignment.quiz.UI_utils.ButtonFactory;
import xjtlu.cpt111.assignment.quiz.utils.userBank;

import java.io.IOException;

public class Main extends Application {
    private Stage Stage0; // 初始化界面Stage0
    private Stage Stage1; // 登录注册界面Stage1
    private Stage Stage2; // 操作选择界面Stage2
    private Stage Stage_Login; //登录界面
    private Stage Stage_Register; //注册界面

    String[] topiclist = {"cs", "ee", "english", "mathematics"};
    userBank user;

    {
        try {
            user = new userBank(topiclist);
        } catch (IOException e) {
            showError(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        System.out.println("System launched!\nAll the events will show below:");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.Stage0 = primaryStage; // 保存主界面的Stage引用
        initialize_Stage0(primaryStage);
    }

    private void initialize_Stage0(Stage stage) {
        StackPane layout = new StackPane();
        Button enterButton = new Button("Enter the Quiz System");
        enterButton.setOnAction(event -> show_Stage1());
        layout.getChildren().add(enterButton);
        Scene scene = new Scene(layout, 480, 270);
        stage.setScene(scene);
        stage.setTitle("Quiz System");
        stage.show();
        System.out.println("Stage0 shown");
    }

    private void show_Stage1() {
        if (Stage1 == null) {
            Stage1 = new Stage();
            initialize_Stage1(Stage1);
        }
        Stage1.show();
        System.out.println("Stage1 shown");
        if (Stage0 != null && Stage0.isShowing()) {
            Stage0.close();
        }
    }

    private void initialize_Stage1(Stage stage) {
        VBox layout = new VBox(10); // 使用VBox布局，间距为10
        layout.setAlignment(Pos.CENTER); // 设置布局居中
        layout.setPrefWidth(120);

        //分别处理三个按钮的事件
        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(100);
        loginButton.setOnAction(event -> {
            try {
                this.Login();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Button registerButton = new Button("Register");
        registerButton.setPrefWidth(100);
        registerButton.setOnAction(event -> this.Register());
        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(100);
        exitButton.setOnAction(event -> this.Exit());

        layout.getChildren().addAll(loginButton, registerButton, exitButton);
        Scene scene = new Scene(layout, 480, 270);
        stage.setScene(scene);
        stage.setTitle("Quiz System");
    }

    private void Login() throws IOException {
        System.out.println("Login button clicked");
        // 执行登录操作

            Stage_Login = new Stage();
            Stage_Login.setTitle("Login");
            Stage_Login.setResizable(false);
            Stage_Login.setWidth(480);
            Stage_Login.setHeight(270);

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10));
            grid.setVgap(10);
            grid.setHgap(10);

            Label userLabel = new Label("Username:");
            grid.add(userLabel, 0, 0);
            TextField usernameField = new TextField();
            grid.add(usernameField, 1, 0);

            Label passLabel = new Label("Password:");
            grid.add(passLabel, 0, 1);
            PasswordField passwordField = new PasswordField();
            grid.add(passwordField, 1, 1);

            Button loginButton = new Button("Login");
            loginButton.setOnAction(e -> {
                String username = usernameField.getText();
                String password = passwordField.getText();
                if (user.check_user(username, password)) {
                    show_Stage2();
                    if (Stage_Login != null && Stage_Login.isShowing()) {
                        Stage_Login.close();
                    }
                } else {
                    showError("Username or Password is incorrect");
                }
            });
            grid.add(loginButton, 1, 2);

        // 添加返回按钮
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            Stage_Login.hide(); // 隐藏登录界面
            Stage1.show(); // 显示注册登录界面
        });
        grid.add(returnButton, 1, 3); // 将返回按钮添加到布局中

        Scene scene = new Scene(grid);
        Stage_Login.setScene(scene);
        Stage_Login.show();

        // 关闭Stage1
        if (Stage1 != null && Stage1.isShowing()) {
            Stage1.hide(); // 隐藏Stage1
        }

    }

    private void show_Stage2() {
        System.out.println("Stage2 shown");
    }

    private void Register() {
        System.out.println("Register button clicked");
        // 执行注册操作
        if (Stage_Register == null || !Stage_Register.isShowing()) {
            Stage_Register = new Stage();
            Stage_Register.setTitle("Register");
            Stage_Register.setResizable(false);
            Stage_Register.setWidth(480);
            Stage_Register.setHeight(270);

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10));
            grid.setVgap(10);
            grid.setHgap(10);

            Label userLabel = new Label("Username:");
            grid.add(userLabel, 0, 0);
            final TextField usernameField = new TextField();
            grid.add(usernameField, 1, 0);
            Label pass_Label = new Label("Password:");
            Label cfmpass_Label = new Label("Confirm Password:");

            Button confirmButton = new Button("Confirm");
            confirmButton.setOnAction(e -> {
                String username = usernameField.getText();
                if (username.length() < 4) {
                    showError("Username must be longer than 4 letters!");
                } else if (user.check_user(username)) { // 假设check_user返回true如果用户名已存在
                    showError("Username has been chosen!");
                } else {
                    // 如果用户名检查通过，显示密码输入框
                    confirmButton.setDisable(true); // 禁用确认按钮

                    grid.add(pass_Label, 0, 1);
                    PasswordField passwordField = new PasswordField();
                    grid.add(passwordField, 1, 1);

                    grid.add(cfmpass_Label, 0, 2);
                    PasswordField confirmPasswordField = new PasswordField();
                    grid.add(confirmPasswordField, 1, 2);

                    Button registerButton = new Button("Register");
                    registerButton.setOnAction(event -> {
                        String password = passwordField.getText();
                        String confirmPassword = confirmPasswordField.getText();
                        if (!password.equals(confirmPassword)) {
                            showError("Passwords do not match!");
                        } else {
                            // 注册用户逻辑
                            try {
                                user.write_user(username, password); // 假设register方法注册用户
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            show_Stage1(); // 注册成功后返回Stage1
                            Stage_Register.close(); // 关闭注册窗口
                        }
                    });
                    grid.add(registerButton, 1, 3); // 添加注册按钮
                    Scene scene = new Scene(grid);
                    Stage_Register.setScene(scene);
                    Stage_Register.show();

                    // 隐藏Stage1
                    if (Stage1 != null && Stage1.isShowing()) {
                        Stage1.hide();
                    }
                }
            });
            grid.add(confirmButton, 1, 2);

            // 添加返回按钮
            Button returnButton = new Button("Return");
            returnButton.setOnAction(e -> {
                Stage_Register.hide(); // 隐藏注册界面
                Stage1.show(); // 显示上一个界面
            });
            grid.add(returnButton, 1, 3); // 将返回按钮添加到布局中

            Scene scene = new Scene(grid);
            Stage_Register.setScene(scene);
        }
        Stage_Register.show();

    }

    private void Exit() {
        System.out.println("Exit button clicked");
        // 执行退出操作
        // 关闭所有打开的Stage
        if (Stage0 != null) Stage0.close();
        if (Stage1 != null) Stage1.close();
        if (Stage2 != null) Stage2.close();
        if (Stage_Login != null) Stage_Login.close();
        if (Stage_Register != null) Stage_Register.close();
        // 结束程序
        System.out.println("System exited");
        System.exit(0);

    }

    private void showError(String message) {
        Stage errorStage = new Stage();
        errorStage.initStyle(StageStyle.UTILITY);
        errorStage.setTitle("Error");
        VBox vbox = new VBox(10, new Label(message));
        vbox.setPadding(new Insets(10));
        Scene scene = new Scene(vbox);
        errorStage.setScene(scene);
        errorStage.showAndWait();
    }

}