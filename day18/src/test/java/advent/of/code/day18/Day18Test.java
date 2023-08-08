package advent.of.code.day18;

import java.nio.file.FileSystems;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoIntArrayList;

class Day18Test {
    private int[][][] voxels;

    @BeforeEach
    void setup() {
        ArrayList<int[]> voxelArrs = readIntoIntArrayList(FileSystems.getDefault().getPath(ParserUtils.TEST_RESOURCES, "day18_test.txt"));
        voxels = Day18.createVoxelGrid(voxelArrs);
    }

    @Test
    void testPart1() {
        assertEquals(64, Day18.countOpenSides(voxels));
    }

    @Test
    void testPart2() {
        int[][][] trapped = Day18.calculateTrappedAir(voxels);
        assertEquals(58, Day18.countExteriorSides(trapped));
    }
}
