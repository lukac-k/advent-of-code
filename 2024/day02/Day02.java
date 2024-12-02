import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day02 {
    public static void main(String[] args) {
        File file = new File("2024/day02/input.txt");
        List<List<Integer>> reports = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            reports = br.lines()
                    .map(line -> Arrays.stream(line.split(" "))
                            .map(levelStr -> Integer.parseInt(levelStr))
                            .toList())
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Number of safe reports: " + countSafeReports(reports));
        System.out.println("Number of safe reports with dampener: " + countSafeReportsWithDampener(reports));
    }

    private static boolean isSafe(List<Integer> report) {
        boolean increasing = false;
        boolean decreasing = false;

        for (int i = 1; i < report.size(); i++) {
            int diff = report.get(i) - report.get(i - 1);
            if (diff == 0 || Math.abs(diff) > 3) {
                return false;
            } else if (diff > 0) {
                if (decreasing)
                    return false;
                increasing = true;
            } else {
                if (increasing)
                    return false;
                decreasing = true;
            }
        }
        return true;
    }

    private static int countSafeReports(List<List<Integer>> reports) {
        int count = 0;

        for (List<Integer> report : reports) {
            if (isSafe(report)) {
                count++;
            }
        }
        return count;
    }

    private static boolean isSafeWithDampener(List<Integer> report) {

        if (isSafe(report)) {
            return true;
        }
        for (int i = 0; i < report.size(); i++) {
            List<Integer> modifiedReport = new LinkedList<>(report);
            modifiedReport.remove(i);
            if (isSafe(modifiedReport)) {
                return true;
            }
        }
        return false;
    }

    private static int countSafeReportsWithDampener(List<List<Integer>> reports) {
        int count = 0;

        for (List<Integer> report : reports) {
            if (isSafeWithDampener(report)) {
                count++;
            }
        }
        return count;
    }
}
