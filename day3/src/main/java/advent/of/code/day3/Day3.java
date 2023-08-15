package advent.of.code.day3;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.stream.Collectors;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day3 {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath(ParserUtils.MAIN_RESOURCES, "day3.txt");
        List<String> rucksacks = readIntoStringListUntilEOF(path);
        // PART 1
        int prioritySum = rucksacks.stream().map(Day3::getDuplicate).mapToInt(Day3::getPriority).sum();
        System.out.println("Priority sum in part 1: " + prioritySum);
        // PART 2
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
        // Part 1 is the first component of the rucksack, part 2 the second
        var part1 = new HashSet<Character>(rucksack.substring(0, rucksack.length() / 2).chars().mapToObj(i -> (char)i).collect(Collectors.toList()));
        var part2 = new HashSet<Character>(rucksack.substring(rucksack.length() / 2).chars().mapToObj(i -> (char)i).collect(Collectors.toList()));
        // Find the only element that is common between both sets and return it
        part1.retainAll(part2);
        return part1.iterator().next();
    }

    static Character getBadge(String sack1, String sack2, String sack3) {
        var set1 = new HashSet<Character>(sack1.chars().mapToObj(i -> (char)i).collect(Collectors.toList()));
        var set2 = new HashSet<Character>(sack2.chars().mapToObj(i -> (char)i).collect(Collectors.toList()));
        var set3 = new HashSet<Character>(sack3.chars().mapToObj(i -> (char)i).collect(Collectors.toList()));
        set1.retainAll(set2);
        set1.retainAll(set3);
        return set1.iterator().next();
    }

    // Lowercase letters map to 1-26, uppercase 27-52
    static int getPriority(char c) {
        int priority = (int)c - 64;
        // Uppercase
        if (priority < 32) {
            return priority + 26;
        }
        return priority - 32;
    }
}
