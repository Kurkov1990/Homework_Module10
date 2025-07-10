package task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PhoneNumberValidator {

    public static void main(String[] args) {
        printValidPhoneNumbers("phone_numbers.txt");
    }

    private static void printValidPhoneNumbers(String filename) {
        String regex = "(\\(\\d{3}\\) \\d{3}-\\d{4})|(\\d{3}-\\d{3}-\\d{4})";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.matches(regex)) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error during reading the file: " + e.getMessage());
        }
    }


}

