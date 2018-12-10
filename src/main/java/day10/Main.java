package day10;


import day4.Pair;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.Files.lines;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileNameTest = "src/main/resources/day10/input_test.txt";
        String fileName = "src/main/resources/day10/input.txt";

        solution(fileNameTest, 8);
        System.out.println(solution(fileName, 10));
    }

    private static int solution(String fileName, int messageHeight) throws IOException {
        List<Pair<Coordinate, Coordinate>> coordinates = readFile(fileName);

        int time = 0;

        while (true) {
            Pair<Coordinate, Coordinate> boundaryOfNextPosition = getBoundaryOfNextPosition(coordinates, time);
            int minX = boundaryOfNextPosition.getKey().getX();
            int minY = boundaryOfNextPosition.getKey().getY();
            int maxX = boundaryOfNextPosition.getValue().getX();
            int maxY = boundaryOfNextPosition.getValue().getY();

            if (maxY - minY + 1 > messageHeight) {
                time++;
                continue;
            }

            int width = maxX - minX + 1;
            int height = maxY - minY + 1;
            String[][] matrix = new String[height][width];
            for (Pair<Coordinate, Coordinate> coordinate : coordinates) {
                Coordinate position = coordinate.getKey();
                Coordinate velocity = coordinate.getValue();

                int x = position.getX() + velocity.getX() * time - minX;
                int y = position.getY() + velocity.getY() * time - minY;

                matrix[y][x] = "#";
            }

            printMatrix(width, height, matrix);

            return time;
        }
    }

    private static void printMatrix(int width, int height, String[][] matrix) {
        for (int i = 0; i < height; i++) {
            System.out.println();
            for (int j = 0; j < width; j++) {
                if (matrix[i][j] != null ){
                    System.out.print(matrix[i][j]);
                } else {
                    System.out.print(".");
                }
            }
        }
        System.out.println();
    }

    private static List<Pair<Coordinate, Coordinate>> readFile(String fileName) throws IOException {
        return lines(Paths.get(fileName))
                .map(line -> {
                    String[] position = line.substring(line.indexOf("position=") + 10, line.indexOf(" velocity") - 1).trim().split(",");
                    String[] velocity = line.substring(line.indexOf("velocity=") + 10, line.length() - 1).trim().split(",");
                    return new Pair<>(new Coordinate(position[0], position[1]), new Coordinate(velocity[0], velocity[1]));
                })
                .collect(Collectors.toList());
    }

    private static Pair<Coordinate, Coordinate> getBoundaryOfNextPosition(List<Pair<Coordinate, Coordinate>> positions, int time) {
        int maxX = 0, maxY = 0, minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        for (Pair<Coordinate, Coordinate> input : positions) {
            Coordinate position = input.getKey();
            Coordinate velocity = input.getValue();

            int nextX = position.getX() + velocity.getX() * time;
            if (nextX > maxX) {
                maxX = nextX;
            }
            if (nextX < minX) {
                minX = nextX;
            }

            int nextY = position.getY() + velocity.getY() * time;
            if (nextY > maxY) {
                maxY = nextY;
            }
            if (nextY < minY) {
                minY = nextY;
            }
        }

        return new Pair<>(new Coordinate(minX, minY), new Coordinate(maxX, maxY));
    }
}
