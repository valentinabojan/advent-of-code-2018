package day17;


import day10.Coordinate;
import day16.Function;
import day4.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) throws IOException {
        String fileNameTest = "src/main/resources/day17/input_test.txt";
        String fileName = "src/main/resources/day17/input.txt";

        System.out.println(part1(fileNameTest));
//        System.out.println(part1(fileName));
//
//        System.out.println(part2(fileName));
    }

    private static int part1(String fileName) throws IOException {
        List<Coordinate> coordinates = readClayCoordinates(fileName);
        Coordinate maxCoordinate = findMaxCoordinate(coordinates);

        char[][] grid = createClayGrid(coordinates);

        printMatrix(grid, maxCoordinate.getY() + 1, maxCoordinate.getX() + 1);

        Queue<Coordinate> waterQueue = new PriorityQueue<>();
        waterQueue.offer(new Coordinate(500, 0));
        grid[0][500] = '+';

        while (!waterQueue.isEmpty()) {
            Coordinate water = waterQueue.poll();

            if (grid[water.getY()][water.getX()] == '+' || grid[water.getY()][water.getX()] == '|') {

                if (water.getY() + 1 < maxCoordinate.getY() && grid[water.getY() + 1][water.getX()] == '.') {
                    grid[water.getY() + 1][water.getX()] = '|';
                } else if (true) {

                }
            }
        }



        return -1;
    }

    private static int part2(String fileName) throws IOException {
        return -1;
    }

    private static char[][] createClayGrid(List<Coordinate> coordinates) {
        Coordinate maxCoordinate = findMaxCoordinate(coordinates);

        char[][] grid = new char[maxCoordinate.getY() + 1][maxCoordinate.getX() + 1];
        for (int i = 0; i < maxCoordinate.getY() + 1; i++) {
            for (int j = 0; j < maxCoordinate.getX() + 1; j++) {
                grid[i][j] = '.';
            }
        }
        for (Coordinate c : coordinates) {
            grid[c.getY()][c.getX()] = '#';
        }

        return grid;
    }

    private static Coordinate findMaxCoordinate(List<Coordinate> coordinates) {
        Coordinate maxCoordinate = new Coordinate(Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (Coordinate c : coordinates) {
            if (c.getX() > maxCoordinate.getX()) {
                maxCoordinate.setX(c.getX());
            }
            if (c.getY() > maxCoordinate.getY()) {
                maxCoordinate.setY(c.getY());
            }
        }

        return maxCoordinate;
    }

    private static void printMatrix(char[][] landscape, int height, int width) {
        for (int i = 0; i < height; i++) {
            for (int j = 494; j < width; j++) {
                System.out.print(landscape[i][j]);
            }
            System.out.println();
        }
    }

    private static List<Coordinate> readClayCoordinates(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
                .map(line -> {
                    int first = Integer.valueOf(line.substring(line.indexOf("=") + 1, line.indexOf(",")));
                    int second = Integer.valueOf(line.substring(line.lastIndexOf("=") + 1, line.indexOf(".")));
                    int third = Integer.valueOf(line.substring(line.lastIndexOf(".") + 1));

                    List<Coordinate> coordinates = new ArrayList<>();

                    if (line.startsWith("x")) {
                        for (int y = second; y <= third; y++) {
                            coordinates.add(new Coordinate(first, y));
                        }
                    } else {
                        for (int x = second; x <= third; x++) {
                            coordinates.add(new Coordinate(x, first));
                        }
                    }

                    return coordinates;
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

}
