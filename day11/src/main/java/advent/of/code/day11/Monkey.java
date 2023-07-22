package advent.of.code.day11;

import java.util.LinkedList;

/*
 * int id: the ID number of the monkey
 * List<Long> startItems: the list of items the monkey is current holding
 * String operation: The operation to perform as the monkey is inspecting the item
 * int test: the number the worry amount has to be divisible by to succeed
 * int trueMonkey: the monkey to throw the item to if the test is true
 * int falseMonkey: the monkey to throw the item to if the test is false 
*/
record Monkey (
    int id, LinkedList<Long> startItems, MonkeyOp operation, int test, int trueMonkey, int falseMonkey
) {
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Monkey " + id + ": ");
        for (Long item : startItems) {
            str.append(item + ", ");
        }
        str.append(operation.toString() + ", test: divisible by " + test + ", to " + trueMonkey + " if true and " + falseMonkey + " otherwise.\n");
        return str.toString();
    }
}
