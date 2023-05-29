package advent.of.code.day5;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilBlankLine;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListButSkipFirstSet;

class Day5 {
    public static void main(String[] args) {
        InputStream stream = Day5.class.getResourceAsStream("/day5.txt");
        ArrayList<String> raw_crates = readIntoStringListUntilBlankLine(stream); // goes until newline to separate crates and instruction
        raw_crates.remove(raw_crates.size() - 1);

        stream = Day5.class.getResourceAsStream("/day5.txt");
        ArrayList<String> instructions = readIntoStringListButSkipFirstSet(stream);

        ArrayList<LinkedList<Character>> crates = followInstructions(parseRawCrates(raw_crates), instructions);

        System.out.print("Answer for part 1: ");
        for (int i = 0; i < crates.size(); i++) {
            if (crates.get(i).size() > 0) {
                System.out.print(crates.get(i).peek());
            }
        }
        System.out.println();

        crates = followInstructionsPart2(parseRawCrates(raw_crates), instructions);
        System.out.print("Answer for part 2: ");
        for (int i = 0; i < crates.size(); i++) {
            if (crates.get(i).size() > 0) {
                System.out.print(crates.get(i).peek());
            }
        }
        System.out.println();

    }

    static ArrayList<LinkedList<Character>> parseRawCrates(ArrayList<String> raw_crates) {
        var crates = new ArrayList<LinkedList<Character>>();
        for (String level: raw_crates) {
            int strIdx = 0;
            int currentCrate = 0;
            while (strIdx < level.length()) {
                int spaces = 0;
                // Skip all whitespace
                while (strIdx < level.length() && level.charAt(strIdx++) == ' ') {
                    spaces++;
                }
                if (strIdx >= level.length()) {
                    break;
                }
                // Really we want the floor, but that is built-in by integer division.
                currentCrate += 1 + spaces / 4;
                while (crates.size() <= currentCrate) {
                    crates.add(new LinkedList<Character>());
                }
                // Advance beyond the [
                crates.get(currentCrate).offerLast(level.charAt(strIdx));
                strIdx += 3;
            }
        }
        return crates;
    }

    static ArrayList<LinkedList<Character>> followInstructions(ArrayList<LinkedList<Character>> crates, ArrayList<String> instructions) {
        for (String instruction: instructions) {
            String[] ints = instruction.replaceAll("[\\D]", " ").replaceAll("[ ]+", " ").strip().split(" ");
            int quantity = Integer.parseInt(ints[0]);
            int from = Integer.parseInt(ints[1]);
            int to = Integer.parseInt(ints[2]);
            for (int i = 0; i < quantity; i++) {
                crates.get(to).offerFirst(crates.get(from).pollFirst());
            }
        }
        return crates;
    }

    static ArrayList<LinkedList<Character>> followInstructionsPart2(ArrayList<LinkedList<Character>> crates, ArrayList<String> instructions) {
        var buffer = new Stack<Character>();
        for (String instruction: instructions) {
            String[] ints = instruction.replaceAll("[\\D]", " ").replaceAll("[ ]+", " ").strip().split(" ");
            int quantity = Integer.parseInt(ints[0]);
            int from = Integer.parseInt(ints[1]);
            int to = Integer.parseInt(ints[2]);
            for (int i = 0; i < quantity; i++) {
                buffer.push(crates.get(from).pollFirst());
            }
            for (int i = 0; i < quantity; i++) {
                crates.get(to).offerFirst(buffer.pop());
            }
        }
        return crates;
    }
    
}
