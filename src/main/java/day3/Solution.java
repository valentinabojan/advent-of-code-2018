package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class Solution {

    public int findSolution(String fileName) throws IOException {
        List<Claim> claims = buildClaims(fileName);

        int rectangleWidth = getRectangleWidth(claims);
        int rectangleHeight = getRectangleHeight(claims);

        String[][] rectangle = createRectangle(rectangleWidth, rectangleHeight);
        completeRectangleWithCanvas(rectangle, claims);

        return getPuzzlePartSolution(rectangle, claims);
    }

    protected abstract int getPuzzlePartSolution(String[][] rectangle, List<Claim> claims);

    private List<Claim> buildClaims(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
            .map(claimString -> {
                int idStartDelimiter = claimString.indexOf("#");
                int offsetStartDelimiter = claimString.indexOf(" @ ");
                int areaStartDelimiter = claimString.indexOf(": ");
                String claimId = claimString.substring(idStartDelimiter + 1, offsetStartDelimiter);
                String[] offsets = claimString.substring(offsetStartDelimiter + 3, areaStartDelimiter).split(",");
                String[] dimensions = claimString.substring(areaStartDelimiter + 2).split("x");
                return new Claim(
                        Integer.valueOf(claimId),
                        Integer.valueOf(offsets[0]),
                        Integer.valueOf(offsets[1]),
                        Integer.valueOf(dimensions[0]),
                        Integer.valueOf(dimensions[1])
                );
            })
            .collect(toList());
    }

    private int getRectangleWidth(List<Claim> claims) {
        int rectangleWidth = 0;
        for (Claim claim : claims) {
            int claimRightOffset = claim.getLeftOffset() + claim.getWidth();
            if (rectangleWidth < claimRightOffset) {
                rectangleWidth = claimRightOffset;
            }
        }
        return rectangleWidth;
    }

    private int getRectangleHeight(List<Claim> claims) {
        int rectangleHeight = 0;
        for (Claim claim : claims) {
            int claimBottomOffset = claim.getTopOffset() + claim.getHeight();
            if (rectangleHeight < claimBottomOffset) {
                rectangleHeight = claimBottomOffset;
            }
        }
        return rectangleHeight;
    }

    private String[][] createRectangle(int rectangleWidth, int rectangleHeight) {
        String[][] rectangle = new String[rectangleHeight][rectangleWidth];
        for (int i = 0; i < rectangleHeight; i++) {
            for (int j = 0; j < rectangleWidth; j++) {
                rectangle[i][j] = ".";
            }
        }
        return rectangle;
    }

    private void completeRectangleWithCanvas(String[][] rectangle, List<Claim> claims) {
        for (Claim claim : claims) {
            for (int i = claim.getTopOffset(); i < claim.getTopOffset() + claim.getHeight(); i++) {
                for (int j = claim.getLeftOffset(); j < claim.getLeftOffset() + claim.getWidth(); j++) {
                    if (".".equals(rectangle[i][j])) {
                        rectangle[i][j] = claim.getId() + "";
                    } else {
                        rectangle[i][j] = "X";
                    }
                }
            }
        }
    }
}
