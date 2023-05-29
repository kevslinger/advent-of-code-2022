package advent.of.code.day11;

import java.util.List;
import java.util.ArrayList;
import java.math.BigInteger;

class MonkeyTroop {
    private List<Monkey> monkeys;

    MonkeyTroop() {
        monkeys = new ArrayList<Monkey>();
    }

    void addMonkey(Monkey monkey) {
        monkeys.add(monkey);
    }

    int[] simulateMonkeys(int rounds, int worryDivider) {
        int[] inspects = new int[monkeys.size()];

        for (int round = 0 ; round < rounds; round++) {
            for (Monkey monkey: monkeys) {
                int itemSize = monkey.startItems().size();
                for (int i = 0; i < itemSize; i++) {
                    // Get the item, divide by 3, perform operation
                    BigInteger worry = monkey.operation().performOp(monkey.startItems().poll()).divide(BigInteger.valueOf(worryDivider));
                    if (worry.mod(BigInteger.valueOf(monkey.test())).equals(BigInteger.valueOf(0))) {
                        monkeys.get(monkey.trueMonkey()).startItems().add(worry);
                    } else {
                        monkeys.get(monkey.falseMonkey()).startItems().add(worry);
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

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Monkey monkey: monkeys) {
            str.append(monkey.toString());
        }
        return str.toString();
    }
}
