package advent.of.code.parser_utils;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Queue;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;


public class ParserUtils {
    public final static String MAIN_RESOURCES = "src/main/resources";
    public final static String TEST_RESOURCES = "src/test/resources";
    /**
     * Read text into an ArrayList of Strings, where each line is its own element in the array.
     * Stops at the first blank line
     */
    public static ArrayList<String> readIntoStringListUntilBlankLine(InputStream inputStream) {
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
    public static ArrayList<String> readIntoStringListUntilEOF(Path path) {
        Stream<String> stream = null;
        try {
            stream = Files.lines(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(9);
        }
        // Collect all non-blank lines into a list
        return stream.filter(e -> e.length() >= 1).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Read several lines into an Integer FIFO Queue
     */
    public static Queue<Integer> readIntoIntQueue(Path path) {
        Stream<String> stream = null;
        try {
            stream = Files.lines(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(9);
        }
        return stream.map(Integer::parseInt).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Read Calories in for Part 1
     */
    public static Queue<Integer> readIntoCalorieQueue(InputStream inputStream) {
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
     * Read input string into a List of integer arrays.
     * Assumes the integer arrays are comma-separated.
     */
    public static ArrayList<int[]> readIntoIntArrayList(Path path) {
        Stream<String> stream = null;
        try {
            stream = Files.lines(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(9);
        }
        return stream.map(ParserUtils::helper).collect(Collectors.toCollection(ArrayList::new));
    }
    private static int[] helper(String line) {
        String[] toks = line.split(",");
        var arr = new int[toks.length];
        for (int i = 0; i < toks.length; i++) {
            arr[i] = Integer.parseInt(toks[i]);
        }
        return arr;
    }

    /**
     * Read in a single line using a stream
     */
    public static String readString(Path path) {
        Stream<String> stream = null;
        try {
            stream = Files.lines(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(9);
        }
        // Collect all non-blank lines into a list
        return stream.filter(e -> e.length() >= 1).findFirst().orElse(null);
    }
}
