package day1.part2.com.mike;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static final String INPUT_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day1input.txt";
    public static final String INPUT_TEST_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day1testinput.txt";
    public static final String INPUT_TEST_MIKE_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day1testmikeday1input.txt";

    static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws IOException {

        var inputs = Files.readAllLines(Path.of(INPUT_FILE_LOCATION));

        AtomicInteger currentPosition = new AtomicInteger(50);

        inputs.forEach(input -> {
            var direction = input.substring(0, 1);
            var clicks = Integer.parseInt(input.substring(1));

            currentPosition.getAndSet(getNewPosition(direction, clicks, currentPosition.get()));
            if (currentPosition.get() == 0) count.incrementAndGet();

            System.out.println("Position : " + currentPosition);
        });

        System.out.println("Result : " + count);
    }

    private static int getNewPosition(String direction, int clicks, int currentPosition) {
        if (direction.equalsIgnoreCase("L")) {
            for (int i = 0; i < clicks; i++) {
                currentPosition--;
                if (currentPosition == 0 && i + 1 != clicks) count.getAndIncrement();
                if (currentPosition == -1) currentPosition = 99;
            }
            return currentPosition;
        }

        if (direction.equalsIgnoreCase("R")) {
            for (int i = 0; i < clicks; i++) {
                currentPosition++;

                if (currentPosition == 100) currentPosition = 0;
                if (currentPosition == 0 && i + 1 != clicks) count.getAndIncrement();
            }
            return currentPosition;
        }
        return currentPosition;
    }


}