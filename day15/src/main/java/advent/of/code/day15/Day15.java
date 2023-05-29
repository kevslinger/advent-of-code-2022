package advent.of.code.day15;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day15 implements Runnable{
    private int multiplier;
    private ArrayList<String> sensorString;

    Day15(int multiplier, ArrayList<String> sensorString) {
        this.multiplier = multiplier;
        this.sensorString = sensorString;
    }

    public static void main(String[] args) {
        InputStream stream = Day15.class.getResourceAsStream("/day15.txt");
        ArrayList<String> sensorStrings = readIntoStringListUntilEOF(stream);
        TunnelSystem tunnels = new TunnelSystem(sensorStrings, 2000000);
        System.out.println("The answer to part 1 is " + tunnels.countDeadSpots());
        // PART 2
        // TODO: Takes too long to run.
        Runnable r1 = new Day15(1, sensorStrings);
        Runnable r2 = new Day15(2, sensorStrings);
        Runnable r3 = new Day15(3, sensorStrings);
        Runnable r4 = new Day15(4, sensorStrings);
        Runnable r5 = new Day15(5, sensorStrings); 
        Runnable r6 = new Day15(6, sensorStrings);
        Runnable r7 = new Day15(7, sensorStrings);
        Runnable r8 = new Day15(8, sensorStrings);

        ExecutorService pool = Executors.newFixedThreadPool(8);

        pool.execute(r1);
        pool.execute(r2);
        pool.execute(r3);
        pool.execute(r4);
        pool.execute(r5);
        pool.execute(r6);
        pool.execute(r7);
        pool.execute(r8);

        pool.shutdown();
        
        //System.out.println("The answer to part 2 is " + frequency);
    }

    public void run() {
        int x;
        int frequency = -1;
        int minVal = 0, maxVal = 4000000;
        for (int i = 500000 * (multiplier - 1); i <= 500000 * multiplier; i++) {
            TunnelSystem tunnels = new TunnelSystem(sensorString, i);
            x = tunnels.findDistressBeacon(minVal, maxVal);
            if (x != -1) {
                frequency = x * maxVal + i;
                System.out.println("The answer to part 2 is " + frequency);
                break;
            }
            if (i % 1000 == 0) {
                System.out.println(i);
            }
        }
    }
}
