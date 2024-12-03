import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    public static void main(String[] args) {
        File file = new File("2024/day03/input.txt");
        List<String> memory = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            memory = br.lines().toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> instructions = getHealthyInstructions(memory);
        System.out.println(performInstructions(instructions));
    }

    private static int performInstructions(List<String> instructions) {
        int sum = 0;
        boolean include = true;
        for (String instruction : instructions) {
            if (instruction.equals("don't()")) {
                include = false;
            } else if (instruction.equals("do()")) {
                include = true;
            } else if (include == true) {
                String[] parts = instruction.replaceAll("[^0-9,]", "").split(",");
                sum += Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]);
            }
        }
        return sum;
    }

    private static List<String> getHealthyInstructions(List<String> memory) {
        Pattern pattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)|don't\\(\\)|do\\(\\)");
        List<String> cleanInputList = new ArrayList<>();
        for (String line : memory) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                cleanInputList.add(matcher.group());
            }
        }
        return cleanInputList;
    }
}
