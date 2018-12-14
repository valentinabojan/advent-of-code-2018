package day14;


import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        System.out.println(part1(9));
        System.out.println(part1(5));
        System.out.println(part1(18));
        System.out.println(part1(2018));
        System.out.println(part1(765071));

        System.out.println(part2("51589", 10_000));
        System.out.println(part2("01245", 10_000));
        System.out.println(part2("92510", 10_000));
        System.out.println(part2("59414", 10_000));
        System.out.println(part2("765071", 100_000_000));
    }

    private static String part1(int recipesToMake) {
        return generateRecipes(recipesToMake + 10)
                .subList(recipesToMake, recipesToMake + 10)
                .stream()
                .map(r -> r + "")
                .collect(Collectors.joining());
    }

    private static int part2(String recipesToCheck, int recipesToMake) {
        return generateRecipes(recipesToMake)
                .stream()
                .map(r -> r + "")
                .collect(Collectors.joining())
                .indexOf(recipesToCheck);
    }

    private static List<Integer> generateRecipes(int recipesToMake) {
        List<Integer> playersIndexes = new ArrayList<>(Arrays.asList(0, 1));
        List<Integer> recipes = new ArrayList<>(Arrays.asList(3, 7));

        while (recipes.size() < recipesToMake) {
            int sum = playersIndexes.stream().mapToInt(recipes::get).sum();
            if (sum >= 10) {
                recipes.add(sum / 10);
            }
            recipes.add(sum % 10);

            for (int i = 0; i < playersIndexes.size(); i++) {
                Integer currentIndex = playersIndexes.get(i);
                int newIndex = (currentIndex + recipes.get(currentIndex) + 1) % recipes.size();

                playersIndexes.set(i, newIndex);
            }
        }
        return recipes;
    }

}
