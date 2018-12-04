package day3;

import java.util.List;

public class PartTwo extends Solution {

    @Override
    protected int getPuzzlePartSolution(String[][] rectangle, List<Claim> claims) {
        return getNonOverlappingClaimId(rectangle, claims);
    }

    private int getNonOverlappingClaimId(String[][] rectangle, List<Claim> claims) {
        for (Claim claim : claims) {
            boolean isUnique = true;
            for (int i = claim.getTopOffset(); i < claim.getTopOffset() + claim.getHeight(); i++) {
                for (int j = claim.getLeftOffset(); j < claim.getLeftOffset() + claim.getWidth(); j++) {
                    if ("X".equals(rectangle[i][j])) {
                        isUnique = false;
                    }
                }
            }
            if (isUnique) {
                return claim.getId();
            }
        }
        throw new IllegalStateException();
    }
}
