package day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day09 {
    public static void main(String[] args) throws IOException {

        Path path = Path.of("2024/day09/input.txt");

        int[] input = Files.readString(path).chars().map(ch -> ch - '0').toArray();

        System.out.println("Part I: " + solvePartOne(input));
        System.out.println("Part II: " + solvePartTwo(input));
    }

    private static long solvePartOne(int[] diskMap) {

        long checksum = 0;
        int currentPosition = 0;
        int leftIndex = 0;
        int rightIndex = diskMap.length - 1;
        int spaceRequired = diskMap[rightIndex];

        while (leftIndex < rightIndex) {

            for (int i = 0; i < diskMap[leftIndex]; i++) {
                checksum += currentPosition * (leftIndex / 2);
                currentPosition++;
            }
            leftIndex++;

            for (int i = 0; i < diskMap[leftIndex]; i++) {

                if (spaceRequired == 0) {
                    rightIndex -= 2;
                    if (rightIndex <= leftIndex) {
                        break;
                    }
                    spaceRequired = diskMap[rightIndex];
                }
                checksum += currentPosition * (rightIndex / 2);
                currentPosition++;
                spaceRequired--;
            }
            leftIndex++;
        }

        for (int i = 0; i < spaceRequired; i++) {
            checksum += currentPosition * (rightIndex / 2);
            currentPosition++;
        }
        return checksum;
    }

    private static long solvePartTwo(int[] diskMap) {

        int totalBlocks = 0;

        for (int i = 0; i < diskMap.length; i++) {
            totalBlocks += diskMap[i];
        }
        int[] blockMap = new int[totalBlocks];
        int position = 0;

        for (int i = 0; i < diskMap.length; i += 2) {
            int fileLength = diskMap[i];
            int freeSpace = (i + 1 < diskMap.length) ? diskMap[i + 1] : 0;

            for (int j = 0; j < fileLength; j++) {
                blockMap[position++] = i / 2;
            }

            for (int j = 0; j < freeSpace; j++) {
                blockMap[position++] = -1;
            }
        }

        for (int fileId = diskMap.length / 2; fileId >= 0; fileId--) {

            int fileStart = -1;
            int fileEnd = -1;

            for (int i = 0; i < blockMap.length; i++) {

                if (blockMap[i] == fileId) {
                    if (fileStart == -1) {
                        fileStart = i;
                    }
                    fileEnd = i;
                }
            }

            if (fileStart == -1) {
                continue;
            }

            int fileLength = fileEnd - fileStart + 1;
            int freeSpaceStart = -1;

            for (int i = 0; i <= blockMap.length - fileLength; i++) {
                boolean hasEnoughSpace = true;
                for (int j = 0; j < fileLength; j++) {
                    if (blockMap[i + j] != -1) {
                        hasEnoughSpace = false;
                        break;
                    }
                }
                if (hasEnoughSpace && i + fileLength <= fileStart) {
                    freeSpaceStart = i;
                    break;
                }
            }
            if (freeSpaceStart != -1) {
                for (int i = fileStart; i <= fileEnd; i++) {
                    blockMap[i] = -1;
                }
                for (int i = 0; i < fileLength; i++) {
                    blockMap[freeSpaceStart + i] = fileId;
                }
            }
        }

        long checksum = 0;

        for (int i = 0; i < blockMap.length; i++) {
            if (blockMap[i] >= 0) {
                checksum += (long) i * blockMap[i];
            }
        }
        return checksum;
    }
}
