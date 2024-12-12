import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day05 {

    public static void main(String[] args) {
        File file = new File("2024/day05/input.txt");
        Map<Integer, Set<Integer>> rules = new HashMap<>();
        List<List<Integer>> updates = new ArrayList<>();
        parseInputFile(file, rules, updates);

        int middleSumPart1 = 0;
        int middleSumPart2 = 0;
        for (List<Integer> update : updates) {
            int middleIndex = update.size() / 2;
            if (isValid(rules, update)) {
                middleSumPart1 += update.get(middleIndex);
            } else {
                Collections.sort(update, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer before, Integer after) {
                        return rules.getOrDefault(before, new LinkedHashSet<>()).contains(after) ? -1 : 1;
                    }
                });
                middleSumPart2 += update.get(middleIndex);
            }
        }
        System.out.println("PART I: " + middleSumPart1);
        System.out.println("PART II: " + middleSumPart2);
    }

    private static void parseInputFile(File file, Map<Integer, Set<Integer>> rules, List<List<Integer>> updates) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isParsingUpdates = false;

            while ((line = br.readLine()) != null) {
                if (line.equals("")) {
                    isParsingUpdates = true;
                    continue;
                }
                if (!isParsingUpdates) {
                    parseRule(line, rules);
                } else {
                    parseUpdate(line, updates);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseRule(String line, Map<Integer, Set<Integer>> rules) {
        int[] parts = Arrays.stream(line.split("\\|")).mapToInt(Integer::parseInt).toArray();
        Set<Integer> successors = rules.getOrDefault(parts[0], new LinkedHashSet<>());
        successors.add(parts[1]);
        rules.put(parts[0], successors);
    }

    private static void parseUpdate(String line, List<List<Integer>> updates) {
        List<Integer> update = Arrays.stream(line.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        updates.add(update);
    }

    private static boolean isValid(Map<Integer, Set<Integer>> rules, List<Integer> pages) {
        for (int i = 0; i < pages.size() - 1; i++) {
            if (!rules.getOrDefault(pages.get(i), new LinkedHashSet<>()).contains(pages.get(i + 1))) {
                return false;
            }
        }
        return true;
    }
}
