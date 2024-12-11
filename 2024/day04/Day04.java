import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day04 {

    private static final int[][] DIRECTIONS = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 },
            { -1, -1 }, { -1, 1 }, { 1, -1 } };

    public static void main(String[] args) {
        File file = new File("2024/day04/input.txt");
        List<List<Character>> input = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                input.add(line.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Part I: " + countOccurances(input, "XMAS"));
        System.out.println("Part II: " + countXmasPattern(input));
    }

    // PART II
    private static int countXmasPattern(List<List<Character>> input) {
        int count = 0;

        for (int row = 0; row < input.size(); row++) {
            for (int col = 0; col < input.get(row).size(); col++) {
                if (input.get(row).get(col) == 'A') {
                    count += isXmas(input, row, col) ? 1 : 0;
                }
            }
        }
        return count;
    }

    private static boolean isXmas(List<List<Character>> input, int row, int col) {
        return isMas(input, row - 1, col - 1, row, col, row + 1, col + 1)
                && isMas(input, row - 1, col + 1, row, col, row + 1, col - 1);
    }

    private static boolean isMas(List<List<Character>> input, int r1, int c1, int r2, int c2, int r3, int c3) {
        if (!isValid(input, r1, c1) || !isValid(input, r3, c3)) {
            return false;
        }
        String forward = "" + input.get(r1).get(c1) + input.get(r2).get(c2) + input.get(r3).get(c3);
        String backward = "" + input.get(r3).get(c3) + input.get(r2).get(c2) + input.get(r1).get(c1);
        return "MAS".equals(forward) || "MAS".equals(backward);
    }

    // PART I
    private static boolean isValid(List<List<Character>> input, int row, int col) {
        return row >= 0 && row < input.size() && col >= 0 && col < input.get(row).size();
    }

    private static int countOccurances(List<List<Character>> input, String word) {
        int count = 0;

        for (int row = 0; row < input.size(); row++) {
            for (int col = 0; col < input.get(row).size(); col++) {
                if (input.get(row).get(col) == 'X') {
                    count += countWord(input, word, row, col);
                }
            }
        }

        return count;
    }

    private static int countWord(List<List<Character>> input, String word, int row, int col) {
        int count = 0;

        for (int[] direction : DIRECTIONS) {
            int newRow = row;
            int newCol = col;
            int charIndex;

            for (charIndex = 0; charIndex < word.length(); charIndex++) {
                if (!isValid(input, newRow, newCol)) {
                    break;
                }
                if (input.get(newRow).get(newCol) != word.charAt(charIndex)) {
                    break;
                }
                newRow += direction[0];
                newCol += direction[1];

            }
            if (charIndex == word.length()) {
                count++;
            }
        }
        return count;
    }
}
