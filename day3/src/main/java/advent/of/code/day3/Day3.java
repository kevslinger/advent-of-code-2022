package advent.of.code.day3;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day3 {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath(ParserUtils.MAIN_RESOURCES, "day3.txt");
        ArrayList<String> rucksacks = readIntoStringListUntilEOF(path);
        int prioritySum = 0;
        for (String rucksack: rucksacks) {
            prioritySum += getPriority(getDuplicate(rucksack));
        }
        System.out.println("Priority sum in part 1: " + prioritySum);
        int prioritySum2 = 0;
        String sack1 = null, sack2 = null;
        for (int i = 0; i < rucksacks.size(); i++) {
            if (sack1 == null) {
                sack1 = rucksacks.get(i);
            } else if (sack2 == null) {
                sack2 = rucksacks.get(i);
            } else {
                Character badge = getBadge(sack1, sack2, rucksacks.get(i));
                prioritySum2 += getPriority(badge);
                sack1 = null; 
                sack2 = null;
            }
        }
        System.out.println("Priority sum in part 2: " + prioritySum2);
    }

    static Character getDuplicate(String rucksack) {
        Set<Character> part1 = new HashSet<>(), part2 = new HashSet<>();

        for (int i = 0; i < rucksack.length() / 2; i++) {
            part1.add(rucksack.charAt(i));
        }
        for (int j = rucksack.length() / 2; j < rucksack.length(); j++) {
            part2.add(rucksack.charAt(j));
        }
        part1.retainAll(part2);
        return part1.iterator().next();
    }

    static Character getBadge(String sack1, String sack2, String sack3) {
        Set<Character> set1 = new HashSet<>(), set2 = new HashSet<>(), set3 = new HashSet<>();

        for (int i = 0; i < sack1.length(); i++) {
            set1.add(sack1.charAt(i));
        }
        for (int j = 0; j < sack2.length(); j++) {
            set2.add(sack2.charAt(j));
        }
        for (int k = 0; k < sack3.length(); k++) {
            set3.add(sack3.charAt(k));
        }
        set1.retainAll(set2);
        set1.retainAll(set3);
        return set1.iterator().next();
    }

    static int getPriority(char c) {
        int priority = (int)c - 64;
        if (priority < 32) {
            priority += 26;
        } else {
            priority -= 32;
        }
        return priority;
    }
}
