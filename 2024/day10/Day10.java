package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Day10 {

    private record Point(int x, int y) {

    }

    public static void main(String[] args) throws IOException {

        Path path = Path.of("2024/day10/input.txt");

        int[][] map = Files.lines(path)
                .map(line -> line.chars()
                        .map(Character::getNumericValue)
                        .toArray())
                .toArray(int[][]::new);

        solver(map);
    }

    private static void solver(int[][] map) {

        int trailheadScore = 0;
        int trailheadRating = 0;

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (map[y][x] == 0) {
                    Point startingPosition = new Point(x, y);
                    Set<Point> visitedTrailheads = new HashSet<>();
                    trailheadRating += searchNext(map, startingPosition, visitedTrailheads);
                    trailheadScore += visitedTrailheads.size();
                }
            }
        }
        System.out.println("Part I: " + trailheadScore);
        System.out.println("Part 2: " + trailheadRating);
    }

    private static int searchNext(int[][] map, Point currentPosition, Set<Point> visitedTrailheads) {

        int currentHeight = map[currentPosition.y][currentPosition.x];

        if (currentHeight == 9) {
            visitedTrailheads.add(currentPosition);
            return 1;
        }

        int trailheadRating = 0;

        for (int i = 0; i < 4; i++) {
            int nextX = currentPosition.x + (int) Math.cos(Math.PI / 2 * i);
            int nextY = currentPosition.y + (int) Math.sin(Math.PI / 2 * i);
            Point nextPosition = new Point(nextX, nextY);

            if (isInBounds(map, nextX, nextY) && map[nextY][nextX] == currentHeight + 1) {
                trailheadRating += searchNext(map, nextPosition, visitedTrailheads);
            }
        }
        return trailheadRating;
    }

    private static boolean isInBounds(int[][] map, int x, int y) {
        return y >= 0 && y < map.length && x >= 0 && x < map[0].length;
    }
}