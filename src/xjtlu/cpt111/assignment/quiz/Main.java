package xjtlu.cpt111.assignment.quiz;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import xjtlu.cpt111.assignment.quiz.models.Question;
import xjtlu.cpt111.assignment.quiz.utils.userBank;

import static xjtlu.cpt111.assignment.quiz.utils.InfoDialog.show_Info;

public class Main extends Application {
    private Stage Stage0; // 初始化界面 Stage0
    private Stage Stage1; // 登录注册界面 Stage1
    private Stage Stage2; // 操作选择界面 Stage2
    private Stage Stage_Login; //登录界面
    private Stage Stage_Register; //注册界面
    private Stage Stage_Topic; //topic choose 界面
    private Stage Stage_Dashboard; //dashboard 界面
    private Stage Stage_Leaderboard; // leaderboard 界面
    private Stage Stage_Quiz1; //quiz 准备界面
    private Stage Stage_Quiz2; //quiz 界面
    private Stage Stage_Score; //分数界面

    public String UserName = new String();

    String[] topiclist = {"Computer Science", "Electronic Engineering", "English", "Mathematics"};
    userBank user;

    {
        try {
            user = new userBank(topiclist);
        } catch (IOException e) {
            show_Info("Error", e.getMessage());
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
                UserName = username;
                if (Stage_Login != null && Stage_Login.isShowing()) {
                    Stage_Login.close();
                }
            } else {
                show_Info("Error", "Username or Password is incorrect");
            }
        });
        grid.add(loginButton, 1, 2);

        // 添加返回按钮
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            Stage_Login.hide(); // 隐藏登录界面
            Stage1.show(); // 显示注册登录界面
        });
        grid.add(backButton, 1, 3); // 将返回按钮添加到布局中

        Scene scene = new Scene(grid);
        Stage_Login.setScene(scene);
        Stage_Login.show();

        // 关闭Stage1
        if (Stage1 != null && Stage1.isShowing()) {
            Stage1.hide(); // 隐藏Stage1
        }

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
                if (username.length() < 2) {
                    show_Info("Warning", "Username must be longer than 2 letters!");
                } else if (!user.check_user(username)) { // 假设check_user返回true如果用户名已存在
                    show_Info("Error", "Username has been chosen!");
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
                            show_Info("Error", "Passwords do not match!");
                        } else {
                            // 注册用户逻辑
                            try {
                                user.write_user(username, password); // 假设register方法注册用户
                                show_Info("Success", "User successfully registered!");
                            } catch (IOException ex) {
                                show_Info("Error", ex.getMessage());
                                throw new RuntimeException(ex);
                            }
                            show_Stage1(); // 注册成功后返回Stage1
                            Stage_Register.close(); // 关闭注册窗口
                        }
                    });
                    grid.add(registerButton, 1, 3); // 添加注册按钮
//                    Scene scene = new Scene(grid);
//                    Stage_Register.setScene(null);
//                    Stage_Register.setScene(scene);
                    Stage_Register.show();

                    // 隐藏Stage1
                    if (Stage1 != null && Stage1.isShowing()) {
                        Stage1.hide();
                    }
                }
            });
            grid.add(confirmButton, 1, 2);

            // 添加返回按钮
            Button backButton = new Button("Back");
            backButton.setOnAction(e -> {
                Stage_Register.hide(); // 隐藏注册界面
                Stage1.show(); // 显示上一个界面
            });
            grid.add(backButton, 1, 3); // 将返回按钮添加到布局中

            Scene scene = new Scene(grid);
