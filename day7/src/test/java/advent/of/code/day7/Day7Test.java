package advent.of.code.day7;

import java.io.InputStream;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringList;

public class Day7Test {
    ArrayList<String> commands;
    DirectoryNode root;
    InputStream stream;

    @BeforeEach
    public void setup() {
        commands = readIntoStringList(Day7Test.class.getResourceAsStream("/day7_test.txt"));
        root = Day7.readFileSystem(commands);
    }

    @Test
    public void testPart1() {
        assertEquals(95437, Day7.getTotalSmallSizes(root));
    }

    @Test 
    public void testPart2() {
        assertEquals(24933642, Day7.getDirToDeleteSize(root, 8381165, root.getSize()));
    }
}
