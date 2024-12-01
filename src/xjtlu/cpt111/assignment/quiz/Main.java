package xjtlu.cpt111.assignment.quiz;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import xjtlu.cpt111.assignment.quiz.models.Question;
import xjtlu.cpt111.assignment.quiz.utils.questionBank;
import xjtlu.cpt111.assignment.quiz.utils.userBank;

import static xjtlu.cpt111.assignment.quiz.utils.InfoDialog.show_Info;

public class Main extends Application {
    /*
     * Stage0                    initialize stage
     * Stage1                    confirm and register stage
     * Stage2                    operation selecting stage
     * Stage_Login               stage for handling log in progress
     * Stage_Register            stage for handling register progress
     * Stage_Topic               topic selecting stage
     * Stage_Dashboard           dashboard stage
     * Stage_Leaderboard         leaderboard stage
     * Stage_Quiz1               stage to start quiz
     * Stage_Quiz2               stage to do quiz
     * Stage_Score               stage to display score
     *
     * All the Stage change events will be display in the CMD
     *
     * */

    private Stage Stage0;
    private Stage Stage1;
    private Stage Stage2;
    private Stage Stage_Login;
    private Stage Stage_Register;
    private Stage Stage_Topic;
    private Stage Stage_Dashboard;
    private Stage Stage_Leaderboard;
    private Stage Stage_Quiz1;
    private Stage Stage_Quiz2;
    private Stage Stage_Score;

    public String UserName = "";
    questionBank qb = new questionBank();
    String[] topiclist = qb.getTopics();

    int numofTopics = topiclist.length;


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
    public void start(Stage Stage) throws Exception {
        this.Stage0 = Stage;
        initialize_Stage0(Stage);
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
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefWidth(120);

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
        // Place all items on scene
        layout.getChildren().addAll(loginButton, registerButton, exitButton);
        Scene scene = new Scene(layout, 480, 270);
        stage.setScene(scene);
        stage.setTitle("Quiz System");
    }

