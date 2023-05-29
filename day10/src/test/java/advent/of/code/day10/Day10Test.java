package advent.of.code.day10;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day10Test {
    ArrayList<String> instructions;

    @BeforeEach
    void setup() {
        instructions = readIntoStringListUntilEOF(Day10Test.class.getResourceAsStream("/day10_test.txt"));
    }

    @Test
    void testPart1() {
        assertEquals(13140, Day10.getSignalStrength(instructions));
    }

    @Test 
    void testPart2() {
        StringBuilder crt = new StringBuilder();
        crt.append("##..##..##..##..##..##..##..##..##..##..\n");
        crt.append("###...###...###...###...###...###...###.\n");
        crt.append("####....####....####....####....####....\n");
        crt.append("#####.....#####.....#####.....#####.....\n");
        crt.append("######......######......######......####\n");
        crt.append("#######.......#######.......#######.....\n");
        assertEquals(crt.toString(), Day10.getCRT(instructions));
    }

}
