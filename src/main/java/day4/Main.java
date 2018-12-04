package day4;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileNameTest = "src/main/resources/day4/input_test.txt";
        String fileName = "src/main/resources/day4/input.txt";

        System.out.println(new StrategyOne().findSolution(fileNameTest));
        System.out.println(new StrategyOne().findSolution(fileName));

        System.out.println(new StrategyTwo().findSolution(fileNameTest));
        System.out.println(new StrategyTwo().findSolution(fileName));
    }
}
