package xjtlu.cpt111.assignment.quiz;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xjtlu.cpt111.assignment.quiz.UI_utils.ButtonFactory;

public class Main extends Application {
    private Stage Stage0; // 初始化界面Stage0
    private Stage Stage1; // 登录注册界面Stage1
    private Stage Stage_Login; //登录界面
    private Stage Stage_Register; //注册界面

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
        Button enterButton = ButtonFactory.createButtonWithAction("Enter the Quiz System", null, this::show_Stage1);
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
        Stage0.hide(); // 隐藏主界面
    }

    private void initialize_Stage1(Stage stage) {
        VBox layout = new VBox(10); // 使用VBox布局，间距为10
        layout.setAlignment(Pos.CENTER); // 设置布局居中
        layout.setPrefWidth(120);

        Button loginButton = ButtonFactory.createButtonWithAction("Login", null, this::Login);
        loginButton.setPrefWidth(100);
        Button registerButton = ButtonFactory.createButtonWithAction("Register", null, this::Register);
        loginButton.setPrefWidth(100);
        Button exitButton = ButtonFactory.createButtonWithAction("Exit", null, this::Exit);
        loginButton.setPrefWidth(100);

        layout.getChildren().addAll(loginButton, registerButton, exitButton);
        Scene scene = new Scene(layout, 480, 270);
        stage.setScene(scene);
        stage.setTitle("Quiz System");
    }

    private void Login() {
        System.out.println("Login button clicked");
        // 执行登录操作
//        userBank user = new userBank("");

    }

    private void Register() {
        System.out.println("Register button clicked");
        // 执行注册操作
    }

    private void Exit() {
        System.out.println("Exit button clicked");
        // 执行退出操作
        // 关闭所有打开的Stage
        if (Stage0 != null) Stage0.close();
        if (Stage1 != null) Stage1.close();
        // 结束程序
        System.out.println("System exited");
        System.exit(0);


    }
}