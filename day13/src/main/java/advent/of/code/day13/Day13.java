package advent.of.code.day13;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day13 {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath(ParserUtils.MAIN_RESOURCES, "day13.txt");
        ArrayList<String> packetStrings = readIntoStringListUntilEOF(path);
        ArrayList<Packet> packets = packetStrings.stream().map(packetString -> new Packet(packetString)).collect(Collectors.toCollection(ArrayList::new));
        System.out.println("The answer for Part 1 is " + countInOrder(packets));

        packets.add(new Packet("[[2]]"));
        packets.add(new Packet("[[6]]"));
        packets = sortPackets(packets);
        System.out.println("The answer for Part 2 is " + getDecoderKey(packets, "[[2]]", "[[6]]"));
    }
    
    static int countInOrder(List<Packet> packets) {
        int count = 0;
        for (int i = 1; i < packets.size(); i += 2) {
            count += packets.get(i - 1).isInOrder(packets.get(i)) ? (i + 1) / 2 : 0;
        }
        return count;
    }

    static ArrayList<Packet> sortPackets(ArrayList<Packet> packets) {
        // Just use bubble sort.
        for (int i = 0; i < packets.size(); i++) {
            for (int j = i + 1; j < packets.size(); j++) {
                if (!packets.get(i).isInOrder(packets.get(j))) {
                    var tmp = packets.get(j);
                    packets.set(j, packets.get(i));
                    packets.set(i, tmp);
                }
            }
        }
        return packets;
    } 

    // Assume the decoder packets are unique
    static int getDecoderKey(ArrayList<Packet> packets, String packet1, String packet2) {
        int decoderKey = 1;
        for (int i = 0; i < packets.size(); i++) {
            if (packets.get(i).toString().equals(packet1) || packets.get(i).toString().equals(packet2)) {
                decoderKey *= (i+1);
            }
        }
        return decoderKey;
    }
}