//            Stage_Register.setScene(null);
            Stage_Register.setScene(scene);
        }
        Stage_Register.show();

    }

    private void show_Stage2() {
        System.out.println("Stage2 shown");
        if (Stage2 == null || !Stage2.isShowing()) {
            Stage2 = new Stage();
            Stage2.setTitle("Choose Operation");
            Stage2.setResizable(false);
            Stage2.setWidth(480);
            Stage2.setHeight(270);

            VBox layout = new VBox(10); // 使用VBox布局，间距为10
            layout.setAlignment(Pos.CENTER); // 设置布局居中

            // 添加按钮
            Button chooseTopicButton = new Button("Choose quiz topic");
            Button myDashboardButton = new Button("My dashboard");

            Button backButton = new Button("Back");

            // 为按钮添加事件处理器
            chooseTopicButton.setOnAction(e -> {
                Stage_Login.hide();
                Choose_Topic();
            });

            myDashboardButton.setOnAction(e -> {
                Stage_Login.hide();
                Dash_Board();
            });


            backButton.setOnAction(e -> {
                Stage2.hide(); // 隐藏Stage2
                Stage1.show(); // 显示Stage1
            });

            // 将按钮添加到布局中
            layout.getChildren().addAll(chooseTopicButton, myDashboardButton, backButton);

            Scene scene = new Scene(layout, 480, 270);
            Stage2.setScene(scene);
        }
        Stage2.show();
    }


    private void Dash_Board() {
        System.out.println("Dash_Board shown");
//        System.out.println(user.read_user_score(UserName));
        ArrayList<String[]> score_board =user.read_user_score(UserName);


        // 初始化Stage
        if (Stage_Leaderboard == null || !Stage_Leaderboard.isShowing()) {
            Stage_Leaderboard = new Stage();
            Stage_Leaderboard.setTitle("Choose the topic of quiz");
            Stage_Leaderboard.setResizable(false);
            Stage_Leaderboard.setWidth(640);
            Stage_Leaderboard.setHeight(360);

            GridPane layout = new GridPane();
            layout.setPadding(new Insets(10));
            layout.setVgap(10);
            layout.setHgap(10);
            layout.setAlignment(Pos.CENTER);


            Label[][] Label = new Label[4][4];
//            for (int i = 0; i < 4; i++) {
//                Label[0][i] = new Label(topiclist[i]);
//            }

            for(int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    Label[i][j]=new Label(score_board.get(i)[j]);
                    System.out.println(score_board.get(i)[j]);
                }
            }


            for(int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    layout.add(Label[i][j], i, j);
                }
            }
            Scene scene = new Scene(layout);
            Stage_Leaderboard.setScene(scene);
            Stage_Leaderboard.show();

        }

    }

    private void Leader_Board() {
        System.out.println("Leader_Board shown");

        ArrayList<String[]> score_board =user.read_topic_score();


        // 初始化Stage
        if (Stage_Dashboard == null || !Stage_Dashboard.isShowing()) {
            Stage_Dashboard = new Stage();
            Stage_Dashboard.setTitle("Choose the topic of quiz");
            Stage_Dashboard.setResizable(false);
            Stage_Dashboard.setWidth(640);
            Stage_Dashboard.setHeight(360);

            GridPane layout = new GridPane();
            layout.setPadding(new Insets(10));
            layout.setVgap(10);
            layout.setHgap(10);
            layout.setAlignment(Pos.CENTER);


            Label[][] Label = new Label[4][4];
//            for (int i = 0; i < 4; i++) {
//                Label[0][i] = new Label(topiclist[i]);
//            }

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    Label[i][j] = new Label(score_board.get(i)[j]);
                    System.out.println(score_board.get(i)[j]);
                }
            }


            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    layout.add(Label[i][j], i, j);
                }
            }
            Scene scene = new Scene(layout);
            Stage_Dashboard.setScene(scene);
            Stage_Dashboard.show();
        }

    }

    private void Choose_Topic() {
        System.out.println("Topic to be chosen");
        if (Stage_Topic == null || !Stage_Topic.isShowing()) {
            Stage_Topic = new Stage();
            Stage_Topic.setTitle("Choose the topic of quiz");
            Stage_Topic.setResizable(false);
            Stage_Topic.setWidth(640);
            Stage_Topic.setHeight(360);

            GridPane layout = new GridPane();
            layout.setPadding(new Insets(10));
            layout.setVgap(10);
            layout.setHgap(10);
            layout.setAlignment(Pos.CENTER); //置于中间

            // 添加按钮
            Button computerScienceButton = new Button("Computer Science");
            computerScienceButton.setPrefWidth(200);
            Button electronicEngineeringButton = new Button("Electronic Engineering");
            electronicEngineeringButton.setPrefWidth(200);
            Button englishButton = new Button("English");
            englishButton.setPrefWidth(200);
            Button mathematicsButton = new Button("Mathematics");
            mathematicsButton.setPrefWidth(200);
            Button backButton = new Button("Back");

            // 将按钮添加到布局中
            layout.add(computerScienceButton, 0, 0);
            layout.add(electronicEngineeringButton, 0, 1);
            layout.add(englishButton, 0, 2);
            layout.add(mathematicsButton, 0, 3);
            layout.add(backButton, 2, 4);

            // 为按钮添加事件处理器
            backButton.setOnAction(e -> {
                Stage_Topic.hide();
                Stage2.show();
            });
            computerScienceButton.setOnAction(e -> {
                System.out.println("Computer Science selected");
                Stage_Topic.hide();
                Start_quiz("Computer Science");
            });
            electronicEngineeringButton.setOnAction(e -> {
                System.out.println("Electronic Engineering selected");
                Stage_Topic.hide();
                Start_quiz("Electronic Engineering");

            });
            englishButton.setOnAction(e -> {
                System.out.println("English selected");
                Stage_Topic.hide();
                Start_quiz("English");

            });
            mathematicsButton.setOnAction(e -> {
                System.out.println("Mathematics selected");
                Stage_Topic.hide();
                Start_quiz("Mathematics");

            });

            Scene scene = new Scene(layout);
            Stage_Topic.setScene(scene);
        }
        Stage_Topic.show();
    }

    private void Start_quiz(String topic) {
        System.out.println("Start_quiz shown");
        if (Stage_Quiz1 == null || !Stage_Quiz1.isShowing()) {
            Stage_Quiz1 = new Stage();
            Stage_Quiz1.setTitle("Start quiz");
            Stage_Quiz1.setResizable(false);
            Stage_Quiz1.setWidth(640);
            Stage_Quiz1.setHeight(360);

            VBox layout = new VBox(20); // 使用VBox布局，间距为20
            layout.setAlignment(Pos.CENTER); // 设置布局居中
            layout.setPadding(new Insets(20)); // 设置内边距

            // 添加标题文本
            Label topicLabel = new Label("You have chosen Topic: " + topic);
            topicLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
            layout.getChildren().add(topicLabel);

            // 添加按钮
            Button attemptQuizButton = new Button("Attempt Quiz");
            Button leaderBoardButton = new Button("Leader board");
            Button backButton = new Button("Back");

            // 为按钮添加事件处理器
            attemptQuizButton.setOnAction(e -> {
                System.out.println("Attempt Quiz button clicked");
                Stage_Quiz1.hide();
                Quiz(topic);

            });

            leaderBoardButton.setOnAction(e -> {
                Stage_Login.hide();
                Leader_Board();
            });

            backButton.setOnAction(e -> {
                System.out.println("Back button clicked");
                Stage_Quiz1.hide();
                Stage_Topic.show();

            });

            // 将按钮添加到布局中
            layout.getChildren().addAll(attemptQuizButton, leaderBoardButton, backButton);

            Scene scene = new Scene(layout);
            Stage_Quiz1.setScene(scene);
        }
        Stage_Quiz1.show();
    }

    private void Quiz(String topic) {
        System.out.println("Quiz shown");
        questionBank qb = new questionBank();
        var lambdaContext = new Object() {
            int Index = 0;
            int score = 0;
        };

        // 取出特定问题
        Question[] Questions = qb.getQuestions(topic , 8);

        if (Stage_Quiz2 == null || !Stage_Quiz2.isShowing()) {
            Stage_Quiz2 = new Stage();
            Stage_Quiz2.setTitle("Start quiz");
            Stage_Quiz2.setResizable(false);
            Stage_Quiz2.setWidth(960);
            Stage_Quiz2.setHeight(540);

            TextArea questionArea = new TextArea();
            questionArea.setEditable(false);
            questionArea.setWrapText(true);
            questionArea.setStyle("-fx-font-size: 16px;");

            TextField answerField = new TextField();
            answerField.setPromptText("Enter your answer (number only)");
            answerField.setTextFormatter(new TextFormatter<>(change -> {
                if (change.getControlNewText().matches("\\d*")) {
                    return change;
                }
                return null;
            }));

            Button nextButton = new Button("Next");
            Button quitButton = new Button("Quit");

            VBox layout = new VBox(10);
            layout.getChildren().addAll(questionArea, answerField, nextButton, quitButton);

            Scene scene = new Scene(layout);
            Stage_Quiz2.setScene(scene);

            // 显示第一题
            questionArea.setText(String.valueOf(Questions[lambdaContext.Index]));
            Stage_Quiz2.show();

            lambdaContext.Index = 1;
            lambdaContext.score = 0;

            nextButton.setOnAction(e -> {
                String userAnswer = answerField.getText();
                if (lambdaContext.Index < Questions.length) {
                    try {
                        if (qb.isUserAnswerCorrect(userAnswer, Questions[lambdaContext.Index])) {
                            lambdaContext.score++;
                        }

                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    lambdaContext.Index++;
                }
                if (lambdaContext.Index < Questions.length) {
                    questionArea.clear();
                    questionArea.setText(String.valueOf(Questions[lambdaContext.Index]));
                    answerField.clear();
                } else {
                    Score_Page(topic, lambdaContext.score, Questions.length);
                    Stage_Quiz2.close();
                }
            });



        }
    }

    private void Score_Page(String topic, int score, int fullScore) {
        Date now = new Date();
        Stage_Score = new Stage(); // 将 Stage_Score 的初始化移到方法的开始
        Stage_Score.setTitle("Quiz Finished!");

        try {
            user.write_user_score(UserName, topic, now.toString(), score);
            show_Info("Info", "Your score has been saved!");
            Stage_Quiz2.close();
            Stage_Score.show(); // 确保在 Stage_Score 初始化后调用 show 方法
        } catch (IOException e) {
            show_Info("Error", "Failed to save user score");
            throw new RuntimeException(e);
        }

        Stage_Score.setWidth(960);
        Stage_Score.setHeight(540);
        Button closeButton = new Button("Close");
        Button dashboardButton = new Button("My Dashboard");

        closeButton.setOnAction(e -> {
            Stage_Score.close();
            Stage_Topic.show();
        });
        dashboardButton.setOnAction(e -> {
            Dash_Board();
        });

        TextArea scoreArea = new TextArea();
        scoreArea.setEditable(false);
        scoreArea.setWrapText(true);
        scoreArea.setStyle("-fx-font-size: 16px;");

        scoreArea.setText("Topic: " + topic + "\nQuiz completed!" + "\nYour score is: " + score + " / " + fullScore);
        VBox layout = new VBox(10);

        layout.getChildren().addAll(scoreArea, closeButton, dashboardButton);

        Scene scene = new Scene(layout);
        Stage_Score.setScene(scene);
        Stage_Score.show(); // 现在 Stage_Score 已经被初始化，可以安全调用 show 方法
    }


    private void Exit() {
        System.out.println("Exit button clicked");
        // 关闭所有打开的Stage
        if (Stage0 != null) Stage0.close();
        if (Stage1 != null) Stage1.close();
        if (Stage2 != null) Stage2.close();
        if (Stage_Login != null) Stage_Login.close();
        if (Stage_Register != null) Stage_Register.close();
        if (Stage_Topic != null) Stage_Topic.close();
        if (Stage_Dashboard != null) Stage_Dashboard.close();
        if (Stage_Leaderboard != null) Stage_Leaderboard.close();
        if (Stage_Quiz1 != null) Stage_Quiz1.close();
        if (Stage_Quiz2 != null) Stage_Quiz2.close();
        if (Stage_Score != null) Stage_Score.close();
        // 结束程序
        System.out.println("System exited");
        System.exit(0);

    }

}