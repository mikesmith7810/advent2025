package day2.part1.com.mike;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

    public static final String INPUT_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day2/input.txt";
    public static final String INPUT_TEST_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day2/testinput.txt";
    public static final String COMMA = ",";
    public static final String REGEX = "^(.+)\\1$";

    public static void main(String[] args) throws IOException {

        AtomicLong runningTotal = new AtomicLong(0);
        var ranges = readDataFromFile();

        ranges.forEach(range -> {
            var start = Long.parseLong(range.substring(0, range.indexOf("-")));
            var end = Long.parseLong(range.substring(range.indexOf("-") + 1, range.length()));

            for (long i = start; i < end + 1; i++) {
                boolean containsSequence = (String.valueOf(i)).matches(REGEX);

                if (containsSequence) {
                    runningTotal.getAndAdd(i);
                }
            }
        });

        System.out.println("Result : " + runningTotal);
    }

    private static List<String> readDataFromFile() throws IOException {
        var lines = Files.readAllLines(Path.of(INPUT_FILE_LOCATION));

        var ranges = lines.stream().findFirst().map(line -> Arrays.asList(line.split(COMMA))).orElse(List.of());

        ranges.forEach(range -> System.out.println("Range : " + range));
        return ranges;
    }

}