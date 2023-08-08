package advent.of.code.day12;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day12 {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath(ParserUtils.MAIN_RESOURCES, "day12.txt");
        ArrayList<String> graph = readIntoStringListUntilEOF(path);
        Contour contour = readGraph(graph);

        FunctionalInterface part1Function = (int row, int col) -> { return row == contour.getStartRow() && col == contour.getStartCol(); };
        System.out.println("Answer to part 1: " + contour.getShortedDistanceBackwards(part1Function));
        FunctionalInterface part2Function = (int row, int col) -> { return contour.getMap()[row][col] == 1; };
        System.out.println("Answer to part 2: " + contour.getShortedDistanceBackwards(part2Function));
    }

    static Contour readGraph(ArrayList<String> graph) {
        int[][] map = new int[graph.size()][graph.get(0).length()];
        int startRow = 0, startCol = 0, endRow = 0, endCol = 0;
        for (int row = 0; row < graph.size(); row++) {
            String line = graph.get(row);
            for (int col = 0; col < line.length(); col++) {
                char curChar = line.charAt(col);
                
                switch (curChar) {
                    // Start
                    case 'S':
                        startRow = row;
                        startCol = col;
                        map[row][col] = 1;
                        break;
                    // End
                    case 'E':
                        endRow = row;
                        endCol = col;
                        map[row][col] = 26;
                        break;
                    default:
                        map[row][col] = curChar - 96;
                }
            }
        }
        return new Contour(map, startRow, startCol, endRow, endCol);
    }  
}
