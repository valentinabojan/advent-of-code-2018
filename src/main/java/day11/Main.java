package day11;

import day4.Pair;

public class Main {

    private static final int GRID_DIMENSION = 300;

    public static void main(String[] args) {
        System.out.println(part1(18));
        System.out.println(part1(42));
        System.out.println(part1(9995));

        System.out.println(part2(18));
        System.out.println(part2(42));
        System.out.println(part2(9995));
    }

    private static Pair<Integer, Integer> part1(int gridSerialNumber) {
        int squareDimension = 3;
        int[][] grid = new int[GRID_DIMENSION][GRID_DIMENSION];

        computePowerGrid(gridSerialNumber, grid);

        int maxSquareSum = Integer.MIN_VALUE;
        Pair<Integer, Integer> coordinate = null;
        for (int i = 0; i < GRID_DIMENSION - squareDimension; i++) {
            for (int j = 0; j < GRID_DIMENSION - squareDimension; j++) {
                int squareSum = 0;
                for (int k = 0; k < squareDimension; k++) {
                    for (int l = 0; l < squareDimension; l++) {
                        squareSum += grid[i + k][j + l];
                    }
                }

                if (squareSum > maxSquareSum) {
                    maxSquareSum = squareSum;
                    coordinate = new Pair<>(j + 1, i + 1);
                }
            }
        }

        return coordinate;
    }

    private static Pair<Pair<Integer, Integer>, Integer> part2(int gridSerialNumber) {
        int[][] grid = new int[GRID_DIMENSION][GRID_DIMENSION];

        computePowerGrid(gridSerialNumber, grid);

        int maxSquareSum = Integer.MIN_VALUE;
        Pair<Integer, Integer> coordinate = null;
        int maxSquareSize = -1;
        int[][] sumMatrix = new int[GRID_DIMENSION][GRID_DIMENSION];

        for (int squareDimension = 1; squareDimension <= GRID_DIMENSION; squareDimension++) {
            for (int i = 0; i < GRID_DIMENSION - squareDimension; i++) {
                for (int j = 0; j < GRID_DIMENSION - squareDimension; j++) {

                    int additionalSum = 0;
                    for (int k = 0; k < squareDimension; k++) {
                        if (k != squareDimension - 1) {
                            additionalSum += grid[i + squareDimension - 1][j + k];
                            additionalSum += grid[i + k][j + squareDimension - 1];
                        } else {
                            additionalSum += grid[i + squareDimension - 1][j + k];
                        }
                    }

                    sumMatrix[i][j] += additionalSum;

                    if (sumMatrix[i][j] > maxSquareSum) {
                        maxSquareSum = sumMatrix[i][j];
                        coordinate = new Pair<>(j + 1, i + 1);
                        maxSquareSize = squareDimension;
                    }
                }
            }
        }

        return new Pair<>(coordinate, maxSquareSize);
    }

    private static void computePowerGrid(int gridSerialNumber, int[][] grid) {
        for (int i = 0; i < GRID_DIMENSION; i++) {
            for (int j = 0; j < GRID_DIMENSION; j++) {
                int rackId = (j + 1) + 10;
                int powerLevel = (i + 1) * rackId;
                powerLevel += gridSerialNumber;
                powerLevel *= rackId;
                powerLevel = (powerLevel / 100) % 10;
                powerLevel -= 5;

                grid[i][j] = powerLevel;
            }
        }
    }
}
