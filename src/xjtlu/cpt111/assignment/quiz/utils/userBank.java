package xjtlu.cpt111.assignment.quiz.utils;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class userBank {
    private static final String users_csv_path = "./resources/users.csv";
    private static final String users_scores_csv_path = "./resources/users_scores.csv";
    private ArrayList<String[]> users_data;
    private ArrayList<String[]> users_scores_data;
    private String[] topic_list = {"Computer Science", "Electronic Engineering", "English", "Mathematics"};

    public userBank(String[] topic_list_new) throws IOException {
        this.topic_list = topic_list_new;
        this.users_data = refresh_data("users");
        this.users_scores_data = refresh_data("users_scores");
    }

    private static ArrayList<String[]> read_csv(String filePath) throws IOException {

        ArrayList<String[]> temp = new ArrayList<String[]>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(!line.trim().isEmpty()) {
                    String[] row = line.split(",");
                    temp.add(row);
                }
            }
            System.out.println("Read data from\t"+filePath);
            return temp;
        }
    }

    public boolean check_user(String name, String pwd) {
        for (String[] row : users_data) {
            if (Objects.equals(name, row[0])) {
                // username found
                return Objects.equals(pwd, row[2]); // return the result of pwd comparison
            }
        }
        return false; // username not found
    }

    public boolean check_user(String name) {
        for (String[] row : users_data) {
            if (Objects.equals(name, row[0])) {
                return false;
            }
        }
        return true; // username not found
    }


    public int write_user(String name,String true_name, String pwd) throws IOException {
        String[] temp = {name, true_name, pwd};
        this.users_data.add(temp);
        write_to_csv(this.users_data, users_csv_path);
        this.users_data = refresh_data("users");
        return 0;
    }

    public void write_user_score(String name, String topic, String finished_time, double score) throws IOException {
        String[] temp = {name, topic, finished_time, "" + score};
        this.users_scores_data.add(temp);
        write_to_csv(this.users_scores_data, users_scores_csv_path);
        this.users_scores_data = refresh_data("users_scores");
    }

    public ArrayList<String[]> read_user_score(String name) {
        ArrayList<String[]> user_scores = new ArrayList<String[]>();
        for (String s : topic_list) {
            int counter = 0;
            String[] temp = new String[4];
            temp[0] = s;
            for (int i = this.users_scores_data.size() - 1; i > -1; i--) {
                if ((Objects.equals(this.users_scores_data.get(i)[0], name)) && (Objects.equals(this.users_scores_data.get(i)[1], s))) {
                    if (counter < 3) {
                        temp[counter + 1] = this.users_scores_data.get(i)[3];
                        counter++;
                    }
                }
            }
            user_scores.add(temp);
        }
        return user_scores;
    }

    public ArrayList<String[]> read_topic_score() {
        ArrayList<String[]> topics_score = new ArrayList<String[]>();
        for (int i = 0; i < topic_list.length; i++) {
            String[] score = new String[]{"0", "0", "0", "0"};
            topics_score.add(score);
        }
        for (String[] row : this.users_scores_data) {
            for (int j = 0; j < topic_list.length; j++) {
                if (Objects.equals(row[1], topic_list[j])) {
                    if (Double.parseDouble(row[3]) >Double
                            .parseDouble(topics_score.get(j)[3])) {
                        topics_score.set(j, row);
                    }
                    break;
                }
            }
        }
        return topics_score;
    }

    private static void write_to_csv(ArrayList<String[]> data, String csv_file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csv_file))) {
            for (String[] row : data) {
                for (int i = 0; i < row.length; i++) {
                    writer.write(row[i]);
                    if (i < row.length - 1) {
                        writer.write(','); // CSV separate symbol
                    }
                }
                writer.newLine();
            }
        }
    }

    private static ArrayList<String[]> refresh_data(String type) throws IOException {
        int maxRetries = 3;
        int retries = 0;
        String path = "";
        if (Objects.equals(type, "users")) {
            path = users_csv_path;
        } else if (Objects.equals(type, "users_scores")) {
            path = users_scores_csv_path;
        }
        while (retries < maxRetries) {
            try {
                File file = new File(path);
                if (!file.exists()) { // if this file not exits, then create a new file
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
        return new ArrayList<String[]>();// if the method cannot read csv file, return empty arraylist
    }
}
<<<<<<< HEAD
=======

>>>>>>> origin/PMC
