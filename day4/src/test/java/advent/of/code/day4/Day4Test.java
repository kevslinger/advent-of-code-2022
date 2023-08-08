package advent.of.code.day4;

import java.nio.file.FileSystems;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day4Test {
    ArrayList<String> sections;

    @BeforeEach
    void setup() {
        sections = readIntoStringListUntilEOF(FileSystems.getDefault().getPath(ParserUtils.TEST_RESOURCES, "day4_test.txt"));
    }

    @Test
    void testPart1() {
        int counter = 0;
        for (String elf: sections) {
            String[] elves = elf.split(",");
            counter += Day4.checkIfContained(Day4.parseElf(elves[0]), Day4.parseElf(elves[1])) ? 1 : 0;
        }
        assertEquals(2, counter);
    }

    @Test 
    void testPart2() {
        int counter = 0;
        for (String elf: sections) {
            String[] elves = elf.split(",");
            counter += Day4.checkIfOverlap(Day4.parseElf(elves[0]), Day4.parseElf(elves[1])) ? 1 : 0;
        }
        assertEquals(4, counter);
    }
}
