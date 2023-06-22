package advent.of.code.day21;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.math.BigInteger;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day21 {
    public static void main(String[] args) {
        InputStream inputStream = Day21.class.getResourceAsStream("/day21.txt");
        ArrayList<String> monkeyStrings = readIntoStringListUntilEOF(inputStream);
        var monkeyYells = convertToMonkeyYells(monkeyStrings);
        var monkeyMap = createMonkeyHashMap(monkeyYells);
        System.out.println("The answer to part 1 is " + monkeyMap.get("root"));
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
        while (monkeyYells.size() > 0) {
            YellerMonkey monkey = monkeyYells.poll();
            String[] yell = monkey.getYell().split(" ");
            if (yell.length == 1) {
                monkeyMap.put(monkey.getName(), BigInteger.valueOf(monkey.getResult()));
            } else {
                if (monkeyMap.containsKey(yell[0]) && monkeyMap.containsKey(yell[2])) {
                    BigInteger monkey1 = monkeyMap.get(yell[0]);
                    BigInteger monkey2 = monkeyMap.get(yell[2]);
                    BigInteger result;
                    switch(monkey.getOperation()) {
                        case "+":
                            result = monkey1.add(monkey2);
                            break;
                        case "-":
                            result = monkey1.subtract(monkey2);
                            break;
                        case "*":
                            result = monkey1.multiply(monkey2);
                            break;
                        case "/":
                            result = monkey1.divide(monkey2);
                            break;
                        default:
                            result = BigInteger.ZERO;
                            break;
                    }
                    System.out.println(yell[0] + "(" + monkeyMap.get(yell[0]) + ") " + yell[1] + " " + yell[2] + " (" + monkeyMap.get(yell[2]) + ") = " + result);
                    monkeyMap.put(monkey.getName(), result);
                } else {
                    monkeyYells.offer(monkey);
                }
            }
        }
        return monkeyMap;
    }
}