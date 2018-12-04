package day3;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileNameTest = "src/main/resources/day3/input_test.txt";
        String fileName = "src/main/resources/day3/input.txt";

        System.out.println(new PartOne().findSolution(fileNameTest));
        System.out.println(new PartOne().findSolution(fileName));

        System.out.println(new PartTwo().findSolution(fileNameTest));
        System.out.println(new PartTwo().findSolution(fileName));
    }

}
