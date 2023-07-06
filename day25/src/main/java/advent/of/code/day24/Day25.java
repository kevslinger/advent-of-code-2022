package advent.of.code.day25;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.math.BigInteger;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day25 {
    public static void main(String[] args) {
        InputStream inputStream = Day25.class.getResourceAsStream("/day25.txt");
        ArrayList<String> snafuStrings = readIntoStringListUntilEOF(inputStream);

        ArrayList<BigInteger> snafuIntegers = convertSnafuListToDecimalList(snafuStrings);

        BigInteger snafuSum = BigInteger.ZERO;
        for (BigInteger snafuInteger : snafuIntegers) {
            snafuSum = snafuSum.add(snafuInteger);
        }
        
        System.out.println("The answer to part 1 is " + convertDecimalToSnafu(snafuSum));
    }

    static ArrayList<BigInteger> convertSnafuListToDecimalList(ArrayList<String> snafuStrings) {
        var snafuIntegers = new ArrayList<BigInteger>();
        for (String snafuString : snafuStrings) {
            snafuIntegers.add(convertSnafuToDecimal(snafuString));
        }
        return snafuIntegers;
    }

    static BigInteger convertSnafuToDecimal(String snafuString) {
        // Create a simple lookup table to translate characters
        var snafuMap = new HashMap<Character, Integer>();
        snafuMap.put('=', -2);
        snafuMap.put('-', -1);
        snafuMap.put('0', 0);
        snafuMap.put('1', 1);
        snafuMap.put('2', 2);

        BigInteger decimalVal = BigInteger.ZERO;
        for (int i = 0; i < snafuString.length(); i++) {
            decimalVal = decimalVal.add(BigInteger.valueOf(snafuMap.get(snafuString.charAt(i)) * (long)Math.pow(5, snafuString.length() - 1 - i)));
        }
        return decimalVal;
    }

    static String convertDecimalToSnafu(BigInteger snafuSum) {
        // Return value that we'll build through this function
        StringBuilder snafuString = new StringBuilder();
        // Constants
        final BigInteger FIVE = BigInteger.valueOf(5);
        final BigInteger TWO = BigInteger.valueOf(2);
        // Step 1: Figure out largest power
        int largestPower = 0;
        boolean flip = false; // Flag for flipping the characters.
        boolean nextFlip = false;
        
        var flipValues = new ArrayList<BigInteger>();
        // The post-increment allows us to go 1 larger than the biggest power, which is what we want
        while (FIVE.pow(largestPower).compareTo(snafuSum) < 0) {
            if (flipValues.size() == 0) {
                flipValues.add(TWO);
            } else {
                flipValues.add(flipValues.get(largestPower-1).add(FIVE.pow(largestPower).multiply(TWO)));
            }
            System.out.println("Power is " + largestPower + " and flip value is " + flipValues.get(largestPower));
            largestPower++;
        }
        // Check if the number is larger than the flip value
        if (snafuSum.compareTo(flipValues.get(largestPower-1)) > 0) {
            snafuString.append("1");
            snafuSum = FIVE.pow(largestPower).subtract(snafuSum);
            flip = true; // Need to flip the characters except the first.
        }
        // Push largestPower back to where it should be.
        largestPower--;

        // At this point we should be below the flip value,
        // Which means we can go straight down the power list
        while (!snafuSum.equals(BigInteger.ZERO)) {
            // Check current power
            BigInteger divideResult = snafuSum.divide(FIVE.pow(largestPower));
            // Our largest digit in SNAFU is 2 so we need to clip the result to 2.
            if (divideResult.compareTo(TWO) > 0) {
                divideResult = TWO;
            // Check if we need to flip next value
            } else if (largestPower > 0 && snafuSum.subtract(FIVE.pow(largestPower).multiply(divideResult)).compareTo(flipValues.get(largestPower - 1)) > 0){
                divideResult = divideResult.add(BigInteger.ONE);
                nextFlip = !flip;
            }
            // Check next power against flip value.
            if (flip) {
                switch (divideResult.intValue()) {
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
                snafuString.append(divideResult.toString());
            }
            // We are able to use the absolute values because we are flipping the characters as needed.
            // Otherwise the math is more complicated.
            snafuSum = snafuSum.subtract(FIVE.pow(largestPower).multiply(divideResult)).abs();
            largestPower--;
            flip = nextFlip;
        }
        // If we get to 0 before we go through all the powers, we need to append 0s
        while (largestPower-- >= 0) {
            snafuString.append("0");
        }
        return snafuString.toString();
    }
}
