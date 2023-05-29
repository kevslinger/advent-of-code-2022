package advent.of.code.day14;

import java.io.InputStream;
import java.util.ArrayList;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day14 {
    public static void main(String[] args) {
        InputStream stream = Day14.class.getResourceAsStream("/day14.txt");
        ArrayList<String> pathStrings = readIntoStringListUntilEOF(stream);
        ArrayList<Path> paths = parsePaths(pathStrings);
        int[] dimensions = getMatrixDims(paths);
        // Add one to Y for the sand drop, and make sure we have space on the X axis for the sand drop.
        int x = Math.max(dimensions[0], 500) + 1;
        int y = dimensions[1] + 1;
        Coordinate sandDrop = new Coordinate(500, 0);
        int[][] maze = createMaze(y, x, paths, sandDrop);
        // Count the amount of sand in the matrix.
        System.out.println("The answer to part 1 is " + countSand(maze));

        // PART 2
        // dimensions[1] is the max Y of the rocks
        // + 1 for 0-indexed array
        // + 2 for the 2 new rows until the bottom
        // Math.max(dimensions[0], 500) because the sand drops at 500
        // + dimensions[1] because the sand could drop down and right for a max of Y steps
        // + 2 for the new 2 rows (diagonal can fall farther)
        // + 1 for 0-indexed array
        y = dimensions[1] + 3;
        x = Math.max(dimensions[0], 500) + dimensions[1];
        // Add the bottom row
        paths.add(new Path("0," + (y - 1) + " -> " + (x - 1) + "," + (y - 1)));
        maze = createMaze(y, x, paths, sandDrop);
        // Count all the sand.
        System.out.println("The answer to part 2 is " + countSand(maze));
    }

    static ArrayList<Path> parsePaths(ArrayList<String> pathStrings) {
        var paths = new ArrayList<Path>();
        for (String pString: pathStrings) {
            paths.add(new Path(pString));
        }
        return paths;
    }

    static int[] getMatrixDims(ArrayList<Path> paths) {
        int[] dims = new int[2];
        for (Path path: paths) {
            for (Coordinate coordinate: path.getCoordinates()) {
                dims[0] = Math.max(dims[0], coordinate.x());
                dims[1] = Math.max(dims[1], coordinate.y());
            }
        }
        return dims;
    }

    static int[][] createMaze(int y, int x, ArrayList<Path> paths, Coordinate sandDrop) {
        // Construct the Matrix.
        int[][] maze = new int[y][x];
        // Fill in the matrix with rock.
        maze = addRocks(maze, paths);
        // Fill in the matrix with sand.
        maze = addSand(maze, sandDrop);
        return maze;
    }

    static int[][] addRocks(int[][] maze, ArrayList<Path> paths) {
        for (Path path: paths) {
            ArrayList<Coordinate> coords = path.getCoordinates();
            for (int i = 0; i < coords.size() - 1; i++) {
                for (int x = coords.get(i).x(); x <= coords.get(i+1).x(); x++) {
                    maze[coords.get(i).y()][x] = 2; // Rock
                }
                for (int x = coords.get(i).x(); x >= coords.get(i+1).x(); x--) {
                    maze[coords.get(i).y()][x] = 2; // Rock
                }
                for (int y = coords.get(i).y(); y <= coords.get(i+1).y(); y++) {
                    maze[y][coords.get(i).x()] = 2; // Rock
                }
                for (int y = coords.get(i).y(); y >= coords.get(i+1).y(); y--) {
                    maze[y][coords.get(i).x()] = 2; // Rock
                }
            }
        }
        return maze;
    }

    static int[][] addSand(int[][] maze, Coordinate sandDrop) {
        int sandPieces = 0;
        int curX, curY;
        while (true) {
            // Reset sand to drop position.
            curX = sandDrop.x();
            curY = sandDrop.y();
            // When the source of sand is blocked, stop.
            if (maze[curY][curX] != 0) {
                return maze;
            }
            // While we have room to descend
            while (maze[curY][curX] == 0) {
                // Step 1: Go straight down
                if (curY + 1 < maze.length && maze[curY + 1][curX] == 0) {
                    curY++;
                }
                // Step 2: Go down and left
                else if (curY + 1 < maze.length && curX - 1 >= 0 && maze[curY + 1][curX - 1] == 0) {
                    curY++;
                    curX--;
                }
                // Step 3: Go down and right
                else if (curY + 1 < maze.length && curX + 1 < maze[0].length && maze[curY + 1][curX + 1] == 0) {
                    curY++;
                    curX++;
                }
                // Step 4: Cannot go any further
                else {
                    // If we reached the bottom, then we failed and need to stop.
                    if (curY == maze.length - 1 || curX == 0 || curX == maze[0].length - 1) {
                        return maze;
                    }
                    // Otherwise, the sand has settled and we can move on to the next piece.
                    else {
                        sandPieces++;
                        maze[curY][curX] = 1;
                    }
                }
            }
        }
    }

    static int countSand(int[][] maze) {
        int sandPieces = 0;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 1) {
                    sandPieces++;
                }
            }
        }
        return sandPieces;
    }
}
