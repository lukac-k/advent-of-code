package day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day07 {

    public static void main(String[] args) throws IOException {
        Path path = Path.of("2024/day07/input.txt");
        List<String> lines = Files.lines(path).toList();

        long totalResult = 0;
        long totalResultWithConcat = 0;

        for (String line : lines) {
            String[] parts = line.split(":");
            long result = Long.parseLong(parts[0]);
            long[] operands = Arrays.stream(parts[1].trim().split(" ")).mapToLong(Long::parseLong).toArray();

            if (validEquation(result, operands)) {
                totalResult += result;
            }

            if (validEquationWithConcat(result, operands)) {
                totalResultWithConcat += result;
            }
        }

        System.out.println("Part I: " + totalResult);
        System.out.println("Part II: " + totalResultWithConcat);
    }

    private static boolean validEquation(long expectedResult, long[] operands) {

        int n = operands.length - 1;
        int totalCombinations = 1 << n; // 2^n

        for (int binary = 0; binary < totalCombinations; binary++) {

            long result = operands[0];

            for (int i = 0; i < n; i++) {

                if ((binary & (1 << i)) != 0) {
                    result *= operands[i + 1];
                } else {
                    result += operands[i + 1];
                }
            }

            if (result == expectedResult) {
                return true;
            }
        }
        return false;
    }

    private static boolean validEquationWithConcat(long expectedResult, long[] operands) {

        int n = operands.length - 1;
        int totalCombinations = (int) Math.pow(3, n);

        for (int combination = 0; combination < totalCombinations; combination++) {

            long result = operands[0];
            int operatorIndex = combination;

            for (int i = 0; i < n; i++) {
                int operator = operatorIndex % 3;
                operatorIndex /= 3;

                if (operator == 0) {
                    result += operands[i + 1];
                } else if (operator == 1) {
                    result *= operands[i + 1];
                } else if (operator == 2) {
                    result = Long.parseLong(String.valueOf(result) + String.valueOf(operands[i + 1]));
                }

            }

            if (result == expectedResult) {
                return true;
            }
        }
        return false;
    }
}
