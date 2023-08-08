package advent.of.code.day15;

import java.nio.file.FileSystems;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day15Test {
    ArrayList<String> sensorStrings;
    private TunnelSystem tunnels;

    @BeforeEach
    void setup() {
        sensorStrings = readIntoStringListUntilEOF(FileSystems.getDefault().getPath(ParserUtils.TEST_RESOURCES, "day15_test.txt"));
        tunnels = new TunnelSystem(sensorStrings);
    }

    @Test
    void testPart1() {
        tunnels.fillDeadSpotGrid(10);
        assertEquals(26, tunnels.countDeadSpots());
    }

    @Test
    void testPart2() {
        assertEquals(56000011, tunnels.getSpotWhereBeaconCantBe(20));
    }
}
