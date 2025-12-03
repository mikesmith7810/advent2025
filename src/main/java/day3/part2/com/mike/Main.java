package day3.part2.com.mike;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String INPUT_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day3/input.txt";
    public static final String INPUT_TEST_FILE_LOCATION = "/Users/mikesmith/code/personal/advent2025/src/main/resources/day3/testinput.txt";

    public static void main(String[] args) throws IOException {

        var banks = readDataFromFile();
        List<BigInteger> totalVoltage = new ArrayList<>();

        banks.forEach(bank -> {
            StringBuilder result = new StringBuilder();
            int removalsLeft = bank.length() - 12;

            for (int i = 0; i < bank.length(); i++) {
                char currentDigit = bank.charAt(i);

                while (removalsLeft > 0 && !result.isEmpty() && currentDigit > result.charAt(result.length() - 1)) {
                    result.setLength(result.length() - 1);
                    removalsLeft--;
                }

                result.append(currentDigit);
            }

            if (removalsLeft > 0) {
                int currentLength = result.length();
                result.setLength(currentLength - removalsLeft);
            }

            System.out.println("Bank Joltage : " + new BigInteger(result.toString()));
            totalVoltage.add(new BigInteger(result.toString()));
        });
        System.out.println("Highest Joltage : " + totalVoltage.stream().reduce(BigInteger.ZERO, BigInteger::add));
    }

    private static List<String> readDataFromFile() throws IOException {
        var lines = Files.readAllLines(Path.of(INPUT_FILE_LOCATION));

        var banks = new ArrayList<>(lines);

        banks.forEach(bank -> System.out.println("Bank : " + bank));
        return banks;
    }

}