package advent.of.code.day9;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.lang.Math;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringList;

public class Day9 {
    public static void main(String[] args) {
        InputStream stream = Day9.class.getResourceAsStream("/day9.txt");
        ArrayList<String> motions = readIntoStringList(stream);

        // TODO: I could keep track of unique positions for all heads and then only need to run this simulation once.
        System.out.println("Answer to part 1: " + getUniquePositions(motions, 2).size());
        System.out.println("Answer to part 2: " + getUniquePositions(motions, 10).size());
    }

    static HashSet<Position> getUniquePositions(List<String> motions, int heads) {
        var uniquePositions = new HashSet<Position>();
        // Base
        uniquePositions.add(new Position(0, 0));

        int[] hX = new int[heads], hY = new int[heads];
        int moveX = 0, moveY = 0, diffX = 0, diffY = 0;

        for (String motion: motions) {
            String[] toks = motion.split(" ");
            String direction = toks[0];
            int steps = Integer.parseInt(toks[1]);

            switch(direction) {
                case "U":
                    moveX = 0;
                    moveY = 1;
                    break;
                case "D":
                    moveX = 0;
                    moveY = -1;
                    break;
                case "L":
                    moveX = -1;
                    moveY = 0;
                    break;
                case "R":
                    moveX = 1;
                    moveY = 0;
                    break;
                default:
                    moveX = 0;
                    moveY = 0;
                    break;
            }
            for (int step = 0; step < steps; step++) {
                hX[heads - 1] += moveX;
                hY[heads - 1] += moveY;
                
                for (int i = heads - 1; i > 0; i--) {
                    diffX = hX[i] - hX[i - 1];
                    diffY = hY[i] - hY[i - 1];
                    // If T and H are further than 1 apart in either X or Y, then T moves to where H is
                    if (Math.abs(diffX) > 1 || Math.abs(diffY) > 1) {
                        // Check which direction we have to move
                        switch(Math.abs(diffX)) {
                            // Vertical
                            case 0:
                                hY[i - 1] += getNormalizedDelta(hY[i], hY[i - 1]);
                                break;
                            // Diagonal
                            case 1:
                                hX[i - 1] += getNormalizedDelta(hX[i], hX[i - 1]);
                                hY[i - 1] += getNormalizedDelta(hY[i], hY[i - 1]);
                                break;
                            // Could be horizontal or diagonal
                            case 2:
                                if (Math.abs(diffY) >= 1) {
                                    hY[i - 1] += getNormalizedDelta(hY[i], hY[i - 1]);
                                }
                                hX[i - 1] += getNormalizedDelta(hX[i], hX[i - 1]);
                                break;
                        }
                    }
                }
                // Only care about the end of the chain
                uniquePositions.add(new Position(hX[0], hY[0]));
            }
        }
        return uniquePositions;
    }

    private static int getNormalizedDelta(int a, int b) {
        return (a - b) / Math.abs(a - b);
    }
}
