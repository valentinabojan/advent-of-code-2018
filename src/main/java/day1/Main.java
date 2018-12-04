package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) throws IOException {
//        String fileName = "src/main/resources/day1/input_test.txt";
        String fileName = "src/main/resources/day1/input.txt";

        System.out.println(computeTotalSum(fileName));
        System.out.println(findDuplicatedFrequency(fileName));
    }

    private static Integer findDuplicatedFrequency(String fileName) throws IOException {
        List<Integer> frequencies = new ArrayList<>();

        int frequency = 0;
        while (true) {
            List<String> elements = Files.lines(Paths.get(fileName)).collect(toList());

            for (String element: elements) {
                frequency += Integer.valueOf(element);
                if(frequencies.contains(frequency)) {
                    return frequency;
                }
                frequencies.add(frequency);
            }
        }
    }

    private static int computeTotalSum(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
                .mapToInt(Integer::valueOf)
                .reduce(0, Integer::sum);
    }

}
