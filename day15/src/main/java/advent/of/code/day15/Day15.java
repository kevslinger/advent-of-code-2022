package advent.of.code.day15;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day15 {
    public static void main(String[] args) {
        InputStream stream = Day15.class.getResourceAsStream("/day15.txt");
        ArrayList<String> sensorStrings = readIntoStringListUntilEOF(stream);
        TunnelSystem tunnels = new TunnelSystem(sensorStrings);
        tunnels.fillDeadSpotGrid(2000000);
        System.out.println("The answer to part 1 is " + tunnels.countDeadSpots());
        System.out.println("The answer to part 2 is " + tunnels.getSpotWhereBeaconCantBe(4000000));
    }
}
