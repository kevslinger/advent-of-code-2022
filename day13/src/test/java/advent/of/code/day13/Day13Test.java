package advent.of.code.day13;

import java.nio.file.FileSystems;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day13Test {
    ArrayList<String> graph;
    ArrayList<Packet> packets;

    @BeforeEach
    void setup() {
        ArrayList<String> packetStrings = readIntoStringListUntilEOF(FileSystems.getDefault().getPath(ParserUtils.TEST_RESOURCES, "day13_test.txt"));
        packets = new ArrayList<Packet>();
        for (int i = 0; i < packetStrings.size(); i++) {
            packets.add(new Packet(packetStrings.get(i)));
        }
    }

    @Test
    void testPart1() {
        assertEquals(13, Day13.countInOrder(packets));
    }

    @Test 
    void testPart2() {
        packets.add(new Packet("[[2]]"));
        packets.add(new Packet("[[6]]"));
        packets = Day13.sortPackets(packets);
        assertEquals(140, Day13.getDecoderKey(packets, "[[2]]", "[[6]]"));
    }
}
