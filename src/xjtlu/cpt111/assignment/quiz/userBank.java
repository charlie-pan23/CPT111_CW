package xjtlu.cpt111.assignment.quiz;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class userBank {
    private static final String users_csv_path = "./assignment1_quizSystem/assignment1_quizSystem/resources/users.csv";// TODO:
    private static final String users_scores_csv_path = "./assignment1_quizSystem/assignment1_quizSystem/resources/users_scores.csv";// TODO:
    private String[][] users_data;
    private String[][] users_scores_data;
    private String[] topic_list = { "cs", "ee", "english", "mathematics" };// TODO：可以从主类里传过来

    public userBank(String[] topic_list_new ) throws IOException {
        this.topic_list = topic_list_new;
        try {
            this.users_data = refresh_data("users");
            this.users_scores_data = refresh_data("users_scores");
        } catch (IOException e) {
            throw e; // or use System.exit(1);
        }
    }

    private static String[][] double_StringList_rownum(String[][] StringList) {
        String[][] temp = new String[StringList.length * 2][StringList[0].length];
        for (int i = 0; i < StringList.length; i++) {
            for (int j = 0; j < StringList[0].length * 2; j++) {
                temp[i][j] = StringList[i][j];
            }
        }
        return temp;
    }

    private static String[][] double_StringList_colnum(String[][] StringList) {
        String[][] temp = new String[StringList.length][StringList[0].length * 2];
        for (int i = 0; i < StringList.length; i++) {
            for (int j = 0; j < StringList[0].length * 2; j++) {
                temp[i][j] = StringList[i][j];
            }
        }
        return temp;
    }

    private static String[][] read_csv(String filePath) throws IOException {
        String[][] temp = new String[20][20];
        int index_row = -1;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                index_row += 1;
                if (index_row >= temp.length) {
                    temp = double_StringList_rownum(temp);
                }
                for (int i = 0; i < row.length; i++) {
                    if (i >= temp[0].length) {
                        temp = double_StringList_colnum(temp);
                    }
                    temp[index_row][i] = row[i];
                }
            }
            return temp;
        }
    }

    public boolean check_user(String name, String pwd) {
        for (int i = 0; i < this.users_data.length; i++) {
            if (Objects.equals(name, this.users_data[i][1])) { // TODO:The order of name and pwd is to be decide
                if (Objects.equals(pwd, this.users_data[i][2])) {
                    return true;
                } else {
                    return false; // 找到了但是密码错了
                }
            }
        }
        return false; // 没有找到用户名 两者是不是要分开处理
    }

    public boolean check_user(String name) {

        for (int i = 0; i < this.users_data.length; i++) {
            if (Objects.equals(name, this.users_data[i][1])) { // TODO:The order of name and pwd is to be decide
                    return false;
            }
        }
        return true; // 没有找到用户名 两者是不是要分开处理
    }

    public int write_user(String name, String pwd) throws IOException {
        for (int i = 0; i < this.users_data.length; i++) {
            if (Objects.equals(this.users_data[i][1], name)) {
                return 1; // 用户名重复
            }
            if (detect_empty_row(this.users_data, i)) {
                this.users_data[i][1] = name;
                this.users_data[i][2] = pwd;
                write_to_csv(this.users_data, users_csv_path);
                this.users_data=refresh_data("users");
                return 0;
            }
        }
        this.users_data = double_StringList_rownum(this.users_data);
        return write_user(name, pwd);
    }

    public void write_user_score(String name, String topic, String finished_time, double score) throws IOException {
        for (int i = 0; i < this.users_scores_data.length; i++) {
            if (detect_empty_row(this.users_data, i)) {
                this.users_scores_data[i][0] = name;// TODO:The order of name, topic, finished_time and scores is to be
                                               // decide
                this.users_scores_data[i][1] = topic;
                this.users_scores_data[i][2] = finished_time; // 不太确定是否真的需要
                this.users_scores_data[i][3] = "" + score;
                try {
                    write_to_csv(this.users_scores_data, users_scores_csv_path);
                    refresh_data("users_scores");
                } catch (IOException e) {
                    throw e;
                }
                return;// TODO:这里似乎不会有错 直接return了
            }
        }
        this.users_scores_data = double_StringList_rownum(this.users_scores_data);
        write_user_score(name, topic, finished_time, score);
    }

    public String[][] read_user_score(String name) {
        String[][] user_scores = new String[topic_list.length][3];
        int[] counter = new int[topic_list.length];
        for (int i = 0; i < counter.length; i++) {
            counter[i] = -1;
        }
        for (int i = this.users_scores_data.length - 1; i > -1; i--) {
            if (this.users_scores_data[i][0] == name) {
                for (int j = 0; j < topic_list.length; j++) {
                    if (this.users_scores_data[i][1] == topic_list[j]) {
                        counter[j] = counter[j] + 1;
                        if (counter[j] < 3) {
                            user_scores[j][counter[j]] = this.users_scores_data[i][3];
                        } // TODO:应该还能写counter全大于三就提交前结束
                        break;
                    }
                }
            }
        }
        return user_scores;
    }

    public String[] read_topic_score() {
        String[] topics_score = new String[topic_list.length];
        for (int i = this.users_scores_data.length - 1; i > -1; i--) {
            for (int j = 0; j < topic_list.length; j++) {
                if (Objects.equals(this.users_scores_data[i][1], topic_list[j])) {
                    topics_score[j] = "" + Math.max(Double.parseDouble(this.users_scores_data[i][3]),
                            Double.parseDouble(topics_score[j])); // NumberFormatException 未处理
                    break;
                }
            }
        }
        return topics_score;
    }

    private static boolean detect_empty_row(String[][] data, int i) {
        boolean isEmpty = true;
        for (String column : data[i]) {
            if (!column.trim().isEmpty()) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    private static void write_to_csv(String[][] data, String csv_file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csv_file))) {
            for (String[] row : data) {
                for (int i = 0; i < row.length; i++) {
                    writer.write(row[i]);
                    if (i < row.length - 1) {
                        writer.write(','); // CSV分隔符
                    }
                }
                writer.newLine(); // 换行
            }
        } catch (IOException e) {
            throw e;
        }
    }

    private static String[][] refresh_data(String type) throws IOException {
        int maxRetries = 3;
        int retries = 0;
        String path = "";
        if (Objects.equals(type, "users")) {
            path = users_csv_path;
        } else if (Objects.equals(type, "users_scores")) {
            path = users_scores_csv_path;
        } else {
            System.exit(1);// TODO:
        }
        while (retries < maxRetries) {
            try {
                File file = new File(path);
                if (!file.exists()) { // 若不存在就新建
                    file.createNewFile();
                }
                return read_csv(path);
            } catch (IOException e) {
                retries++;
                if (retries < maxRetries) {
                    System.out.println("Retrying...");
                } else {
                    System.out.println("Maximum retries reached. Exiting.");
                    throw e; // or use System.exit(1);
                }
            }
        }
        return read_csv(users_csv_path);// TODO:合理处理返回值！
    }
}
