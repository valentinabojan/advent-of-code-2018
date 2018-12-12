package day12;


import day4.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileNameTest = "src/main/resources/day12/input_test.txt";
        String fileName = "src/main/resources/day12/input.txt";

        System.out.println(solution(fileNameTest, 20));

        System.out.println(solution(fileName, 20));

        System.out.println(solution(fileName, 201) - solution(fileName, 200));
        System.out.println(solution(fileName, 202) - solution(fileName, 201));
        System.out.println(solution(fileName, 203) - solution(fileName, 202));

        int sumDelta = solution(fileName, 201) - solution(fileName, 200);
        System.out.println(solution(fileName, 200) + sumDelta * (50_000_000_000L - 200));
    }

    private static int solution(String fileName, long generations) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        String initialState = lines.get(0).substring(lines.get(0).indexOf(":") + 2);
        Map<String, String> rules = getRules(lines.subList(2, lines.size()));
        int offset = 0;

        for (long g = 0; g < generations; g++) {
            if(initialState.substring(0, 4).contains("#")) {
                initialState = "...." + initialState;
                offset += 4;
            }
            if (initialState.substring(initialState.length() - 4).contains("#")) {
                initialState += "....";
            }

            initialState = computeNewState(initialState, rules);
        }

        return computeSum(initialState, offset);
    }

    private static String computeNewState(String initialState, Map<String, String> rules) {
        String state = initialState;

        for (int i = 2; i < initialState.length() - 2; i++) {
            String pots = initialState.substring(i - 2, i + 3);
            state = state.substring(0, i) + rules.getOrDefault(pots, ".") + state.substring(i + 1);
        }
        return state;
    }

    private static Map<String, String> getRules(List<String> lines) {
        return lines.stream()
                .map(line -> {
                    String rule = line.substring(0, line.indexOf(" =>"));
                    String existPlant = line.charAt(line.length() - 1) + "";

                    return new Pair<>(rule, existPlant);
                })
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private static int computeSum(String initialState, int offset) {
        int sum = 0;
        for (int i = 0; i < initialState.length(); i++) {
            if (initialState.charAt(i) == '#') {
                sum += i - offset;
            }
        }
        return sum;
    }
}
