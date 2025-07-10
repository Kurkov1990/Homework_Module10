package task2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

            int nameIndex = -1;
            int ageIndex = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase("name")) {
                    nameIndex = i;
                } else if (headers[i].equalsIgnoreCase("age")) {
                    ageIndex = i;
                }
            }

            if (nameIndex == -1 || ageIndex == -1) {
                System.out.println("Missing required 'name' or 'age' columns.");
                return users;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] values = line.trim().split("\\s+");
                if (values.length <= Math.max(nameIndex, ageIndex)) {
                    continue;
                }

                String name = values[nameIndex];
                int age;
                try {
                    age = Integer.parseInt(values[ageIndex]);
                    if (age < 0 || age > 120) {
                        System.out.printf("Skipping user %s with unrealistic age: %d%n", name, age);
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.printf("Skipping user %s with invalid format of the age: %s%n", name, values[ageIndex]);
                    continue;
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
