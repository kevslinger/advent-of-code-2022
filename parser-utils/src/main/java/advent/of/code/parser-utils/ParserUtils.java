package advent.of.code.parser_utils;

import java.util.Queue;
import java.util.PriorityQueue;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;


public class ParserUtils {
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
}
