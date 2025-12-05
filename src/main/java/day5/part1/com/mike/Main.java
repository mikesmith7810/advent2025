package day5.part1.com.mike;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {

    public static final String INPUT_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day5/input.txt";
    public static final String INPUT_TEST_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day5/testinput.txt";

    public static void main(String[] args) throws IOException {
        List<Range> ranges = readRangesFromFile();

        List<Long> ingredients = readIngredientsFromFile();

        List<Long> freshIngredients = new ArrayList<>();

        ingredients.forEach(ingredient -> {
            ranges.forEach(range -> {
               if (ingredient >= range.low && ingredient <= range.high)
                   freshIngredients.add(ingredient);
            });
        });

        List<Long> dedupedFreshIngredients = freshIngredients.stream().distinct().toList();
        System.out.println("Ranges : " + ranges.size());
        System.out.println("Ingredients : " + ingredients.size());
        System.out.println("Number of fresh ingredients : " + dedupedFreshIngredients.size());



    }

    private static List<Range> readRangesFromFile() throws IOException {
        var lines = Files.readAllLines(Path.of(INPUT_FILE_LOCATION));

        return lines.stream().
                filter(line -> line.contains("-")).
                map(line -> {
                    String[] rangeValues = line.split("-");

                    return new Range(Long.parseLong(rangeValues[0]), Long.parseLong(rangeValues[1]));
                }).
                collect(Collectors.toList());
    }

    private static List<Long> readIngredientsFromFile() throws IOException {
        var lines = Files.readAllLines(Path.of(INPUT_FILE_LOCATION));

        return lines.stream().
                filter(line -> (!line.contains("-") && !line.isBlank())).
                map(Long::parseLong).
                collect(Collectors.toList());
    }
    ;
    public record Range(long low, long high) {}

}