    private void Login() throws IOException {
        System.out.println("Login button clicked");

        Stage_Login = new Stage();
        Stage_Login.setTitle("Login");
        Stage_Login.setResizable(false);

        // Using GridPane style for conveniently typesetting due to using coordinate
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        // Initialize items
        Label userLabel = new Label("Username:");
        grid.add(userLabel, 0, 0);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 0);
        Label passLabel = new Label("Password:");
        grid.add(passLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 1);

        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                passwordField.requestFocus();
                event.consume();
            }
        });

        // Button with event reaction
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

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                loginButton.fire();
                event.consume();
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            Stage_Login.hide();
            Stage1.show();
        });
        grid.add(backButton, 1, 3);

        Scene scene = new Scene(grid, 480, 270);
        Stage_Login.setScene(scene);
        Stage_Login.show();

        if (Stage1 != null && Stage1.isShowing()) {
            Stage1.hide();
        }
    }

    private void Register() {
        System.out.println("Register button clicked");
        // using if to ensure no stage conflict
        if (Stage_Register == null || !Stage_Register.isShowing()) {
            Stage_Register = new Stage();
            Stage_Register.setTitle("Register");
            Stage_Register.setResizable(false);
            Stage_Register.setWidth(560);
            Stage_Register.setHeight(315);

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10));
            grid.setVgap(10);
            grid.setHgap(10);
            grid.setAlignment(Pos.CENTER);

            Label userLabel = new Label("Username:");
            grid.add(userLabel, 0, 0);
            final TextField usernameField = new TextField();
            grid.add(usernameField, 1, 0);
            Label nameLabel = new Label("True name:");
            grid.add(nameLabel, 0, 1);
            final TextField nameField = new TextField();
            grid.add(nameField, 1, 1);

            Label pass_Label = new Label("Password:");
            Label cfmpass_Label = new Label("Confirm Password:");

            usernameField.setOnKeyPressed(event -> {
                if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                    nameField.requestFocus();
                    event.consume();
                }
            });

            Button confirmButton = new Button("Confirm");
            Button backButton1 = new Button("Back");
            backButton1.setOnAction(e -> {
                Stage_Register.hide();
                Stage1.show();
            });
            grid.add(backButton1, 1, 4);
            Scene scene = new Scene(grid);
            Stage_Register.setScene(scene);

            nameField.setOnKeyPressed(event -> {
                if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                    confirmButton.fire();
                    event.consume();
                }
            });

            confirmButton.setOnAction(e -> {
                String username = usernameField.getText();
                usernameField.setEditable(false);           // lock up textbox
                String trueName = nameField.getText();
                nameField.setEditable(false);
                if (username.length() < 2) {
                    // not allowing too short name
                    show_Info("Warning", "Username must be longer than 2 letters!");
                    usernameField.setEditable(true);
                    nameField.setEditable(true);
                } else if (trueName.length() < 2) {
                    show_Info("Warning", "Username must be longer than 2 letters!");
                    usernameField.setEditable(true);
                    nameField.setEditable(true);
                } else if (!user.check_user(username)) {
                    //check whether username is already used
                    show_Info("Error", "Username has been chosen!");
                    usernameField.setEditable(true);
                    nameField.setEditable(true);
                } else {
                    // if username is legal, display password items
                    confirmButton.setDisable(true);
                    backButton1.setDisable(true);
                    grid.add(pass_Label, 0, 2);
                    PasswordField passwordField = new PasswordField();
                    grid.add(passwordField, 1, 2);

                    grid.add(cfmpass_Label, 0, 3);
                    PasswordField confirmPasswordField = new PasswordField();
                    grid.add(confirmPasswordField, 1, 3);

                    Button registerButton = new Button("Register");
                    registerButton.setOnAction(event -> {
                        String password = passwordField.getText();
                        String confirmPassword = confirmPasswordField.getText();
                        if(password.length()<6 || confirmPassword.length()<6) {
                            show_Info("Warning", "Password must be longer than 6 characters!");
                        }

                        if (!password.equals(confirmPassword)) {
                            show_Info("Error", "Two passwords do not match!");
                        } else {
                            //write in user into database
                            try {
                                user.write_user(username, trueName, password);    // from userBank
                                show_Info("Success", "User successfully registered!");
                            } catch (IOException ex) {
                                show_Info("Error", "Register Failed!\n" + ex.getMessage());
                            }
                            // automatically back
                            show_Stage1();
                            Stage_Register.close();
                        }
                    });

                    Button backButton2 = new Button("Back");
                    backButton2.setOnAction(event -> {
                        //back button unlock username textfield and keep username, but clear password
                        usernameField.setEditable(true);
                        nameField.setEditable(true);
                        confirmButton.setDisable(false);
                        backButton1.setDisable(false);
                        passwordField.clear();
                        confirmPasswordField.clear();
                        grid.getChildren().remove(passwordField);
                        grid.getChildren().remove(confirmPasswordField);
                        grid.getChildren().remove(pass_Label);
                        grid.getChildren().remove(cfmpass_Label);
                        grid.getChildren().remove(registerButton);
                        grid.getChildren().remove(backButton2);
                        // reset the scene
                        grid.setLayoutY(0);
                    });

                    grid.add(registerButton, 1, 4);
                    grid.add(backButton2, 1, 5);
                    Stage_Register.show();

                    if (Stage1 != null && Stage1.isShowing()) {
                        Stage1.hide();
                    }
                }
            });
            grid.add(confirmButton, 1, 3);
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

            // using vbox style for vertical typesetting
            VBox layout = new VBox(10);
            //centering
            layout.setAlignment(Pos.CENTER);

            // add items
            Button chooseTopicButton = new Button("Choose quiz topic");
            chooseTopicButton.setPrefWidth(200);
            Button myDashboardButton = new Button("My dashboard");
            myDashboardButton.setPrefWidth(200);
            Button leaderBoardButton = new Button("Leader board");
            leaderBoardButton.setPrefWidth(200);
            Button backButton = new Button("Back");

            chooseTopicButton.setOnAction(e -> {
                Stage_Login.hide();
                Choose_Topic();
            });

            myDashboardButton.setOnAction(e -> {
                Stage_Login.hide();
                Dash_Board();
            });

            leaderBoardButton.setOnAction(e -> {
                Stage_Login.hide();
                Leader_Board();
            });

            backButton.setOnAction(e -> {
                Stage2.hide();
                Stage1.show();
            });
            layout.getChildren().addAll(chooseTopicButton, myDashboardButton, leaderBoardButton, backButton);

            Scene scene = new Scene(layout, 480, 270);
            Stage2.setScene(scene);
        }
        Stage2.show();
    }


    private void Dash_Board() {
        System.out.println("Dash_Board shown");
        ArrayList<String[]> score_board = user.read_user_score(UserName);
        // reuse same code block
        if (Stage_Leaderboard == null || !Stage_Leaderboard.isShowing()) {
            Stage_Leaderboard = new Stage();
            Stage_Leaderboard.setTitle("Choose the topic of quiz");
            Stage_Leaderboard.setResizable(false);

            GridPane layout = new GridPane();
            layout.setPadding(new Insets(10));
            layout.setVgap(10);
            layout.setHgap(10);
            layout.setAlignment(Pos.CENTER);

            // table is hard to use, so we use label for the same function
            Label[][] Label = new Label[numofTopics][4];

            for (int i = 0; i < numofTopics; i++) {
                for (int j = 0; j < 4; j++) {
                    Label[i][j] = new Label(score_board.get(i)[j]);
                }
            }
            for (int i = 0; i < numofTopics; i++) {
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
        ArrayList<String[]> score_board = user.read_topic_score();

        if (Stage_Dashboard == null || !Stage_Dashboard.isShowing()) {
            Stage_Dashboard = new Stage();
            Stage_Dashboard.setTitle("Choose the topic of quiz");
            Stage_Dashboard.setResizable(false);

            GridPane layout = new GridPane();
            layout.setPadding(new Insets(10));
            layout.setVgap(10);
            layout.setHgap(10);
            layout.setAlignment(Pos.CENTER);

            Label[][] Label = new Label[numofTopics][4];

            for (int i = 0; i < numofTopics; i++) {
                for (int j = 0; j < 4; j++) {
                    Label[i][j] = new Label(score_board.get(i)[j]);
                }
            }

            for (int i = 0; i < numofTopics; i++) {
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

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(10));
            layout.setAlignment(Pos.CENTER);

            for (int i = 0; i < numofTopics; i++) {
                Button topicButton = new Button(topiclist[i]);
                topicButton.setPrefWidth(200);
                layout.getChildren().add(topicButton);
                String topic = topiclist[i];
                topicButton.setOnAction(e -> {
                    System.out.println(topic + " selected");
                    Stage_Topic.hide();
                    Start_quiz(topic);
                });
            }

            Button backButton = new Button("Back");
            layout.getChildren().add(backButton);
            backButton.setOnAction(e -> {
                Stage_Topic.hide();
                Stage2.show();
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

            VBox layout = new VBox(20);
            layout.setAlignment(Pos.CENTER);
            Label topicLabel = new Label("You have chosen Topic:\t" + topic);
            // set the text bold font and larger
            topicLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
            layout.getChildren().add(topicLabel);

            Button attemptQuizButton = new Button("Attempt Quiz");
            Button backButton = new Button("Back");

            attemptQuizButton.setOnAction(e -> {
                System.out.println("Attempt Quiz button clicked");
                Stage_Quiz1.hide();
                Quiz(topic);
            });

            backButton.setOnAction(e -> {
                System.out.println("Back button clicked");
                Stage_Quiz1.hide();
                Stage_Topic.show();
            });

            layout.getChildren().addAll(attemptQuizButton, backButton);

            Scene scene = new Scene(layout);
            Stage_Quiz1.setScene(scene);
        }
        Stage_Quiz1.show();
    }

    private void Quiz(String topic) {
        System.out.println("Quiz shown");

        var lambdaContext = new Object() {
            int Index = 0;  // number of current question
            int score = 0;  // to store score
        };
        //get questions
        Question[] Questions = qb.getQuestions(topic, 8);

        if (Stage_Quiz2 == null || !Stage_Quiz2.isShowing()) {
            Stage_Quiz2 = new Stage();
            Stage_Quiz2.setTitle("Start quiz");
            Stage_Quiz2.setResizable(false);
            //set the stage fixed-sized
            Stage_Quiz2.setWidth(960);
            Stage_Quiz2.setHeight(540);

            TextArea questionArea = new TextArea();
            questionArea.setEditable(false);
            questionArea.setWrapText(true);
            // larger text for question
            questionArea.setStyle("-fx-font-size: 16px;");

            TextField answerField = new TextField();
            answerField.setPromptText("Enter your answer (number only)");
            // numbers are only allowed, avoid weird input text
            answerField.setTextFormatter(new TextFormatter<>(change -> {
                if (change.getControlNewText().matches("\\d?")) {
                    return change;
                }
                return null;
            }));

            Button nextButton = new Button("Next");
            Button quitButton = new Button("Quit");

            VBox layout = new VBox(10);
            layout.setAlignment(Pos.CENTER);
            layout.getChildren().addAll(questionArea, answerField, nextButton, quitButton);

            Scene scene = new Scene(layout);
            Stage_Quiz2.setScene(scene);

            // display the first question
            questionArea.setText(String.valueOf(Questions[lambdaContext.Index]));
            Stage_Quiz2.show();

            lambdaContext.Index = 0;
            lambdaContext.score = 0;

            nextButton.setOnAction(e -> {
                String userAnswer = answerField.getText();
                if (!userAnswer.isEmpty()) {
                    if (lambdaContext.Index < Questions.length) {
                        try {
                            if (qb.isUserAnswerCorrect(userAnswer, Questions[lambdaContext.Index])) {
                                lambdaContext.score++;
                            }

                        } catch (Exception ex) {
                            show_Info("Warning", "You choose the option out of this question.\n This question is signed ZERO");
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
                } else {
                    show_Info("Notice", "The answer is empty!");
                }
            });

            // when press ENTER equals to click nextButton
            answerField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    nextButton.fire(); // equals to click nextButton
                    event.consume(); // consume event
                }
            });

            //quit without save score
            quitButton.setOnAction(e -> {
                Stage_Quiz2.close();
                Start_quiz(topic);
            });
        }
    }

    private void Score_Page(String topic, int score, int fullScore) {
        // get current time
        Date now = new Date();
        Stage_Score = new Stage();
        Stage_Score.setTitle("Quiz Finished!");

        try {
            user.write_user_score(UserName, topic, now.toString(), score);
            show_Info("Info", "Your score has been saved!");
            Stage_Quiz2.close();
            Stage_Score.show();
        } catch (IOException e) {
            show_Info("Error", "Failed to save user score");
            throw new RuntimeException(e);
        }

        Stage_Score.setWidth(960);
        Stage_Score.setHeight(540);
        Button closeButton = new Button("Close");
        Button dashboardButton = new Button("My Dashboard");
        Button exitButton = new Button("Exit");

        closeButton.setOnAction(e -> {
            Stage_Score.close();
            Stage_Topic.show();
        });
        dashboardButton.setOnAction(e -> {
            Dash_Board();
        });
        exitButton.setOnAction(e -> {
            Exit();
        });

        TextArea scoreArea = new TextArea();
        scoreArea.setEditable(false);
        scoreArea.setWrapText(true);
        scoreArea.setStyle("-fx-font-size: 16px;");

        scoreArea.setText("Topic: " + topic + "\nQuiz completed!" + "\nYour score is: " + score + " / " + fullScore);
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(scoreArea, dashboardButton, closeButton, exitButton);

        Scene scene = new Scene(layout);
        Stage_Score.setScene(scene);
        Stage_Score.show();
    }

    private void Exit() {
        System.out.println("Exit button clicked");
        // ensure all stages are closed
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

        // System shuts
        System.out.println("System exited");
        System.exit(0);
    }
}