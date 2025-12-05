package day5.part2.com.mike;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Main {

    public static final String INPUT_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day5/input.txt";
    public static final String INPUT_TEST_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day5/testinput.txt";
    public static final String INPUT_MIKE_TEST_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day5/miketestinput.txt";

    public static void main(String[] args) throws IOException {
        List<Range> ranges = readRangesFromFile();

        AtomicLong freshIngredients = new AtomicLong();

        ranges.sort(Comparator.comparingLong(Range::high).thenComparingLong(Range::low));
        ranges = ranges.stream().distinct().collect(Collectors.toCollection(ArrayList::new));

        List<Range> newRanges = new ArrayList<>();

        for (int i = 0; i < ranges.size(); i++) {
            if (i == ranges.size() - 1) {
                newRanges.add(new Range(ranges.get(i).low, ranges.get(i).high));
                break;
            }
            if (ranges.get(i).high >= ranges.get(i + 1).low) {
                long newLow = ranges.get(i).low;
                long newHigh = ranges.get(i + 1).low - 1;

                if (newLow < newHigh)
                    newRanges.add(new Range(ranges.get(i).low, ranges.get(i + 1).low - 1));
            } else {
                newRanges.add(new Range(ranges.get(i).low, ranges.get(i).high));
            }
        }

        List<Range> filteredNewRanges = new ArrayList<>();
        
        for (int i = 0; i < newRanges.size(); i++) {
            if (i == newRanges.size() - 1) {
                filteredNewRanges.add(new Range(newRanges.get(i).low, newRanges.get(i).high));
                break;
            }
            if (newRanges.get(i).low != newRanges.get(i + 1).low)
                filteredNewRanges.add(new Range(newRanges.get(i).low, newRanges.get(i).high));
        }

        filteredNewRanges.forEach(range -> {
            freshIngredients.set(freshIngredients.get() + (range.high - range.low + 1));
        });

        System.out.println("Ranges : " + newRanges.size());
        System.out.println("Number of fresh ingredients : " + freshIngredients);


    }

    private static List<Range> readRangesFromFile() throws IOException {
        var lines = Files.readAllLines(Path.of(INPUT_FILE_LOCATION));

        return lines.stream().filter(line -> line.contains("-")).map(line -> {
            String[] rangeValues = line.split("-");

            return new Range(Long.parseLong(rangeValues[0]), Long.parseLong(rangeValues[1]));
        }).collect(Collectors.toList());
    }

    public record Range(long low, long high) {
    }

}