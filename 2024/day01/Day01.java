import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day01 {

    public static void main(String[] args) {
        File file = new File("2024/day01/input.txt");
        List<Integer> firstList = new ArrayList<>();
        List<Integer> secondList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines()
                    .map(line -> line.split("   "))
                    .forEach(parts -> {
                        firstList.add(Integer.parseInt(parts[0]));
                        secondList.add(Integer.parseInt(parts[1]));
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Answer part one: " + distance(firstList, secondList));
        System.out.println("Answer part two: " + similarity(firstList, secondList));
    }

    private static int distance(List<Integer> leftList, List<Integer> rightList) {
        Collections.sort(leftList);
        Collections.sort(rightList);
        int totalDistance = 0;
        for (int i = 0; i < leftList.size(); i++) {
            totalDistance += Math.abs(leftList.get(i) - rightList.get(i));
        }
        return totalDistance;
    }

    private static int similarity(List<Integer> firstList, List<Integer> secondList) {
        Map<Integer, Integer> secondListValueCount = new HashMap<>();
        for (Integer number : secondList) {
            if (secondListValueCount.containsKey(number)) {
                secondListValueCount.put(number, secondListValueCount.get(number) + 1);
            } else {
                secondListValueCount.put(number, 1);
            }
        }
        int similarityScore = 0;
        for (Integer number : firstList) {
            similarityScore += number * secondListValueCount.getOrDefault(number, 0);
        }
        return similarityScore;
    }
}
