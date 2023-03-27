package advent.of.code.day4;

import java.util.ArrayList;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

public class Day4 {
    public static void main(String[] args) {
        ArrayList<String> sections = readIntoStringListUntilEOF(Day4.class.getResourceAsStream("/day4.txt"));
        int counter = 0, counter2 = 0;
        for (String elf: sections) {
            String[] elves = elf.split(",");
            int[] elf1 = parseElf(elves[0]), elf2 = parseElf(elves[1]);
            counter += checkIfContained(elf1, elf2) ? 1 : 0;
            counter2 += checkIfOverlap(elf1, elf2) ? 1: 0;
        }
        System.out.println("Answer for part 1: " + counter);
        System.out.println("Answer for part 2: " + counter2);
    }

    static int[] parseElf(String elf) {
        String[] toks = elf.split("-");
        return new int[]{Integer.parseInt(toks[0]), Integer.parseInt(toks[1])};
    }

    static boolean checkIfContained(int[] elf1, int[] elf2) {
        // Case 1: elf1[0] == elf2[0]
        if (elf1[0] == elf2[0] || elf1[1] == elf2[1]) {
            return true;
        } else if (elf1[0] < elf2[0]) {
            if (elf1[1] >= elf2[1]) {
                return true;
            } 
        } else if (elf2[1] > elf1[1]) {
                return true;
        }
        return false;
    }

    static boolean checkIfOverlap(int[] elf1, int[] elf2) {
        return (elf1[0] == elf2[0]) || (elf1[1] == elf2[1]) || (elf1[0] < elf2[0] && elf1[1] >= elf2[0]) || (elf2[0] < elf1[0] && elf2[1] >= elf1[0]);

    }
    
}
