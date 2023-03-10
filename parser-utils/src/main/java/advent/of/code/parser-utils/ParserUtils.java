package advent.of.code.parser_utils;

import java.util.Queue;
import java.util.PriorityQueue;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;


public class ParserUtils {
    /**
     * Read text into an ArrayList of Strings, where each line is its own element in the array.
     */
    public static ArrayList<String> readIntoStringList(InputStream inputStream) {
        BufferedReader br;
        
        var lines = new ArrayList<String>();

        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = br.readLine();
            while (line.length() > 0) {
                lines.add(line);
                line = br.readLine();
                if (line == null) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(9);
        }

        return lines; 
    }

    /**
     * Read text into a String list, but skip over the first set (i.e. start after the first blank line)
     */
    public static ArrayList<String> readIntoStringListButSkipFirstSet(InputStream inputStream) {
        BufferedReader br;
        
        var lines = new ArrayList<String>();

        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = br.readLine();
            while (line.length() > 0) {
                line = br.readLine();
            }
            line = br.readLine();
            while (line.length() > 0) {
                lines.add(line);
                line = br.readLine();
                if (line == null) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(9);
        }

        return lines; 
    }

    /**
     * Read text into an ArrayList of Strings, where each line is its own element in the array.
     */
    public static ArrayList<String> readIntoStringListUntilEOF(InputStream inputStream) {
        BufferedReader br;
        
        var lines = new ArrayList<String>();

        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = br.readLine();
            while (line != null) {
                if (line.length() >= 1) {
                    lines.add(line);
                }
                line = br.readLine();
                if (line == null) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(9);
        }

        return lines; 
    }

    /**
     * Read several lines into an Integer FIFO Queue
     */
    public static Queue<Integer> readIntoIntQueue(InputStream inputStream) {
        var calorieCounts = new PriorityQueue<Integer>();

        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            int calories;
            String line = br.readLine();
            while (line != null) {
                calories = 0;
                while (line.length() > 0) {
                    calories -= Integer.parseInt(line);
                    line = br.readLine();
                    if (line == null) {
                        break;
                    }
                }
                if (line != null) {
                    line = br.readLine();
                }
                calorieCounts.add(calories);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(9);
        }
        return calorieCounts;
    }

    /**
     * Read in a single line
     */
    public static String readString(InputStream inputStream) {
        String line = null;
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            line = br.readLine();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(9);
        }
        return line;
    }
}
