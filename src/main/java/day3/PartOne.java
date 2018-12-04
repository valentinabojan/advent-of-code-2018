package day3;

import java.util.List;

public class PartOne extends Solution {

    @Override
    protected int getPuzzlePartSolution(String[][] rectangle, List<Claim> claims) {
        return getOverlappingInches(rectangle, rectangle[0].length, rectangle.length);
    }

    private int getOverlappingInches(String[][] rectangle, int rectangleWidth, int rectangleHeight) {
        int overlappingInches = 0;
        for (int i = 0; i < rectangleHeight; i++) {
            for (int j = 0; j < rectangleWidth; j++) {
                if ("X".equals(rectangle[i][j])) {
                    overlappingInches++;
                }
            }
        }
        return overlappingInches;
    }
}
