package day13;


import day10.Coordinate;
import day4.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileNameTestPart1 = "src/main/resources/day13/input_test_part_1.txt";
        String fileNameTestPart2 = "src/main/resources/day13/input_test_part_2.txt";
        String fileName = "src/main/resources/day13/input.txt";

        System.out.println(part1(fileNameTestPart1));
        System.out.println(part1(fileName));

        System.out.println(part2(fileNameTestPart2));
        System.out.println(part2(fileName));
    }

    private static Coordinate part1(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        char[][] grid = createGrid(lines);
        List<Player> players = createPlayers(lines);

        while(true) {
            for (Player player : players) {
                makePlayerMovesPerTick(grid, player);
            }

            Map<Coordinate, Long> groupedCoordinates = players.stream()
                    .map(Player::getCoordinate)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            for (Map.Entry<Coordinate, Long> e: groupedCoordinates.entrySet()) {
                if (e.getValue() > 1) {
                    return e.getKey();
                }
            }
        }
    }

    private static Coordinate part2(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        char[][] grid = createGrid(lines);
        List<Player> players = createPlayers(lines);

        while(true) {
            players = sortPlayers(players);
            List<Player> copy = new ArrayList<>(players);

            for (Player player : players) {
                makePlayerMovesPerTick(grid, player);

                List<Player> crashedPlayers = players.stream()
                        .filter(p -> p.getCoordinate().equals(player.getCoordinate()))
                        .collect(Collectors.toList());
                if (crashedPlayers.size() > 1) {
                    copy.removeIf(p -> p.getCoordinate().equals(player.getCoordinate()));
                }
            }
            players = copy;

            if (players.size() == 1) {
                return players.get(0).getCoordinate();
            }
        }
    }

    private static List<Player> sortPlayers(List<Player> players) {
        Comparator<Player> comparator = Comparator.comparing(p -> p.getCoordinate().getX());
        comparator = comparator.thenComparing(p -> p.getCoordinate().getY());

        return players.stream().sorted(comparator).collect(Collectors.toList());
    }

    private static void makePlayerMovesPerTick(char[][] grid, Player player) {
        Pair<Coordinate, Character> nextPositionAndDirection = getNextPositionAndDirection(player.getCurrentDirection(), player.getCoordinate(), grid);
        if (nextPositionAndDirection != null) {
            player.setCoordinate(nextPositionAndDirection.getKey());
            player.setCurrentDirection(nextPositionAndDirection.getValue());
        }

        if (grid[player.getCoordinate().getY()][player.getCoordinate().getX()] == '+') {
            Pair<Direction, Character> directionCharacterPair = computeNextDirectionAtIntersection(player.getLastDirectionAtIntersection(), player.getCurrentDirection());
            player.setLastDirectionAtIntersection(directionCharacterPair.getKey());
            player.setCurrentDirection(directionCharacterPair.getValue());
        }
    }

    private static Pair<Direction, Character> computeNextDirectionAtIntersection(Direction lastDirectionAtIntersection, char currentDirection) {
        Direction newDirection = getNextDirection(lastDirectionAtIntersection);
        if (newDirection == Direction.LEFT) {
            if (currentDirection == 'v') {
                return new Pair<>(newDirection, '>');
            } else if (currentDirection == '^') {
                return new Pair<>(newDirection, '<');
            } else if (currentDirection == '>') {
                return new Pair<>(newDirection, '^');
            } else if (currentDirection == '<') {
                return new Pair<>(newDirection, 'v');
            }
        } else if (newDirection == Direction.RIGHT) {
            if (currentDirection == 'v') {
                return new Pair<>(newDirection, '<');
            } else if (currentDirection == '^') {
                return new Pair<>(newDirection, '>');
            } else if (currentDirection == '>') {
                return new Pair<>(newDirection, 'v');
            } else if (currentDirection == '<') {
                return new Pair<>(newDirection, '^');
            }
        }

        return new Pair<>(newDirection, currentDirection);
    }

    private static Pair<Coordinate, Character> getNextPositionAndDirection(char playerDirection, Coordinate playerCoordinates, char[][] grid) {
        int x = playerCoordinates.getX();
        int y = playerCoordinates.getY();

        if (playerDirection == 'v') {
            if (grid[y + 1][x] == '/') {
                return new Pair<>(new Coordinate(x, y + 1), '<');
            } else if (grid[y + 1][x] == '\\') {
                return new Pair<>(new Coordinate(x, y + 1), '>');
            } else {
                return new Pair<>(new Coordinate(x, y + 1), playerDirection);
            }
        } else if (playerDirection == '^') {
            if (grid[y - 1][x] == '/') {
                return new Pair<>(new Coordinate(x, y - 1), '>');
            } else if (grid[y - 1][x] == '\\') {
                return new Pair<>(new Coordinate(x, y - 1), '<');
            } else {
                return new Pair<>(new Coordinate(x, y - 1), playerDirection);
            }
        } else if (playerDirection == '>') {
            if (grid[y][x + 1] == '/') {
                return new Pair<>(new Coordinate(x + 1, y), '^');
            } else if (grid[y][x + 1] == '\\') {
                return new Pair<>(new Coordinate(x + 1, y), 'v');
            } else {
                return new Pair<>(new Coordinate(x + 1, y), playerDirection);
            }
        } else if (playerDirection == '<') {
            if (grid[y][x - 1] == '/') {
                return new Pair<>(new Coordinate(x - 1, y), 'v');
            } else if (grid[y][x - 1] == '\\') {
                return new Pair<>(new Coordinate(x - 1, y), '^');
            } else {
                return new Pair<>(new Coordinate(x - 1, y), playerDirection);
            }
        }

        return null;
    }

    private static List<Player> createPlayers(List<String> lines) {
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(0).length(); j++) {
                if (isPlayer(lines.get(i).charAt(j))) {
                    players.add(new Player(new Coordinate(j, i), lines.get(i).charAt(j), null));
                }
            }
        }
        return players;
    }

    private static char[][] createGrid(List<String> lines) {
        char[][] grid = new char[lines.size()][lines.get(0).length()];
        for(int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(0).length(); j++) {
                grid[i][j] = lines.get(i).charAt(j);
            }
        }
        return grid;
    }

    private static Direction getNextDirection(Direction currentDirection) {
        if (currentDirection == null) {
            return Direction.LEFT;
        }

        switch (currentDirection) {
            case LEFT: return Direction.STRAIGHT;
            case STRAIGHT: return Direction.RIGHT;
            case RIGHT: return Direction.LEFT;
            default: return null;
        }
    }

    private static boolean isPlayer(char c) {
        return c == '>' || c == '<' || c == '^' || c == 'v';
    }
}
