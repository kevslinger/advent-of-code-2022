package advent.of.code.day15;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

public class Day15Test {
    ArrayList<String> sensorStrings;
    private TunnelSystem tunnels;

    @BeforeEach
    public void setup() {
        sensorStrings = readIntoStringListUntilEOF(Day15Test.class.getResourceAsStream("/day15_test.txt"));
        tunnels = new TunnelSystem(sensorStrings, 10);
    }

    @Test
    public void testPart1() {
        assertEquals(26, tunnels.countDeadSpots());
    }

    @Test
    public void testPart2() {
        int frequency = -1;
        int x;
        int minVal = 0;
        int maxVal = 20;
        for (int i = minVal; i <= maxVal; i++) {
            tunnels = new TunnelSystem(sensorStrings, i);
            x = tunnels.findDistressBeacon(minVal, maxVal);
            if (x != -1) {
                frequency = x * 4000000 + i;
            }
        }
        assertEquals(56000011, frequency);
    }
}
