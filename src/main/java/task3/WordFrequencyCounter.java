package task3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class WordFrequencyCounter {

    public static void main(String[] args) {
        List<String> words = readWordsFromFile("words.txt");
        Map<String, Integer> wordCount = countWordFrequency(words);
        List<Entry<String, Integer>> sortedEntries = sortByFrequency(wordCount);

        for (Entry<String, Integer> entry : sortedEntries) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    private static List<String> readWordsFromFile(String filename) {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitWords = line.trim().split("\\s+");
                for (String word : splitWords) {
                    if (!word.isEmpty()) {
                        words.add(word);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error during reading the file: " + e.getMessage());
        }
        return words;
    }

    private static Map<String, Integer> countWordFrequency(List<String> words) {
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
        return wordCount;
    }

    private static List<Entry<String, Integer>> sortByFrequency(Map<String, Integer> wordCount) {
        List<Entry<String, Integer>> sortedEntries = new ArrayList<>(wordCount.entrySet());
        sortedEntries.sort(Comparator.comparingInt(Map.Entry<String, Integer>::getValue).reversed());
        return sortedEntries;
    }
}
