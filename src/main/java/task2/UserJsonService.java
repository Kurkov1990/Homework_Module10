package task2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserJsonService {

    public static void main(String[] args) {
        String inputFile = "file.txt";
        String outputFile = "user.json";

        List<User> users = readUsersFromFile(inputFile);
        writeUsersToJson(users, outputFile);

        System.out.println("JSON " + outputFile + " file has been created.");
    }

    private static List<User> readUsersFromFile(String filename) {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                System.out.println("File is empty!");
                return users;
            }
            String[] headers = headerLine.trim().split("\\s+");

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] values = line.trim().split("\\s+");
                Map<String, String> data = new HashMap<>();
                for (int i = 0; i < headers.length && i < values.length; i++) {
                    data.put(headers[i], values[i]);
                }

                String name = data.getOrDefault("name", "");
                int age = 0;
                try {
                    age = Integer.parseInt(data.getOrDefault("age", "0"));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age value, defaulting to 0: " + data.get("age"));
                }

                users.add(new User(name, age));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    private static void writeUsersToJson(List<User> users, String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


