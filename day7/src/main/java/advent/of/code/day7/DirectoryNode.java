package advent.of.code.day7;

import java.util.ArrayList;

public class DirectoryNode {
    private String name;
    private DirectoryNode parent;
    private ArrayList<DirectoryNode> childDirs;
    private ArrayList<File> files;

    public DirectoryNode(String name, DirectoryNode parent) {
        this.name = name;
        this.parent = parent;
        childDirs = new ArrayList<>();
        files = new ArrayList<File>();
    }

    public boolean addDir(String dirName) {
        childDirs.add(new DirectoryNode(dirName, this));
        return true;
    } 

    public boolean addDir(DirectoryNode dir) {
        dir.parent = this;
        childDirs.add(dir);
        return true;
    }

    public boolean addFile(String fileName, int size) {
        files.add(new File(fileName, size));
        return true;
    }

    public boolean addFile(File file) {
        files.add(file);
        return true;
    }

    public int getSize() {
        int size = 0;
        for (File f: files) {
            size += f.size();
        }
        for (DirectoryNode dir: childDirs) {
            size += dir.getSize();
        }
        return size;
    }
   
   // Getters 

    public String getName() {
        return name;
    }

    public DirectoryNode getParent() {
        return parent;
    }

    public ArrayList<DirectoryNode> getChildren() {
        return childDirs;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(name + "\n");
        for (DirectoryNode dir: childDirs) {
            str.append("-" + dir.getName() + " (dir)\n");
            str.append(dir.toString());
        }
        for (File file: files) {
            str.append("- " + file.name() + " (file, size=" + file.size() + ")\n");
        }
        str.append("\n");
        return str.toString();
    }
}
