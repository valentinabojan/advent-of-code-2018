package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileNamePart1Test = "src/main/resources/day2/input_part1_test.txt";
        String fileNamePart2Test = "src/main/resources/day2/input_part2_test.txt";
        String fileName = "src/main/resources/day2/input.txt";

        System.out.println(part1(fileNamePart1Test));
        System.out.println(part1(fileName));

        System.out.println(part2(fileNamePart2Test));
        System.out.println(part2(fileName));
    }

    private static int part1(String fileName) throws IOException {
        List<String> input = Files.lines(Paths.get(fileName)).collect(toList());

        int twice = 0, threeTimes = 0;
        for (String word: input) {
            Map<Integer, Long> countedLetters = countLetters(word);

            twice += countedLetters.containsValue(2L) ? 1 : 0;
            threeTimes += countedLetters.containsValue(3L) ? 1 : 0;
        }

        return twice * threeTimes;
    }

    private static String part2(String fileName) throws IOException {
        List<String> input = Files.lines(Paths.get(fileName)).collect(toList());

        for (int i = 0; i < input.size(); i++) {
            for (int j = i + 1; j < input.size(); j++) {
                if (differWithExactly1Letter(input.get(i), input.get(j))) {
                    return getCommonLetters(input.get(i), input.get(j));
                }
            }
        }

        return "";
    }

    private static Map<Integer, Long> countLetters(String word) {
        return word.chars()
                .boxed()
                .collect(groupingBy(identity(), counting()));
    }

    private static boolean differWithExactly1Letter(String word1, String word2) {
        int differentLetters = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                differentLetters++;
            }
            if (differentLetters > 1) {
                return false;
            }
        }
        return true;
    }

    private static String getCommonLetters(String word1, String word2) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) == word2.charAt(i)) {
                result.append(word1.charAt(i));
            }
        }
        return result.toString();
    }
}
