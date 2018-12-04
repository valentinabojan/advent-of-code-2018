package day4;

import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;

public class StrategyTwo extends Solution {

    @Override
    public int findMostSleepyGuard(Map<Integer, List<Pair<Integer, Integer>>> guardsSleepingIntervals) {
        return guardsSleepingIntervals
                .entrySet()
                .stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue().size()))
                .max(comparing(Pair::getValue))
                .get()
                .getKey();
    }
}
