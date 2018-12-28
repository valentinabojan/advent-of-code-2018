package day25;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileNameTest1 = "src/main/resources/day25/input_test_part_1_1.txt";
        String fileNameTest2 = "src/main/resources/day25/input_test_part_1_2.txt";
        String fileNameTest3 = "src/main/resources/day25/input_test_part_1_3.txt";
        String fileNameTest4 = "src/main/resources/day25/input_test_part_1_4.txt";
        String fileName = "src/main/resources/day25/input.txt";

        System.out.println(part1(fileNameTest1));
        System.out.println(part1(fileNameTest2));
        System.out.println(part1(fileNameTest3));
        System.out.println(part1(fileNameTest4));
        System.out.println(part1(fileName));
    }

    private static int part1(String fileName) throws IOException {
        List<Coordinate4D> points = readPoints(fileName);

        List<List<Coordinate4D>> constellations = new ArrayList<>();

        for(Coordinate4D point : points) {
            List<List<Coordinate4D>> constellationsToMerge = new ArrayList<>();

            for (List<Coordinate4D> constellation : constellations) {
                for (int i = 0; i < constellation.size(); i++) {
                    if (getManhatanDistance(point, constellation.get(i)) <= 3) {
                        constellationsToMerge.add(constellation);
                        break;
                    }
                }
            }

            if (constellationsToMerge.size() == 0) {
                List<Coordinate4D> newConstellation = new ArrayList<>();
                newConstellation.add(point);
                constellations.add(newConstellation);
            } else if(constellationsToMerge.size() == 1) {
                constellationsToMerge.get(0).add(point);
            } else {
                constellationsToMerge.get(0).add(point);
                for(int i = 1; i < constellationsToMerge.size(); i++) {
                    constellationsToMerge.get(0).addAll(constellationsToMerge.get(i));
                }
                for(int i = 1; i < constellationsToMerge.size(); i++) {
                    constellations.remove(constellationsToMerge.get(i));
                }
            }

        }

        return constellations.size();
    }

    private static List<Coordinate4D> readPoints(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
                .map(line -> {
                    String[] coordinateStr = line.split(",");
                    return new Coordinate4D(coordinateStr[0], coordinateStr[1], coordinateStr[2], coordinateStr[3]);
                })
                .collect(Collectors.toList());
    }

    private static int getManhatanDistance(Coordinate4D point1, Coordinate4D point2) {
        return Math.abs(point1.getX() - point2.getX()) +
                Math.abs(point1.getY() - point2.getY()) +
                Math.abs(point1.getZ() - point2.getZ()) +
                Math.abs(point1.getW() - point2.getW());
    }

}
