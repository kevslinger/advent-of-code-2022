package advent.of.code.day4;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringList;

public class Day4Test {
    ArrayList<String> sections;

    @BeforeEach
    public void setup() {
        sections = readIntoStringList(Day4Test.class.getResourceAsStream("/day4_test.txt"));
    }

    @Test
    public void testPart1() {
        int counter = 0;
        for (String elf: sections) {
            String[] elves = elf.split(",");
            counter += Day4.checkIfContained(Day4.parseElf(elves[0]), Day4.parseElf(elves[1])) ? 1 : 0;
        }
        assertEquals(2, counter);
    }

    @Test 
    public void testPart2() {
        int counter = 0;
        for (String elf: sections) {
            String[] elves = elf.split(",");
            counter += Day4.checkIfOverlap(Day4.parseElf(elves[0]), Day4.parseElf(elves[1])) ? 1 : 0;
        }
        assertEquals(4, counter);
    }
}
