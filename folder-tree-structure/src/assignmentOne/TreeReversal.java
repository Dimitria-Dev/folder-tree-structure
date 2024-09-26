package assignmentOne;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class TreeReversal {
    public static void main(String[] args) throws Exception{
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Node nodes = listDirectory(currentPath);
        printDirectory(nodes, 0);
    }

    public static Node listDirectory(Path path) throws Exception {
        Node folderNode = new Node(path.getFileName().toString());
        BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);

        if (attributes.isDirectory()) {
            DirectoryStream<Path> paths = Files.newDirectoryStream(path);

            for (Path tempPath : paths) {
                Node childNode = listDirectory(tempPath);
                folderNode.childFolders.add(childNode);
                folderNode.numberOfFiles += childNode.numberOfFiles;
                folderNode.totalFolderSize += childNode.totalFolderSize;
            }

        } else {
            folderNode.numberOfFiles = 1;
            folderNode.totalFolderSize = attributes.size();
        }
        return folderNode;
    }

    public static String spacesForDepth(int depth) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            builder.append("    ");
        }
        return builder.toString();
    }

    //This method is used to print the directory tree structure recursively

    public static void printDirectory(Node node, int depth) throws Exception {
        System.out.println(spacesForDepth(depth) + " > " + node.folderName + " (" + node.numberOfFiles + " files, " + node.totalFolderSize + " bytes)");

        for(Node childrenNode : node.childFolders){
            printDirectory(childrenNode, depth + 1);
        }
    }

    //Class Node to represent each folder in the tree
    static class Node{
        String folderName;
        int numberOfFiles;
        long totalFolderSize;
        List<Node> childFolders;

        public Node(String folderName) {
            this.folderName = folderName;
            this.numberOfFiles = 0;
            this.totalFolderSize = 0;
            this.childFolders = new ArrayList<>();
        }
    }
}
