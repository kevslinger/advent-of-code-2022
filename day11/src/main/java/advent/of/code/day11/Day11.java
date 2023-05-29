package advent.of.code.day11;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.math.BigInteger;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

// TODO: Is there a better way other than BigInteger? It takes a very long time to run.
class Day11 {
    public static void main(String[] args) {
        InputStream stream = Day11.class.getResourceAsStream("/day11.txt");
        ArrayList<String> monkeys = readIntoStringListUntilEOF(stream);
        MonkeyTroop monkeyTroop = createMonkeyTroop(monkeys);

        int[] inspections = monkeyTroop.simulateMonkeys(20, 3);
        Arrays.sort(inspections);
        System.out.println("Answer to part 1: " + inspections[inspections.length - 2] * inspections[inspections.length - 1]);
        inspections = monkeyTroop.simulateMonkeys(10000, 1);
        System.out.println("Answer to part 2: " + inspections[inspections.length - 2] * inspections[inspections.length - 1]);
    }

    static MonkeyTroop createMonkeyTroop(ArrayList<String> monkeys) {
        MonkeyTroop monkeyTroop = new MonkeyTroop();

        // a Monkey is 6 lines
        // ID
        // Starting items
        // Operation
        // Test
        // True monkey
        // False monkey
        int idx = 0;
        while (idx < monkeys.size()) {
            // ID
            String[] monkeyIdToks = monkeys.get(idx++).split(" ");
            int monkeyId = Integer.parseInt(monkeyIdToks[1].substring(0, monkeyIdToks[1].length() - 1));
            // Starting items
            String[] monkeyStartingItemsToks = monkeys.get(idx++).replaceAll("\\s+", "").split(":")[1].split(","); 
            var startingItems = new LinkedList<BigInteger>();
            for (String item : monkeyStartingItemsToks) {
                startingItems.add(new BigInteger(item));
            }
            // Operation
            MonkeyOp operation = new MonkeyOp(monkeys.get(idx++));
            // Test
            int test = getLastIntFromString(monkeys.get(idx++));
            // True monkey
            int trueMonkey = getLastIntFromString(monkeys.get(idx++));
            // false monkey
            int falseMonkey = getLastIntFromString(monkeys.get(idx++));
            // Create the monkey and add it to the troop
            monkeyTroop.addMonkey(new Monkey(monkeyId, startingItems, operation, test, trueMonkey, falseMonkey));
        }
        return monkeyTroop;
    }

    // Helper function to get the last token from a string (space-separated) and convert to integer
    private static int getLastIntFromString(String s) {
        String[] toks = s.split(" ");
        return Integer.parseInt(toks[toks.length - 1]);
    }    
}
