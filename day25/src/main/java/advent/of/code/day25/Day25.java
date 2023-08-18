package advent.of.code.day25;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day25 {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath(ParserUtils.MAIN_RESOURCES, "day25.txt");
        ArrayList<String> snafuStrings = readIntoStringListUntilEOF(path);

        ArrayList<Long> snafuIntegers = convertSnafuListToDecimalList(snafuStrings);
        long snafuSum = snafuIntegers.stream().mapToLong(i -> i).sum();
        System.out.println("The answer to part 1 is " + convertDecimalToSnafu(snafuSum));
    }

    static ArrayList<Long> convertSnafuListToDecimalList(ArrayList<String> snafuStrings) {
        return snafuStrings.stream().map(Day25::convertSnafuToDecimal).collect(Collectors.toCollection(ArrayList::new));
    }

    static Long convertSnafuToDecimal(String snafuString) {
        // Create a simple lookup table to translate characters
        var snafuMap = new HashMap<Character, Integer>();
        snafuMap.put('=', -2);
        snafuMap.put('-', -1);
        snafuMap.put('0', 0);
        snafuMap.put('1', 1);
        snafuMap.put('2', 2);

        //BigInteger decimalVal = BigInteger.ZERO;
        Long decimalVal = IntStream.range(0, snafuString.length())
                 .mapToLong(i -> snafuMap.get(snafuString.charAt(i)) * (long)Math.pow(5, snafuString.length() - 1 - i))
                 .sum();
        return decimalVal;
    }

    static String convertDecimalToSnafu(long snafuSum) {
        // Return value that we'll build through this function
        StringBuilder snafuString = new StringBuilder();
        // Constants
        final long FIVE = 5L;
        final long TWO = 2L;
        // Step 1: Figure out largest power
        int largestPower = 0;
        boolean flip = false; // Flag for flipping the characters.
        boolean nextFlip = false;
        
        var flipValues = new ArrayList<Long>();
        // The post-increment allows us to go 1 larger than the biggest power, which is what we want
        while (pow(5, largestPower) < snafuSum) {
            if (flipValues.size() == 0) {
                flipValues.add(TWO);
            } else {
                flipValues.add(flipValues.get(largestPower-1) + pow(FIVE, largestPower) * TWO);
            }
            largestPower++;
        }
        // Check if the number is larger than the flip value
        if (snafuSum > flipValues.get(largestPower-1)) {
            snafuString.append("1");
            snafuSum = pow(FIVE, largestPower) - snafuSum;
            flip = true; // Need to flip the characters except the first.
        }
        // Push largestPower back to where it should be.
        largestPower--;

        // At this point we should be below the flip value,
        // Which means we can go straight down the power list
        while (snafuSum != 0) {
            // Check current power
            long divideResult = snafuSum / pow(FIVE, largestPower);
            System.out.println(divideResult);
            // Our largest digit in SNAFU is 2 so we need to clip the result to 2.
            if (divideResult > TWO) {
                divideResult = TWO;
            // Check if we need to flip next value
            } else if (largestPower > 0 && snafuSum - pow(FIVE, largestPower) * divideResult > flipValues.get(largestPower - 1)) {
                divideResult = divideResult + 1L;
                nextFlip = !flip;
            }
            // Check next power against flip value.
            if (flip) {
                switch ((int)divideResult) {
                    case 0 -> {
                        snafuString.append("0");
                    }
                    case 1 -> {
                        snafuString.append("-");
                    }
                    case 2 -> {
                        snafuString.append("=");
                    }
                }
            } else {
                snafuString.append("" + divideResult);
            }
            // We are able to use the absolute values because we are flipping the characters as needed.
            // Otherwise the math is more complicated.
            snafuSum = Math.abs(snafuSum - pow(FIVE, largestPower) * divideResult);
            largestPower--;
            flip = nextFlip;
        }
        // If we get to 0 before we go through all the powers, we need to append 0s
        while (largestPower-- >= 0) {
            snafuString.append("0");
        }
        return snafuString.toString();
    }

    static long pow(long a, long b) {
        return (long)Math.pow(a, b);
    }
}
