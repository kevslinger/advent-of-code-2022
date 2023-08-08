package advent.of.code.day8;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

import advent.of.code.parser_utils.ParserUtils;
import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day8 {
    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath(ParserUtils.MAIN_RESOURCES, "day8.txt");
        ArrayList<String> trees = readIntoStringListUntilEOF(path);

        int[][] scenic = initialiseScenic(trees);
        
        System.out.println("The answer for part 1 is: " + countVisibleTrees(scenic));
        System.out.println("The answer for part 2 is: " + countTotalScenicScore(scenic));
    }

    /**
     * Get the scenic score for all trees
     */
    static int[][] initialiseScenic(List<String> trees) {
        int rows = trees.size();
        int cols = trees.get(0).length();
        int[][] scenic = new int[rows][cols];

        // Initialise the edges.
        // We use -1 to denote the tree is "visible"
        // Top and Bottom row
        for (int i = 0; i < cols; i++) {
            scenic[0][i] = -1;
            scenic[rows - 1][i] = -1;
        }
        // First column
        for (int i = 0; i < rows; i++) {
            scenic[i][0] = -1;
            scenic[i][cols - 1] = -1;
        }

        // Go through all the rows and columns other than the outside
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                int north = getOneDirectionScenicScore(trees, i, j, -1,  0, scenic);
                int south = getOneDirectionScenicScore(trees, i, j,  1,  0, scenic);
                int west = getOneDirectionScenicScore(trees, i, j,   0, -1, scenic);
                int east = getOneDirectionScenicScore(trees, i, j, 0,  1, scenic);
                scenic[i][j] = Math.abs(north * south * west * east);
                // Can see to the end (is a visible tree)
                if (north < 0 || south < 0 || west < 0 || east < 0) {
                    scenic[i][j] = -1 * scenic[i][j];
                }
            }
        }

        return scenic;
    }

    static int countVisibleTrees(int[][] scenic) {
        int rows = scenic.length;
        int cols = scenic[0].length;

        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (scenic[i][j] < 0) {
                    count++;
                }
            }
        }
        return count;
    }  

    static int countTotalScenicScore(int[][] scenic) {
        int rows = scenic.length;
        int cols = scenic[0].length;

        int maxScenic = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Scenic score doesn't care about whether or not the tree is visible.
                maxScenic = Math.max(maxScenic, Math.max(scenic[i][j], -1 * scenic[i][j]));
            }
        }
        return maxScenic;
    }
    
    private static int getOneDirectionScenicScore(List<String> trees, int row, int col, int deltaRow, int deltaCol, int[][] visible) {
        int rows = visible.length;
        int cols = visible[0].length;
        int newRow = row + deltaRow;
        int newCol = col + deltaCol;
        if (!isInBounds(rows, cols, newRow, newCol)) {
            return 0;
        }
        
        int treeHeight = Character.getNumericValue(trees.get(row).charAt(col));
        int scenicScore = 0;
        while (isInBounds(rows, cols, newRow, newCol)) {
            scenicScore++;
            if (treeHeight <= Character.getNumericValue(trees.get(newRow).charAt(newCol))) {
                return scenicScore;
            }
            newRow = newRow + deltaRow;
            newCol = newCol + deltaCol;

        }
        // If we got all the way to the end, use -1 to signal the tree is visible.
        return -1 * scenicScore;
    }

    private static boolean isInBounds(int maxRows, int maxCols, int row, int col) {
        return row >= 0 && row < maxRows && col >= 0 && col < maxCols;
    }
}
