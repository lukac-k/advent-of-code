package day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Day08 {

    private record Cell(int row, int col) {
    }

    public static void main(String[] args) throws IOException {
        Path path = Path.of("2024/day08/input.txt");
        char[][] grid = Files.lines(path).map(String::toCharArray).toArray(char[][]::new);

        HashMap<Character, ArrayList<Cell>> antennas = getAntennas(grid);

        System.out.println("Part I: " + getAntinodesPartI(grid, antennas).size());
        System.out.println("Part II: " + getAntinodesPartII(grid, antennas).size());
    }

    private static HashMap<Character, ArrayList<Cell>> getAntennas(char[][] grid) {
        HashMap<Character, ArrayList<Cell>> antennas = new HashMap<>();

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] != '.') { // '.' indicating empty cell
                    ArrayList<Cell> antennaPositions = antennas.getOrDefault(grid[row][col], new ArrayList<>());
                    antennaPositions.add(new Cell(row, col));
                    antennas.putIfAbsent(grid[row][col], antennaPositions);
                }
            }
        }
        return antennas;
    }

    private static Set<Cell> getAntinodesPartI(char[][] grid, HashMap<Character, ArrayList<Cell>> antennas) {
        Set<Cell> antinodes = new HashSet<>();

        for (ArrayList<Cell> positions : antennas.values()) {
            int size = positions.size();

            for (int i = 0; i < size - 1; i++) {
                for (int j = i + 1; j < size; j++) {

                    Cell firstCandidate = getAntinode(positions.get(i), positions.get(j));
                    Cell secondCandidate = getAntinode(positions.get(j), positions.get(i));

                    if (isInBounds(firstCandidate, grid)) {
                        antinodes.add(firstCandidate);
                    }
                    if (isInBounds(secondCandidate, grid)) {
                        antinodes.add(secondCandidate);
                    }
                }
            }
        }
        return antinodes;
    }

    private static Set<Cell> getAntinodesPartII(char[][] grid, HashMap<Character, ArrayList<Cell>> antennas) {
        Set<Cell> antinodes = new HashSet<>();

        for (ArrayList<Cell> positions : antennas.values()) {
            int size = positions.size();

            for (int i = 0; i < size - 1; i++) {
                for (int j = i + 1; j < size; j++) {

                    Cell first = positions.get(i);
                    Cell second = positions.get(j);

                    antinodes.add(first);
                    antinodes.add(second);

                    antinodes.addAll(getExtendedAntinodes(first, second, grid));
                }
            }

        }
        return antinodes;
    }

    private static Set<Cell> getExtendedAntinodes(Cell first, Cell second, char[][] grid) {
        Set<Cell> antinodes = new HashSet<>();

        int dRow = second.row - first.row;
        int dCol = second.col - first.col;

        int row = first.row - dRow;
        int col = first.col - dCol;

        while (isInBounds(new Cell(row, col), grid)) {
            antinodes.add(new Cell(row, col));
            row -= dRow;
            col -= dCol;
        }

        row = second.row + dRow;
        col = second.col + dCol;

        while (isInBounds(new Cell(row, col), grid)) {
            antinodes.add(new Cell(row, col));
            row += dRow;
            col += dCol;
        }
        return antinodes;
    }

    private static Cell getAntinode(Cell first, Cell second) {
        int dRow = second.row - first.row;
        int dCol = second.col - first.col;

        return new Cell(second.row + dRow, second.col + dCol);
    }

    private static boolean isInBounds(Cell cell, char[][] grid) {
        return cell.row >= 0 && cell.row < grid.length && cell.col >= 0 && cell.col < grid[0].length;
    }
}
