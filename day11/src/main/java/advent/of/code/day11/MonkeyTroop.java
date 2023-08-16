package advent.of.code.day11;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

class MonkeyTroop {
    private List<Monkey> monkeys;

    MonkeyTroop() {
        monkeys = new ArrayList<Monkey>();
    }

    void addMonkey(Monkey monkey) {
        monkeys.add(monkey);
    }

    long[] simulateMonkeys(int rounds, int worryDivider) {
        long[] inspects = new long[monkeys.size()];
        int leastCommonTest = getLeastCommonTest();
        for (int round = 0 ; round < rounds; round++) {
            for (Monkey monkey : monkeys) {
                int numItems = monkey.startItems().size();
                throwItems(monkey, numItems, worryDivider, leastCommonTest);
                // Keep track of how many inspections the monkey did.
                inspects[monkey.id()] += numItems;
            }
        }
        return inspects;
    }

    void throwItems(Monkey monkey, int numItems, int worryDivider, int leastCommonTest) {
        for (int i = 0; i < numItems; i++) {
            // Get the item, divide by worryDivider, perform operation
            long worry = monkey.operation().performOp(monkey.startItems().poll()) / worryDivider;
            if (worry % monkey.test() == 0) {
                monkeys.get(monkey.trueMonkey()).startItems().add(worry % leastCommonTest);
            } else {
                monkeys.get(monkey.falseMonkey()).startItems().add(worry % leastCommonTest);
            }
        }
    }

    List<Monkey> getMonkeys() {
        return monkeys;
    }

    private int getLeastCommonTest() {
        int leastCommonTest = monkeys.get(0).test();
        for (var monkey : monkeys) {
            leastCommonTest = getLCM(leastCommonTest, monkey.test());
        }
        return leastCommonTest;
    }

    private int getLCM(int test1, int test2) {
        // Start off with the bigger number
        int iniitalLcm = test1 > test2 ? test1 : test2;
        int lcm = iniitalLcm;
        while (true) {
            if (lcm % test1 == 0 && lcm % test2 == 0) {
                return lcm;
            }
            lcm += iniitalLcm;
        }
    }

    @Override
    public String toString() {
        return monkeys.stream().map(Monkey::toString).collect(Collectors.joining("")).toString();
    }
}
