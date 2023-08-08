package advent.of.code.day10;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day10 {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath(ParserUtils.MAIN_RESOURCES, "day10.txt");
        ArrayList<String> instructions = readIntoStringListUntilEOF(path);
    
        System.out.println("Answer to part 1: " + getSignalStrength(instructions));
        System.out.println("Answer to part 2: ");
        System.out.println(getCRT(instructions));
    }

    static int getSignalStrength(ArrayList<String> instructions) {
        int xVal = 1, curCycle = 0, totalStrength = 0;

        for (String instruction: instructions) {
            String[] toks = instruction.split(" ");
            curCycle += 1;
            // Check signal at 20, 60, 80, ...
            totalStrength += updateTotalStrength(curCycle, xVal);
            // Execute add instruction, which requires an additional cycle
            if (toks[0].equals("addx")) {
                curCycle += 1;
                totalStrength += updateTotalStrength(curCycle, xVal);
                xVal += Integer.parseInt(toks[1]);
            }
        }
        return totalStrength;
    }

    static String getCRT(ArrayList<String> instructions) {
        StringBuilder crt = new StringBuilder();
        int xVal = 1, curCycle = 0, totalStrength = 0;

        for (String instruction: instructions) {
            String[] toks = instruction.split(" ");
            curCycle += 1;

            crt.append(crtDraw(curCycle, xVal));
            totalStrength += updateTotalStrength(curCycle, xVal);

            if (toks[0].equals("addx")) {
                curCycle += 1;
                
                // CRT draws DURING cycle, xVal is updated AFTER cycle
                crt.append(crtDraw(curCycle, xVal));
                totalStrength += updateTotalStrength(curCycle, xVal);
                xVal += Integer.parseInt(toks[1]);
            }
        }
        return crt.toString();
    }

    private static int updateTotalStrength(int curCycle, int xVal) {
        if ((curCycle - 20) % 40 == 0) {
            return curCycle * xVal;
        }
        return 0;
    }

    private static String crtDraw(int curCycle, int xVal) {
        StringBuilder crtAddition = new StringBuilder();
        if (xVal <= curCycle % 40 && xVal + 2 >= curCycle % 40 || curCycle % 40 == 0 && xVal >= 38 && xVal <= 40){
            crtAddition.append("#");
        } else {
            crtAddition.append(".");
        }

        if (curCycle % 40 == 0) {
            crtAddition.append("\n");
        }
        return crtAddition.toString();
    }
}
