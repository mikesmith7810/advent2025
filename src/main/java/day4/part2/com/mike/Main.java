package day4.part2.com.mike;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String INPUT_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day4/input.txt";
    public static final String INPUT_TEST_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day4/testinput.txt";

    public static final List<Point> accessPositions = List.of(
            new Point(-1, -1),
            new Point(0, -1),
            new Point(1, -1),
            new Point(-1, 0),
            new Point(1, 0),
            new Point(-1, 1),
            new Point(0, 1),
            new Point(1, 1));

    public static void main(String[] args) throws IOException {
        List<String> rowsList = readDataFromFile();

        int accessiblePoints = 0;
        boolean positionsFound = true;

        List<Point> totalPointsToBeRemoved = new ArrayList<>();

        while (positionsFound) {
            List<Point> pointsToBeRemoved = new ArrayList<>();
            String[] shelves = rowsList.toArray(new String[0]);

            for (int row = 0; row < shelves.length; row++) {
                for (int column = 0; column < shelves[row].length(); column++) {
                    char currentPosition = shelves[row].charAt(column);
                    if (String.valueOf(currentPosition).equalsIgnoreCase(".")) continue;

                    int accessCount = 0;

                    for (Point accessPosition : accessPositions) {
                        try {
                            char checkPosition = shelves[row + accessPosition.y].charAt(column + accessPosition.x);
                            if (String.valueOf(checkPosition).equalsIgnoreCase("@")) accessCount++;

                        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException exception) {
                            //no accessPoint found
                        }
                    }

                    if (accessCount < 4) {
                        accessiblePoints++;
                        pointsToBeRemoved.add(new Point(column, row));
                    }
                }
            }

            if (pointsToBeRemoved.isEmpty())
                positionsFound = false;
            else
                totalPointsToBeRemoved.addAll(pointsToBeRemoved);

            rowsList = rebuildGrid(shelves, pointsToBeRemoved);

            System.out.println("Total Accessible Points : " + accessiblePoints);
        }
        System.out.println("Total Points Removed : " + totalPointsToBeRemoved.size());
    }

    private static List<String> rebuildGrid(String[] shelves, List<Point> pointsToBeRemoved) {
        List<String> rowsList;
        rowsList = new ArrayList<>();

        for (int row = 0; row < shelves.length; row++) {
            StringBuilder shelf = new StringBuilder();
            for (int column = 0; column < shelves[row].length(); column++) {

                String pointToBeAdded = String.valueOf(shelves[row].charAt(column));
                for (Point point : pointsToBeRemoved) {
                    if ((row == (int) point.getY()) && (column == (int) point.getX()))
                        pointToBeAdded = ".";
                }
                shelf.append(pointToBeAdded);
            }
            rowsList.add(shelf.toString());
        }
        return rowsList;
    }

    private static ArrayList<String> readDataFromFile() throws IOException {
        var lines = Files.readAllLines(Path.of(INPUT_FILE_LOCATION));

        var rows = new ArrayList<>(lines);

        rows.forEach(row -> System.out.println("Row : " + row));
        return rows;
    }

}