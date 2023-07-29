package advent.of.code.day23;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.LinkedList;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

class Day23 {
    public static void main(String[] arc) {
        InputStream InputStream = Day23.class.getResourceAsStream("/day23.txt");
        ArrayList<String> gridLines = readIntoStringListUntilEOF(InputStream);
        Elf[][] elfGrid = convertToElfGrid(gridLines);

        System.out.println("The answer to part 1 is " + computePart1(runSimulation(elfGrid)));
        // TODO: Create a new copy of the elf grid
        elfGrid = convertToElfGrid(gridLines);
        System.out.println("The answer to part 2 is " + computePart2(elfGrid));

    }

    static Elf[][] convertToElfGrid(ArrayList<String> gridLines) {
        // TODO: The grid size is hardcoded
        var elfGrid = new Elf[gridLines.size() + 150][gridLines.get(0).length() + 150]; // Add 75 to each direction

        for (int i = 0; i < gridLines.size(); i++) {
            String line = gridLines.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') { // '#' represents an elf
                    elfGrid[i + 75][j + 75] = new Elf(new Coordinate(i + 75, j + 75));
                }
            }
        }
        return elfGrid;
    }

    static Elf[][] runSimulation(Elf[][] elfGrid) {
        ArrayList<ArrayList<Coordinate>> directions = getDirections();
        for (int round = 0; round < 10; round++) {
            elfGrid = runProposalPhase(elfGrid, directions);
            elfGrid = runMovementPhase(elfGrid, directions);
            // Update the directions list order
            directions.add(directions.remove(0));
            
        }
        return elfGrid;
    }

    private static Elf[][] runProposalPhase(Elf[][] elfGrid, ArrayList<ArrayList<Coordinate>> directions) {
        // Iterate over the grid to find proposals for each elf
        for (int i = 0; i < elfGrid.length; i++) {
            for (int j = 0; j < elfGrid[0].length; j++) {
                // No elf
                if (elfGrid[i][j] == null) {
                    continue;
                }
                // Found an elf
                Elf curElf = elfGrid[i][j];
                // Go through each prposal direction
                int firstDirectionFound = -1;
                int sumDirectionsFound = 0;
                for (int k = 0; k < directions.size(); k++) {
                    var direction = directions.get(k);
                    boolean empty = true;
                    for (Coordinate neighbor : direction) {
                        // If there is an elf in one of the spots, we cannot propose to move there
                        if (elfGrid[i + neighbor.x()][j + neighbor.y()] != null) {
                            empty = false;
                            break;
                        }
                    }
                    // Found a direction to propose
                    if (empty) {
                        // The elf will propose the first direction found
                        if (firstDirectionFound == -1) {
                            firstDirectionFound = k;
                        }
                        sumDirectionsFound += k + 1; 
                    }
                }
                // Only set a proposal direction if there is a nearby elf
                // i.e. if all directions are possible, don't set any direction
                if (sumDirectionsFound < 10) {
                    curElf.setProposalDir(firstDirectionFound);
                }
            }
        }
        return elfGrid;
    }

    private static Elf[][] runMovementPhase(Elf[][] elfGrid, ArrayList<ArrayList<Coordinate>> directions) {
        // Iterate over the grid to move the elves
        for (int i = 0; i < elfGrid.length; i++) {
            for (int j = 0; j < elfGrid[0].length; j++) {
                // No elf
                if (elfGrid[i][j] == null) {
                    continue;
                }
                // Only care about elves with real proposal directions
                Elf curElf = elfGrid[i][j];
                int proposedDir = curElf.getProposalDir();
                if (proposedDir == -1) {
                    continue;
                }
                Coordinate dir = directions.get(proposedDir).get(0);
                curElf.setProposalDir(-1); // Reset proposed movement to neutral
                // Move the elf
                Coordinate newLoc = new Coordinate(i + dir.x(), j + dir.y());
                if (elfGrid[newLoc.x()][newLoc.y()] == null) {
                    curElf.setProposalDir(-1);
                    elfGrid[newLoc.x()][newLoc.y()] = curElf;
                    elfGrid[i][j] = null;
                } else {
                    // Elf is contested and cannot move. We need to return the original elf to its old location
                    Elf tmpElf = elfGrid[newLoc.x()][newLoc.y()];
                    Coordinate tmpLoc = tmpElf.getOldLoc();
                    elfGrid[tmpLoc.x()][tmpLoc.y()] = tmpElf;
                    elfGrid[newLoc.x()][newLoc.y()] = null;
                }
            }
        }
        // Iterate over the grid to update the elves' metadata
        for (int i = 0; i < elfGrid.length; i++) {
            for (int j = 0; j < elfGrid[0].length; j++) {
                if (elfGrid[i][j] == null) {
                    continue;
                }
                Elf curElf = elfGrid[i][j];
                curElf.setOldLoc(new Coordinate(i, j));
            }
        }
        return elfGrid;
    }

    static int computePart1(Elf[][] elfGrid) {
        int north = elfGrid.length - 1, south = 0, west = elfGrid[0].length - 1, east = 0;
        // Compute boundaries for the smallest rectangle
        int numElves = 0;
        for (int i = 0; i < elfGrid.length; i++) {
            for (int j = 0; j < elfGrid[0].length; j++) {
                if (elfGrid[i][j] == null) {
                    continue;
                }
                numElves++;
                north = Math.min(north, i);
                south = Math.max(south, i);
                west = Math.min(west, j);
                east = Math.max(east, j);
            }
        }
        return (east - west + 1) * (south - north + 1) - numElves;
    }

    private static ArrayList<ArrayList<Coordinate>> getDirections() {
        int n = -1;
        int s = 1;
        int w = -1;
        int e = 1;
        var directionList = new ArrayList<ArrayList<Coordinate>>();

        var north = new ArrayList<Coordinate>();
        north.add(new Coordinate(n, 0));
        north.add(new Coordinate(n, w));
        north.add(new Coordinate(n, e));
        directionList.add(north);

        var south = new ArrayList<Coordinate>();
        south.add(new Coordinate(s, 0));
        south.add(new Coordinate(s, w));
        south.add(new Coordinate(s, e));
        directionList.add(south);

        var west = new ArrayList<Coordinate>();
        west.add(new Coordinate(0, w));
        west.add(new Coordinate(n, w));
        west.add(new Coordinate(s, w));
        directionList.add(west);

        var east = new ArrayList<Coordinate>();
        east.add(new Coordinate(0, e));
        east.add(new Coordinate(n, e));
        east.add(new Coordinate(s, e));
        directionList.add(east);

        return directionList;
    }

    static int computePart2(Elf[][] elfGrid) {
        ArrayList<ArrayList<Coordinate>> directions = getDirections();
        // TODO: There is a world where 2 elves would *want* to move but 
        // do not because their proposed locations collide.
        // However I am going to let this run until no elf proposes to move
        int rounds = 0;
        ArrayList<Elf> elves = getElves(elfGrid);
        boolean proposedMovement;
        do {
            proposedMovement = false;
            elfGrid = runProposalPhase(elfGrid, directions);
            for (Elf elf: elves) {
                if (elf.getProposalDir() != -1) {
                    proposedMovement = true;
                    break; // As long as one elf proposes movement, we continue the simulation.
                }
            }
            elfGrid = runMovementPhase(elfGrid, directions);
            // Update the directions list order
            directions.add(directions.remove(0));
            rounds++;
        } while (proposedMovement);
        return rounds;
    }

    private static ArrayList<Elf> getElves(Elf[][] elfGrid) {
        var elves = new ArrayList<Elf>();
        for (int i = 0; i < elfGrid.length; i++) {
            for (int j = 0; j < elfGrid[0].length; j++) {
                if (elfGrid[i][j] != null) {
                    elves.add(elfGrid[i][j]);
                }
            }
        }
        return elves;
    }
}
