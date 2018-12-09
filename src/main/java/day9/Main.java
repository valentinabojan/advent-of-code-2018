package day9;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
//      10 players; last marble is worth 1618 points: high score is 8317
//      13 players; last marble is worth 7999 points: high score is 146373
//      17 players; last marble is worth 1104 points: high score is 2764
//      21 players; last marble is worth 6111 points: high score is 54718
//      30 players; last marble is worth 5807 points: high score is 37305
        System.out.println(winningScoreForMarbleGame(9, 25));
        System.out.println(winningScoreForMarbleGame(10, 1618));
        System.out.println(winningScoreForMarbleGame(13, 7999));
        System.out.println(winningScoreForMarbleGame(17, 1104));
        System.out.println(winningScoreForMarbleGame(21, 6111));
        System.out.println(winningScoreForMarbleGame(30, 5807));

//      424 players; last marble is worth 71482 points: high score is 408679
        System.out.println(winningScoreForMarbleGame(424, 71482));

//      424 players; last marble is worth 71482 * 100 points: high score is 3443939356
        System.out.println(winningScoreForMarbleGame(424, 71482 * 100));
    }

    private static long winningScoreForMarbleGame(int players, int marbles) {
        List<Long> scores = Stream.iterate(0L, i -> 0L).limit(players).collect(Collectors.toList());

        LinkedList<Integer> circle = new LinkedList<>();
        circle.add(0);
        ListIterator<Integer> currentMarble = circle.listIterator();

        int nextMarble = 1;

        boolean isGameOver = false;
        while(!isGameOver) {
            for (int i = 0; i < players; i++) {
                if (nextMarble % 23 == 0) {
                    scores.set(i, scores.get(i) + nextMarble);
                    currentMarble = moveMarble7PositionsCounterClockwise(circle, currentMarble);
                    scores.set(i, scores.get(i) + currentMarble.next());
                    currentMarble = removeMarble(circle, currentMarble);
                } else {
                    currentMarble = moveMarble2PositionsClockwise(circle, currentMarble, nextMarble);
                }

                nextMarble++;
                if (nextMarble > marbles) {
                    isGameOver = true;
                    break;
                }
            }
        }

        return scores.stream().mapToLong(i -> i).max().getAsLong();
    }

    private static ListIterator<Integer> removeMarble(LinkedList<Integer> circle, ListIterator<Integer> currentMarble) {
        currentMarble.remove();
        if (currentMarble.hasNext()) {
            currentMarble.next();
        } else {
            currentMarble = circle.listIterator(1);
        }
        return currentMarble;
    }

    private static ListIterator<Integer> moveMarble7PositionsCounterClockwise(LinkedList<Integer> circle, ListIterator<Integer> currentMarble) {
        for (int j = 0; j < 8; j++) {
            if (currentMarble.hasPrevious()) {
                currentMarble.previous();
            } else {
                currentMarble = circle.listIterator(circle.size() - 1);
            }
        }
        return currentMarble;
    }

    private static ListIterator<Integer> moveMarble2PositionsClockwise(LinkedList<Integer> circle, ListIterator<Integer> currentMarble, int nextMarble) {
        if (currentMarble.hasNext()) {
            currentMarble.next();
            currentMarble.add(nextMarble);
        } else {
            circle.add(1, nextMarble);
            currentMarble = circle.listIterator(1);
            currentMarble.next();
        }
        return currentMarble;
    }

}
