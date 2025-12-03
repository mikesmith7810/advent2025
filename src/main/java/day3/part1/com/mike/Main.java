package day3.part1.com.mike;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class Main {

    public static final String INPUT_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day3/input.txt";
    public static final String INPUT_TEST_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day3/testinput.txt";
    public static final String COMMA = ",";
    public static final String REGEX = "^(.+)\\1$";

    public static void main(String[] args) throws IOException {
        List<Integer> joltages = new ArrayList<>();

        AtomicLong runningTotal = new AtomicLong(0);
        var banks = readDataFromFile();

        banks.forEach(bank -> {
            AtomicInteger highestVoltage = new AtomicInteger();
            AtomicInteger highestVoltagePosition = new AtomicInteger();

            bank.chars().
                    limit(bank.length() - 1).
                    map(Character::getNumericValue).
                    forEach(voltage -> {
                        if (voltage > highestVoltage.get()) {
                            highestVoltage.set(voltage);
                            highestVoltagePosition.set(bank.indexOf(String.valueOf(voltage)));
                        }
                    });

            var highestSubVoltage = 0;
            for (int i = highestVoltagePosition.get()+1; i<bank.length(); i++){
                var subVoltage  = Integer.parseInt(bank.substring(i,i+1));
                if (subVoltage > highestSubVoltage){
                    highestSubVoltage = subVoltage;
                }
            }

            System.out.println("Highest Voltage : " + highestVoltage);
            System.out.println("Highest Voltage Position : " + highestVoltagePosition);
            System.out.println("Highest Sub Voltage Position : " + highestSubVoltage);
            System.out.println("Highest Joltage : " + highestVoltage.get() + highestSubVoltage);
            joltages.add(Integer.parseInt(String.valueOf(highestVoltage.get()) + String.valueOf(highestSubVoltage)));

        });


        System.out.println("Result : " + joltages.stream().mapToInt(Integer::intValue).sum());
    }

    private static List<String> readDataFromFile() throws IOException {
        var lines = Files.readAllLines(Path.of(INPUT_FILE_LOCATION));

        var banks = new ArrayList<>(lines);

        banks.forEach(bank -> System.out.println("Bank : " + bank));
        return banks;
    }

}