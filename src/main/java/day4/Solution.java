package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.valueOf;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.toList;

public abstract class Solution {

    public int findSolution(String fileName) throws IOException {
        List<String> inputLines = getInputLines(fileName);

        Map<Integer, List<Pair<Integer, Integer>>> guardsSleepingIntervals = getGuardsSleepingIntervals(inputLines);
        Map<Integer, List<Integer>> guardsSleepingMinutes = getGuardsSleepingMinutes(guardsSleepingIntervals);

        int mostSleepyGuard = findMostSleepyGuard(guardsSleepingIntervals);
        int mostPreferredMinuteToSleep = findMostPreferredMinuteToSleep(guardsSleepingMinutes, mostSleepyGuard);

        return mostSleepyGuard * mostPreferredMinuteToSleep;
    }

    private List<String> getInputLines(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
                .sorted()
                .collect(toList());
    }

    protected abstract int findMostSleepyGuard(Map<Integer, List<Pair<Integer, Integer>>> guardsSleepingIntervals);

    protected int findMostPreferredMinuteToSleep(Map<Integer, List<Integer>> guardsSleepingMinutes, int mostSleepyGuard) {
        return guardsSleepingMinutes.get(mostSleepyGuard).stream()
                .sorted()
                .collect(Collectors.groupingBy(identity(), counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .get()
                .getKey();
    }

    private Map<Integer, List<Pair<Integer, Integer>>> getGuardsSleepingIntervals(List<String> input) {
        Map<Integer, List<Pair<Integer, Integer>>> guardSleepingIntervals = new HashMap<>();
        int guardId = -1;
        for (String event: input) {
            LocalDateTime time = parse(event.substring(event.indexOf("[") + 1, event.indexOf("]")), ofPattern("yyyy-MM-dd HH:mm"));

            if (event.endsWith("begins shift")) {
                guardId = valueOf(event.substring(event.indexOf("#") + 1, event.indexOf(" ", event.indexOf("#"))));
                guardSleepingIntervals.putIfAbsent(guardId, new ArrayList<>());
            } else if (event.endsWith("falls asleep")) {
                Pair<Integer, Integer> sleepingInterval = new Pair<>();
                sleepingInterval.setKey(time.getMinute());
                guardSleepingIntervals.get(guardId).add(sleepingInterval);
            } else {
                List<Pair<Integer, Integer>> sleepingIntervals = guardSleepingIntervals.get(guardId);
                Pair<Integer, Integer> lastSleepingInterval = sleepingIntervals.get(sleepingIntervals.size() - 1);
                lastSleepingInterval.setValue(time.getMinute());
            }
        }
        return guardSleepingIntervals;
    }

    protected Map<Integer, List<Integer>> getGuardsSleepingMinutes(Map<Integer, List<Pair<Integer, Integer>>> guardSleepingIntervals) {
        Map<Integer, List< Integer>> guardSleepingMinutes = new HashMap<>();

        guardSleepingIntervals.forEach((guardId, sleepingIntervals) -> {
            guardSleepingMinutes.putIfAbsent(guardId, new ArrayList<>());
            sleepingIntervals.stream()
                    .map(interval -> IntStream.range(interval.getKey(), interval.getValue()).boxed().collect(toList()))
                    .forEach(sleepingMinutes -> guardSleepingMinutes.get(guardId).addAll(sleepingMinutes));
        });

        return guardSleepingMinutes;
    }

}
