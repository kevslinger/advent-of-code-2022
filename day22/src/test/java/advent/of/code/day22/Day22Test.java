package advent.of.code.day22;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilBlankLine;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListButSkipFirstSet;

class Day22Test {
    private int[][] map;
    private ArrayList<String> instructionList;

    @BeforeEach
    void setup() {
        ArrayList<String> mapStrings = readIntoStringListUntilBlankLine(Day22Test.class.getResourceAsStream("/day22_test.txt"));
        String instructionStrings = readIntoStringListButSkipFirstSet(Day22Test.class.getResourceAsStream("/day22_test.txt")).get(0);

        try {
            map = Day22.createMap(mapStrings);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        instructionList = Day22.createInstructionList(instructionStrings);
    }

    @Test
    void testPart1() {
        Position finalPosition = Day22.runSimulation(map, instructionList);
        System.out.println(finalPosition);
        assertEquals(6032, Day22.getResult(finalPosition));
    }
}
