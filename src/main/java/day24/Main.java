package day24;


import day4.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;


public class Main {

    public static void main(String[] args) throws IOException {
        String fileNameTest1 = "src/main/resources/day24/input_test_part_1.txt";
        String fileNameTest2 = "src/main/resources/day24/input_test_part_2.txt";
        String fileName = "src/main/resources/day24/input.txt";

        System.out.println(part1(fileNameTest1));
        System.out.println(part1(fileNameTest2));
        System.out.println(part1(fileName));

        System.out.println(part2(fileNameTest1));
        System.out.println(part2(fileName));
    }

    private static int part1(String fileName) throws IOException {
        List<Group> groups = readGroups(fileName);

        return fight(groups).getValue();
    }

    private static int part2(String fileName) throws IOException {
        int boost = 0;
        while (true) {
            int finalBoost = boost;
            List<Group> reference = readGroups(fileName);
            List<Group> groups = reference.stream()
                    .map(r -> {
                        if (r.getArmy() == Army.IMUNE_SYSTEM) {
                            return new Group(r.getArmy(), r.getUnits(), r.getHitPoints(), r.getAttackDamage() + finalBoost,
                                    r.getInitiative(), r.getAttackType(), r.getWeaknesses(), r.getImmunities());
                        } else {
                            return r;
                        }
                    })
                    .collect(Collectors.toList());

            Pair<Army, Integer> result = fight(groups);
            if (result!= null && result.getKey() == Army.IMUNE_SYSTEM) {
                return result.getValue();
            }
            boost++;
        }
    }

    private static Pair<Army, Integer> fight(List<Group> groups) {
        while (!isGameOver(groups)) {
            List<Group> groupsToChoose = groups.stream()
                    .sorted(comparing(Group::getEffectivePower, Comparator.reverseOrder())
                            .thenComparing(Group::getInitiative, Comparator.reverseOrder()))
                    .collect(Collectors.toList());

            List<Pair<Group, Group>> selections = new ArrayList<>();

            for (Group attackingGroup : groupsToChoose) {
                List<Group> selectedTargets = selections.stream().map(Pair::getValue).collect(Collectors.toList());

                List<Group> targets = groups.stream()
                        .filter(g -> g.getArmy() != attackingGroup.getArmy())
                        .filter(g -> !selectedTargets.contains(g))
                        .collect(Collectors.toList());
                if (targets.isEmpty()) {
                    continue;
                }

                List<Pair<Group, Integer>> chosenTarget = targets.stream()
                        .map(t -> new Pair<>(t, computeDamage(attackingGroup, t)))
                        .sorted(Comparator.<Pair<Group, Integer>, Integer>comparing(Pair::getValue, Comparator.reverseOrder())
                                .thenComparing(p -> p.getKey().getEffectivePower(), Comparator.reverseOrder())
                                .thenComparing(p -> p.getKey().getInitiative(), Comparator.reverseOrder()))
                        .collect(Collectors.toList());
                if (chosenTarget.get(0).getValue() != 0) {
                    selections.add(new Pair<>(attackingGroup, chosenTarget.get(0).getKey()));
                }
            }

            List<Pair<Group, Group>> attackGroups = selections.stream()
                    .sorted(Comparator.comparing(p -> p.getKey().getInitiative(), Comparator.reverseOrder()))
                    .collect(Collectors.toList());

            int deadths = 0;
            for (Pair<Group, Group> attackPair : attackGroups) {
                if (attackPair.getKey().getUnits() > 0) {
                    int damage = computeDamage(attackPair.getKey(), attackPair.getValue());

                    int deadUnits = damage / attackPair.getValue().getHitPoints();
                    if (deadUnits > 0) {
                        deadths++;
                    }
                    int healthyUnits = attackPair.getValue().getUnits() - deadUnits;
                    attackPair.getValue().setUnits(healthyUnits);
                }
            }
            if (deadths == 0) {
                return null;
            }
            groups.removeIf(g -> g.getUnits() <= 0);
        }

        return new Pair<>(groups.get(0).getArmy(), groups.stream().mapToInt(Group::getUnits).sum());
    }

    private static boolean isGameOver(List<Group> groups) {
        Map<Army, Long> map = groups.stream()
                .collect(groupingBy(Group::getArmy, counting()));

        return map.keySet().size() < 2;

    }

    private static int computeDamage(Group attackingGroup, Group defendingGroup) {
        if (defendingGroup.getImmunities().contains(attackingGroup.getAttackType())) {
            return 0;
        } else if (defendingGroup.getWeaknesses().contains(attackingGroup.getAttackType())) {
            return 2 * attackingGroup.getEffectivePower();
        } else {
            return attackingGroup.getEffectivePower();
        }
    }

    private static List<Group> readGroups(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        List<Group> groups = new ArrayList<>();

        int idx = 1;
        while (!lines.get(idx).isEmpty()) {
            groups.add(parseGroup(lines.get(idx), Army.IMUNE_SYSTEM));
            idx++;
        }
        idx += 2;
        while (idx < lines.size()) {
            groups.add(parseGroup(lines.get(idx), Army.INFECTION));
            idx++;
        }

        return groups;
    }

    private static Group parseGroup(String groupLine, Army army) {
        int units = Integer.parseInt(groupLine.substring(0, groupLine.indexOf(" ")));
        int hitPoints = Integer.parseInt(groupLine.substring(groupLine.indexOf("with ") + 5, groupLine.indexOf(" hit")));

        String lastPart = groupLine.substring(groupLine.indexOf("does ") + 5);
        int attackDamage = Integer.parseInt(lastPart.substring(0, lastPart.indexOf(" ")));
        String attackType = lastPart.substring(lastPart.indexOf(" ") + 1, lastPart.indexOf("damage") - 1);
        int initiative = Integer.parseInt(lastPart.substring(lastPart.lastIndexOf(" ") + 1));

        List<String> weaknesses = new ArrayList<>();
        List<String> immunities = new ArrayList<>();

        if (groupLine.contains("(")) {
            String middle = groupLine.substring(groupLine.indexOf("(") + 1, groupLine.indexOf(")"));
            String[] strings = middle.split(";");

            if (strings[0].startsWith("weak")) {
                String[] weaknessesStr = strings[0].substring(strings[0].indexOf("weak to ") + 8).split(",");
                for(String weakness : weaknessesStr) {
                    weaknesses.add(weakness.trim());
                }

                if (strings.length == 2) {
                    String[] immunitiesStr = strings[1].substring(strings[1].indexOf("immune to ") + 10).split(",");
                    for (String immunity : immunitiesStr) {
                        immunities.add(immunity.trim());
                    }
                }
            } else {
                String[] immunitiesStr = strings[0].substring(strings[0].indexOf("immune to ") + 10).split(",");
                for (String immunity : immunitiesStr) {
                    immunities.add(immunity.trim());
                }

                if (strings.length == 2) {
                    String[] weaknessesStr = strings[1].substring(strings[1].indexOf("weak to ") + 8).split(",");
                    for(String weakness : weaknessesStr) {
                        weaknesses.add(weakness.trim());
                    }
                }
            }


        }

        return new Group(army, units, hitPoints, attackDamage, initiative, attackType, weaknesses, immunities);
    }

}
