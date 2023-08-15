package advent.of.code.day5;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilBlankLine;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListButSkipFirstSet;

class Day5Test {
    InputStream stream;
    ArrayList<String> raw_crates;
    ArrayList<String> instructions;

    @BeforeEach
    void setup() {
        stream = Day5Test.class.getResourceAsStream("/day5_test.txt");
        raw_crates = readIntoStringListUntilBlankLine(stream); // goes until newline to separate crates and instruction
        raw_crates.remove(raw_crates.size() - 1);

        stream = Day5.class.getResourceAsStream("/day5_test.txt");
        instructions = readIntoStringListButSkipFirstSet(stream);
    }

    @Test
    void testPart1() {
        ArrayList<LinkedList<Character>> crates = Day5.followInstructions(Day5.parseRawCrates(raw_crates), instructions);

        String str = crates.stream().map(Day5::peekAtChar).filter(Objects::nonNull).map(c -> c.toString()).collect(Collectors.joining(""));

        assertEquals("CMZ", str);
    }

    @Test 
    void testPart2() {
        ArrayList<LinkedList<Character>> crates = Day5.followInstructionsPart2(Day5.parseRawCrates(raw_crates), instructions);
        String str = crates.stream().map(Day5::peekAtChar).filter(Objects::nonNull).map(c -> c.toString()).collect(Collectors.joining(""));

        assertEquals("MCD", str.toString());
    }
}
