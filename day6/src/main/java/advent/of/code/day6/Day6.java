package advent.of.code.day6;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readString;

class Day6 {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath(ParserUtils.MAIN_RESOURCES, "day6.txt");
        String signals = readString(path);

        System.out.println("Answer to part 1: " + getCharsUntilStart(signals, 4));
        System.out.println("Answer to part 2: " + getCharsUntilStart(signals, 14));
    }

    static int getCharsUntilStart(String signals, int bufferSize) {
        var buffer = new LinkedList<Character>();
        var counts = new HashMap<Character, Integer>(); 

        for (int i = 0; i < signals.length(); i++) {
            // Only keep track of the 4 most recent characters
            if (buffer.size() >= bufferSize) {
                Character evicted = buffer.poll();
                counts.put(evicted, counts.get(evicted) - 1);
                if (counts.get(evicted) == 0) {
                    counts.remove(evicted);
                }
            }
            // Add the character to the queue and map
            Character curChar = signals.charAt(i);
            counts.put(curChar, counts.getOrDefault(curChar, 0) + 1);
            buffer.offer(curChar);

            // If the hashmap has 4 entries, i.e. the 4 most recent chars are unique,
            // return
            if (counts.size() == bufferSize) {
                return i + 1;
            }
        }
        return -1;
    }
}
