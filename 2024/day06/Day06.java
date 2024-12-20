import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Day06 {

    private static final int[][] DIRECTIONS = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

    private record Cell(int row, int col) {
    }

    private record GuardState(Cell cell, int direction) {
    }

    public static void main(String[] args) throws IOException {
        Path filePath = Path.of("day06/input.txt");
        char[][] grid = Files.lines(filePath).map(String::toCharArray).toArray(char[][]::new);

        System.out.println("Part I: " + getVisitedCells(grid).size());
        System.out.println(
                "Part II: " + countObstructionsThatCausesLoop(grid));
    }

    private static int countObstructionsThatCausesLoop(char[][] grid) {

        Cell guard = findGuardPosition(grid);
        Set<Cell> obstructionCandidates = getVisitedCells(grid);
        int loopsDetected = 0;

        for (Cell obstructionCandidate : obstructionCandidates) {

            if (obstructionCandidate.equals(guard)) {
                continue;
            }
            grid[obstructionCandidate.row][obstructionCandidate.col] = '#';
            if (causesLoop(grid, guard)) {
                loopsDetected++;
            }
            grid[obstructionCandidate.row][obstructionCandidate.col] = '.';
        }
        return loopsDetected;
    }

    private static boolean causesLoop(char[][] grid, Cell guard) {

        int direction = getDirectionIndex(grid[guard.row][guard.col]);
        Set<GuardState> visited = new HashSet<>();

        int currentRow = guard.row;
        int currentCol = guard.col;

        while (isInBounds(grid, currentRow, currentCol)) {
            GuardState currentState = new GuardState(new Cell(currentRow, currentCol), direction);

            if (visited.contains(currentState)) {
                return true;
            }
            visited.add(currentState);
            int nextRow = currentRow + DIRECTIONS[direction][0];
            int nextCol = currentCol + DIRECTIONS[direction][1];

            if (!isInBounds(grid, nextRow, nextCol)) {
                return false;
            } else if (grid[nextRow][nextCol] == '#') {
                direction = (direction + 1) % 4; // Rotate 90 degrees clock-wise
            } else {
                currentRow = nextRow;
                currentCol = nextCol;
            }
        }
        return true;
    }

    private static Set<Cell> getVisitedCells(char[][] grid) {

        Cell guard = findGuardPosition(grid);
        int direction = getDirectionIndex(grid[guard.row][guard.col]);
        Set<Cell> visited = new HashSet<>();

        int currentRow = guard.row;
        int currentCol = guard.col;

        while (isInBounds(grid, currentRow, currentCol)) {

            visited.add(new Cell(currentRow, currentCol));
            int nextRow = currentRow + DIRECTIONS[direction][0];
            int nextCol = currentCol + DIRECTIONS[direction][1];

            if (!isInBounds(grid, nextRow, nextCol)) {
                break;
            } else if (grid[nextRow][nextCol] == '#') {
                direction = (direction + 1) % 4; // Rotate 90 degrees clock-wise
            } else {
                currentRow = nextRow;
                currentCol = nextCol;
            }
        }
        return visited;
    }

    private static Cell findGuardPosition(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (isGuard(grid[i][j])) {
                    return new Cell(i, j);
                }
            }
        }
        return null;
    }

    private static boolean isInBounds(char[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    private static int getDirectionIndex(char c) {
        return switch (c) {
            case '^' -> 0;
            case '>' -> 1;
            case 'v' -> 2;
            case '<' -> 3;
            default -> -1;
        };
    }

    private static boolean isGuard(char c) {
        return c == '^' || c == '>' || c == 'v' || c == '<';
    }
}
