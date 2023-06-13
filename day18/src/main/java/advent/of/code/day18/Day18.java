package advent.of.code.day18;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

import static advent.of.code.parser_utils.ParserUtils.readIntoIntArrayList;

class Day18 {
    private static int[][] directions = { {-1, 0, 0}, {1, 0, 0}, {0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1} };
    public static void main(String[] arc) {
        // TODO: Null check if resource is not found?
        InputStream stream = Day18.class.getResourceAsStream("/day18.txt");
        ArrayList<int[]> voxelArrs = readIntoIntArrayList(stream);
        int[][][] voxels = createVoxelGrid(voxelArrs);

        System.out.println("The answer to part 1 is: " + countOpenSides(voxels));
        // PART 2
        int[][][] trapped = calculateTrappedAir(voxels);
        System.out.println("The answer to part 2 is: " + countExteriorSides(trapped));
    }

    // TODO: Better data structure?
    static int[][][] createVoxelGrid(ArrayList<int[]> voxelArrs) {
        // Figure out max dimensions of the voxel grid.
        int[] curArr = voxelArrs.get(0);
        int maxX = curArr[0], maxY = curArr[1], maxZ = curArr[2];
        for (int[] arr: voxelArrs) {
            maxX = Math.max(arr[0], maxX);
            maxY = Math.max(arr[1], maxY);
            maxZ = Math.max(arr[2], maxZ);
        }
        // Fill in the voxel grid
        int[][][] voxels = new int[maxX + 1][maxY + 1][maxZ + 1];
        for (int[] arr2: voxelArrs) {
            voxels[arr2[0]][arr2[1]][arr2[2]] = 1;
        }
        return voxels;
    }

    static int countOpenSides(int[][][] voxels) {
        // Final result is count * 6 - overlap.
        int count = 0, overlap = 0;
        for (int x = 0; x < voxels.length; x++) {
            for (int y = 0; y < voxels[0].length; y++) {
                for (int z = 0; z < voxels[0][0].length; z++) {
                    // If there is a voxel there
                    if (voxels[x][y][z] == 1) {
                        count++;
                        // For each adjacent square, if it is in grid and also is a square, then it is an overlap
                        overlap += isOverlap(voxels, x - 1, y, z) + isOverlap(voxels, x + 1, y, z) + isOverlap(voxels, x, y - 1, z) + isOverlap(voxels, x, y + 1, z) + isOverlap(voxels, x, y, z - 1) + isOverlap(voxels, x, y, z + 1);
                    }
                }
            }
        }
        return count * 6 - overlap;
    }

    private static int isOverlap(int[][][] voxels, int x, int y, int z) {
        return (x >= 0 && x < voxels.length && y >= 0 && y < voxels[0].length && z >= 0 && z < voxels[0][0].length && voxels[x][y][z] == 1) ? 1 : 0;
    }

    static int[][][] calculateTrappedAir(int[][][] voxels) {
        for (int x = 0; x < voxels.length; x++) {
            for (int y = 0; y < voxels[0].length; y++) {
                for (int z = 0; z < voxels[0][0].length; z++) {
                    if (voxels[x][y][z] == 0) {
                        voxels[x][y][z] = 2; // Trapped unless shown to be free.
                        // Create visited set
                        var visited = new HashSet<ThreeDimCoordinate>();
                        visited.add(new ThreeDimCoordinate(x, y, z));
                        // Go through each of the 6 directions to see if the cell is trapped.
                        if (isFree(voxels, x, y, z, visited)) {
                            voxels[x][y][z] = -1; // Free
                        }
                    }
                }
            }
        }
        return voxels;
    }

    private static boolean isFree(int[][][] voxels, int x, int y, int z, HashSet<ThreeDimCoordinate> visited) {
        // Any air on the boundary is not trapped. voxels[x][y][z] is guaranteed to be 0.
        if (x <= 0 || x >= voxels.length - 1 || y <= 0 || y >= voxels[0].length - 1 || z <= 0 || z >= voxels[0][0].length - 1) {
            return true;
        }
        // Add cell to visited set
        var oldCoords = new ThreeDimCoordinate(x, y, z);
        visited.add(oldCoords);
        // These cells must be within the boundary
        // If we hit lava, then we are trapped
        boolean result = false;
        // If the new cell is trapped or lava, then that direction is trapped.
        // If the new cell is free, then we are also free.
        // If we haven't processed that cell yet, then recursively call it.
        for (int[] direction: directions) {
            // Make sure we don't go the opposite direction (would cause loops)
            int newX = x + direction[0], newY = y + direction[1], newZ = z + direction[2];
            var newCoord = new ThreeDimCoordinate(newX, newY, newZ);
            if (visited.contains(newCoord)) {
                continue;
            }
            switch (voxels[newX][newY][newZ]) {
                case 1: // lava
                case 2: // trapped air cell
                    result = false;
                    break;
                case -1: // free air cell
                    result = true;
                    break;
                default: // call recursively
                    if (isFree(voxels, newX, newY, newZ, visited)) {
                        result = true;
                    }
                    break;
            }
            // If we are not trapped, then we can end early.
            if (result) {
                break;
            } 
        }
        return result;
    }

    static int countExteriorSides(int[][][] voxels) {
        int count = 0;
        for (int x = 0; x < voxels.length; x++) {
            for (int y = 0; y < voxels[0].length; y++) {
                for (int z = 0; z < voxels[0][0].length; z++) {
                    // If there is a voxel there
                    if (voxels[x][y][z] == 1) {
                        for (int[] dir: directions) {
                            int newX = x + dir[0], newY = y + dir[1], newZ = z + dir[2];
                            if (newX < 0 || newX >= voxels.length || newY < 0 || newY >= voxels[0].length || newZ < 0 || newZ >= voxels[0][0].length || (voxels[newX][newY][newZ] == -1)) {
                                count++;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }
}
