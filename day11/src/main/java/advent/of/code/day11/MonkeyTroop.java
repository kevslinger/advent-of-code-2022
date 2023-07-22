package advent.of.code.day11;

import java.util.List;
import java.util.ArrayList;

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
                int itemSize = monkey.startItems().size();
                for (int i = 0; i < itemSize; i++) {
                    // Get the item, divide by 3, perform operation
                    long worry = monkey.operation().performOp(monkey.startItems().poll()) / worryDivider;
                    if (worry % monkey.test() == 0) {
                        monkeys.get(monkey.trueMonkey()).startItems().add(worry % leastCommonTest);
                    } else {
                        monkeys.get(monkey.falseMonkey()).startItems().add(worry % leastCommonTest);
                    }
                    // Logging
                    inspects[monkey.id()]++;
                }
            }
        }
        return inspects;
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
        StringBuilder str = new StringBuilder();
        for (Monkey monkey : monkeys) {
            str.append(monkey.toString());
        }
        return str.toString();
    }
}
