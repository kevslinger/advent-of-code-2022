package advent.of.code.day7;

import java.io.InputStream;
import java.util.ArrayList;
import java.lang.Math;

import static advent.of.code.parser_utils.ParserUtils.readIntoStringListUntilEOF;

public class Day7 {
    public static void main(String[] args) {
        InputStream stream = Day7.class.getResourceAsStream("/day7.txt");
        ArrayList<String> commands = readIntoStringListUntilEOF(stream);

        DirectoryNode root = readFileSystem(commands);
        System.out.println("The answer for part 1 is : " + getTotalSmallSizes(root));

        int rootSize = root.getSize();
        int sizeLeft = 70000000 - rootSize;

        int sizeNeeded = 30000000 - sizeLeft;
        System.out.println("The answer for part 2 is : " + getDirToDeleteSize(root, sizeNeeded, rootSize));

    }

    static DirectoryNode readFileSystem(ArrayList<String> commands)  {
        DirectoryNode root = new DirectoryNode("Dummy", null);
        DirectoryNode curDir = root;
        int idx = 0;
        // Commands have $ prepended to them
        while (idx < commands.size()) {
            String command = commands.get(idx++);
            // Is a command
            if (command.startsWith("$")) {
                String[] toks = command.split(" ");
                String cmdName = toks[1];
                switch (cmdName) {
                    case "cd":
                        // If CD, there needs to be another argument
                        String dirStr = toks[2];
                        // Going out of or in to the dir
                        if (dirStr.equals("..")) {
                            curDir = curDir.getParent();
                        } else {
                            // Create the new directory and go into it.
                            curDir = new DirectoryNode(dirStr, curDir);
                            curDir.getParent().addDir(curDir);
                        }
                        break;
                    case "ls":
                        // Keep advancing the index until we get to the next command
                        while (idx < commands.size() && !commands.get(idx).startsWith("$")) {
                            // Each line either starts with a dir or a file
                            toks = commands.get(idx).split(" ");
                            if (!toks[0].equals("dir")) {
                                curDir.addFile(toks[1], Integer.parseInt(toks[0]));
                            }
                            idx++;
                            // TODO: What happens if we ls and see a directory but never CD into it?
                            // TODO: This also assumes we don't call LS on the same directory more than once
                        }
                        break;
                    // Neither CD nor LS
                    default:
                        break;
                }
            }
        }
        // Get the first child (since root is just a dummy node, the child is the real root directory).
        return root.getChildren().get(0);
    }

    static int getTotalSmallSizes(DirectoryNode root) {
        int totalSizes = 0;
        int rootSize = root.getSize();
        if (rootSize <= 100000) {
            totalSizes += rootSize;
        }
        for (DirectoryNode child: root.getChildren()) {
            totalSizes += getTotalSmallSizes(child);
        }
        return totalSizes;
    }

    static int getDirToDeleteSize(DirectoryNode root, int sizeNeeded, int minSizeToDelete) {
        int rootSize = root.getSize();
        if (rootSize >= sizeNeeded) {
            minSizeToDelete = Math.min(minSizeToDelete, rootSize);
            for (DirectoryNode child: root.getChildren()) {
                minSizeToDelete = getDirToDeleteSize(child, sizeNeeded, minSizeToDelete);
            }
        }
        return minSizeToDelete;
    }
}
