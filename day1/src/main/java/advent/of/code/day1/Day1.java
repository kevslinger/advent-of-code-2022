package advent.of.code.day1;

import java.util.Queue;

import static advent.of.code.parser_utils.ParserUtils.readIntoIntQueue;

class Day1 {
    public static void main(String[] args) {
        Queue<Integer> calories = readIntoIntQueue(Day1.class.getResourceAsStream("/day1.txt"));
        int most = calories.poll();
        System.out.println("Answer to part 1: " + -1 * most);
        System.out.println("Answer to part 2: " + -1 * (most + calories.poll() + calories.poll()));
    }
}
