package advent.of.code.day10;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringList;

public class Day10Test {
    ArrayList<String> instructions;

    @BeforeEach
    public void setup() {
        instructions = readIntoStringList(Day10Test.class.getResourceAsStream("/day10_test.txt"));
    }

    @Test
    public void testPart1() {
        assertEquals(13140, Day10.getSignalStrength(instructions));
    }

    @Test 
    public void testPart2() {
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
