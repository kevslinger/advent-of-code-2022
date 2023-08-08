package advent.of.code.day21;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.math.BigInteger;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day21 {
    static final String OURNAME = "humn"; // Us for part 2
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath(ParserUtils.MAIN_RESOURCES, "day21.txt");
        ArrayList<String> monkeyStrings = readIntoStringListUntilEOF(path);
        var monkeyYells = convertToMonkeyYells(monkeyStrings);

        var monkeyMap = createMonkeyHashMap((LinkedList<YellerMonkey>)monkeyYells.clone());
        System.out.println("The answer to part 1 is " + monkeyMap.get("root"));

        monkeyMap = createMonkeyHashMapPart2(monkeyYells);
        System.out.println("The answer to part 2 is " + monkeyMap.get(OURNAME));
    }

    static LinkedList<YellerMonkey> convertToMonkeyYells(ArrayList<String> monkeyStrings) {
        var monkeyYells = new LinkedList<YellerMonkey>();
        for (String monkey : monkeyStrings) {
            monkeyYells.add(new YellerMonkey(monkey));
        }
        return monkeyYells;
    }

    static HashMap<String, BigInteger> createMonkeyHashMap(LinkedList<YellerMonkey> monkeyYells) {
        var monkeyMap = new HashMap<String, BigInteger>();
        // Continue looping until we have exhausted all monkeys
        while (monkeyYells.size() > 0) {
            YellerMonkey monkey = monkeyYells.poll();
            // Yell is in either form of a number or <monkey1> <op> <monkey2>
            String[] yell = monkey.getYell().split(" ");
            // Just a number
            if (yell.length == 1) {
                monkeyMap.put(monkey.getName(), BigInteger.valueOf(monkey.getResult()));
            // Longer form
            } else {
                // We can only add the result of the yell if both operands are in the map already
                if (monkeyMap.containsKey(yell[0]) && monkeyMap.containsKey(yell[2])) {
                    BigInteger monkey1 = monkeyMap.get(yell[0]), monkey2 = monkeyMap.get(yell[2]);
                    BigInteger result = applyOp(monkey.getOperation(), monkey1, monkey2);
                    monkeyMap.put(monkey.getName(), result);
                // If we cannot yet complete the operation, put the monkey back at the end of the queue.
                } else {
                    monkeyYells.offer(monkey);
                }
            }
        }
        return monkeyMap;
    }

    static HashMap<String, BigInteger> createMonkeyHashMapPart2(LinkedList<YellerMonkey> monkeyYells) {
        // Keep a map of Integers for all solved yell values, and a yell map for all remaining unsolved
        var monkeyResultMap = new HashMap<String, BigInteger>();
        var monkeyYellMap = new HashMap<String, String>();
        int oldSize = 0;
        while (oldSize != monkeyYells.size()) { // If we go through an iteration and don't change anything, break.
            oldSize = monkeyYells.size();
            for (int i = 0; i < oldSize; i++) { 
                YellerMonkey monkey = monkeyYells.poll();
                if (monkey.getName().equals(Day21.OURNAME)) { // Get rid of ourselves (variable)
                    continue;
                }
                String[] yell = monkey.getYell().split(" ");
                if (yell.length == 1) {
                    monkeyResultMap.put(monkey.getName(), BigInteger.valueOf(monkey.getResult()));
                } else {
                    if (monkeyResultMap.containsKey(yell[0]) && monkeyResultMap.containsKey(yell[2])) {
                        BigInteger monkey1 = monkeyResultMap.get(yell[0]);
                        BigInteger monkey2 = monkeyResultMap.get(yell[2]);
                        BigInteger result = applyOp(monkey.getOperation(), monkey1, monkey2);
                        monkeyResultMap.put(monkey.getName(), result);
                    } else {
                        monkeyYells.offer(monkey);
                    }
                }
                // Add the yell to our YellMap
                monkeyYellMap.putIfAbsent(monkey.getName(), monkey.getYell());
            }
        }
        // At this point, monkeyMap is a mapping of Monkey Name -> Result
        // and monkeyYells is composed of the monkeys which depend on us (humn)
        // We assume that one half of root's yell is in the table of already solved values
        // So we need to find which is not solved and then solve for it.
        String[] rootYell = monkeyYellMap.get("root").split(" ");
        BigInteger result;
        String dependentMonkey;
        // Figure out which monkey has already been solved, and which depends on us
        if (monkeyResultMap.containsKey(rootYell[0])) {
            result = monkeyResultMap.get(rootYell[0]);
            dependentMonkey = rootYell[2];
        } else {
            result = monkeyResultMap.get(rootYell[2]);
            dependentMonkey = rootYell[0];
        }
        monkeyResultMap.put(dependentMonkey, result);
        // Starting from dependentMonkey, go down the chain until we get to ourselves
        while (!dependentMonkey.equals(Day21.OURNAME)) {
            String[] monkeyYell = monkeyYellMap.get(dependentMonkey).split(" "); // <monkey1> <op> <monkey2>
            String solvedMonkey;
            String newDependentMonkey;
            String op = monkeyYell[1];
            BigInteger solvedValue;
            boolean invert = false;
            if (monkeyResultMap.containsKey(monkeyYell[0])) {
                solvedMonkey = monkeyYell[0];
                newDependentMonkey = monkeyYell[2];   
                // Subtraction and Division are not commutative
                // We need to account for this when solving backwards.
                // e.g. 5 = depMonk - 5 means depMonk = 10
                // but  5 = 5 - depMonk means depMonk =  0             
                if (op.equals("-")) {
                    op = "+";
                    invert = true; // Need to multiply the result by -1 to correct for inversion
                } else if (op.equals("/")) {
                    op = "*";
                }
            } else {
                solvedMonkey = monkeyYell[2];
                newDependentMonkey = monkeyYell[0];
            }
            result = applyReverseOp(op, result, monkeyResultMap.get(solvedMonkey));
            // Multiply by -1
            if (invert) {
                result = BigInteger.ZERO.subtract(result);
            }
            // Commit the new result to the table
            monkeyResultMap.put(newDependentMonkey, result);
            dependentMonkey = newDependentMonkey;
        }
        return monkeyResultMap;
    }

    static BigInteger applyOp(String op, BigInteger a, BigInteger b) throws IllegalArgumentException {
        switch (op) {
            case "+" -> { return a.add(b); }
            case "-" -> { return a.subtract(b); }
            case "*" -> { return a.multiply(b); }
            case "/" -> { return a.divide(b); }
            default -> { throw new IllegalArgumentException("Operation not recognized."); }
        }
    }

    static BigInteger applyReverseOp(String op, BigInteger a, BigInteger b) throws IllegalArgumentException {
        switch (op) {
            case "+" -> { return a.subtract(b); }
            case "-" -> { return a.add(b); }
            case "*" -> { return a.divide(b); }
            case "/" -> { return a.multiply(b); }
            default -> { throw new IllegalArgumentException("Operation not recognized"); }
        }
    }
}
