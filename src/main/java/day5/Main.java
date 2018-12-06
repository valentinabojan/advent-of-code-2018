package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileNameTest = "src/main/resources/day5/input_test.txt";
        String fileName = "src/main/resources/day5/input.txt";

        System.out.println(part1(fileNameTest));
        System.out.println(part1(fileName));

        System.out.println(part2(fileNameTest));
        System.out.println(part2(fileName));
    }

    private static int part1(String fileName) throws IOException {
        String inputPolymer = Files.readAllLines(Paths.get(fileName)).get(0);
        return computeUnitsAfterPolymerReaction(inputPolymer);
    }

    private static int computeUnitsAfterPolymerReaction(String inputPolymer) {
        List<Character> stack = new ArrayList<>();
        for (int i = 0; i < inputPolymer.length(); i++) {
            if (!stack.isEmpty() && shouldUnitsReact(stack.get(stack.size() - 1), inputPolymer.charAt(i))) {
                stack.remove(stack.size() - 1);
            } else {
                stack.add(inputPolymer.charAt(i));
            }
        }
        return stack.size();
    }

    private static int part2(String fileName) throws IOException {
        String inputPolymer = Files.readAllLines(Paths.get(fileName)).get(0);
        int minPolymerLength = inputPolymer.length();

        for (Character unit: getUniquePolymerUnits(inputPolymer)) {
            String newPolymer = removePolymerUnit(inputPolymer, unit);
            int newPolymerLength = computeUnitsAfterPolymerReaction(newPolymer);

            if (newPolymerLength < minPolymerLength) {
                minPolymerLength = newPolymerLength;
            }
        }

        return minPolymerLength;
    }

    private static String removePolymerUnit(String inputPolymer, Character unit) {
        return inputPolymer
                .replaceAll(unit.toString().toLowerCase(), "")
                .replaceAll(unit.toString().toUpperCase(), "");
    }

    private static boolean shouldUnitsReact(char unit1, char unit2) {
        return ((isUpperCase(unit1) && isLowerCase(unit2)) || (isLowerCase(unit1) && isUpperCase(unit2))) &&
                (toLowerCase(unit1) == toLowerCase(unit2));
    }

    private static Set<Character> getUniquePolymerUnits(String inputPolymer) {
        Set<Character> polymerUnits = new HashSet<>();
        for (int i = 0; i < inputPolymer.length(); i++) {
            polymerUnits.add(Character.toLowerCase(inputPolymer.charAt(i)));
        }
        return polymerUnits;
    }

}
