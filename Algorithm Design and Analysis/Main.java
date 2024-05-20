import java.util.*;

class Node{
    String name;
    String content;
    Map<String, Node> children;
    Node parent;
    public Node(String name){
        this.name=name;
        this.content=null;
        this.parent=null;
        this.children=new HashMap<>();
    }

    public String getAbsolutePath() {
        if (parent == null) {
            // If the current node is the root, return "/"
            return "/";
        } else {
            // Recursively construct the absolute path by appending the name of the current node
            // to the absolute path of its parent
            return parent.getAbsolutePath() + "/" + name;
        }
    }
}
public class Main {
    Node root;
    public Main(){
        root=new Node("/");
    }
    private void mkdir(String path) {
        String[] directories=path.split("/");
        Node current=root;
        StringBuilder outputPath = new StringBuilder("./");
        for(String directory: directories){
            if(directory.isEmpty()){
                continue;
            }
            outputPath.append(directory).append("/");
            if(!current.children.containsKey(directory)){
                Node newDirectory=new Node(directory);
                newDirectory.parent=current;
                current.children.put(directory, newDirectory);
            }
            current=current.children.get(directory);
        }
//        System.out.println(outputPath.substring(0, outputPath.length() - 1));
    }
    private void echo(String content, String path) {
        String[] directories=path.split("/");
        Node current=root;
        StringBuilder outputPath = new StringBuilder("./");
        for(String directory: directories){
            if(directory.isEmpty()){
                continue;
            }
            outputPath.append(directory).append("/");
            if(!current.children.containsKey(directory)){
                Node newDirectory=new Node(directory);
                newDirectory.parent=current;
                current.children.put(directory, newDirectory);
            }
            current=current.children.get(directory);
        }
            if (content != null) {
                current.content = content;
//                System.out.println(outputPath.substring(0,outputPath.length()-1));
                //"Content updated successfully for file: "
//                System.out.println(content);
            } else {
                current.content = " ";
//                System.out.println(outputPath.substring(0,outputPath.length()-1));
                //"File created successfully with empty content: "
            }
    }
    private void rm(String path, boolean recursive) {
        String[] directories=path.split("/");
        Node current=root;
        StringBuilder outputPath = new StringBuilder("./");
            for(String directory: directories) {
                if (directory.isEmpty()) {
                    continue;
                }
                outputPath.append(directory).append("/");
                if (!current.children.containsKey(directory)) {
                    System.out.println("Directory not found" + outputPath.substring(0,outputPath.length()-1));
                    return;
                }
                current = current.children.get(directory);
            }
                if(current.content!=null){
                    current.parent.children.remove(current.name);
//                    System.out.println("File removed successfully: "+outputPath.substring(0,outputPath.length()-1));
                }else{
                    if(recursive){
                        removeDirectoryRecursive(current);
                    }else{
                        System.out.println("Cannot remove file without -rf");
                    }
                }
    }
    public void removeDirectoryRecursive(Node directory){
        for(Node child:directory.children.values()){
            if(child.content!=null){
                directory.children.remove(child.name);
            }else{
                removeDirectoryRecursive(child);
            }
        }
        directory.parent.children.remove(directory.name);
//        System.out.println("Directory removed successfully: "+directory.name);
    }

    private void mv(String srcPath, String dstPath) {

        String[] srcDirectories = srcPath.split("/");
        String[] dstDirectories = dstPath.split("/");

        Node srcParent = null;
        Node srcNode = root;
        Node dstParent = null;
        Node dstNode = root;

        for (String directory : srcDirectories) {
            if (directory.isEmpty()) {
                continue;
            }

            srcParent = srcNode;
            srcNode = srcNode.children.get(directory);
            if (srcNode == null) {
                System.out.println("Source path not found: " + srcPath);
                return;
            }
        }


        for (int i = 0; i < dstDirectories.length - 1; i++) {
            String directory = dstDirectories[i];
            if (directory.isEmpty()) {
                continue;
            }

            dstParent = dstNode;
            dstNode = dstNode.children.get(directory);
            if (dstNode == null) {
                System.out.println("Destination path not found: " + dstPath);
                return;
            }
        }


        String fileName = srcDirectories[srcDirectories.length - 1];
//        if (dstNode.children.containsKey(fileName)) {
//            System.out.println("A file or directory with the same name already exists in the destination: " + fileName);
//            return;
//        }
        srcParent.children.remove(srcNode.name);
        srcNode.parent = dstNode;
        dstNode.children.put(fileName, srcNode);

//        System.out.println("Moved successfully from " + srcPath + " to " + dstPath);
    }

    private void cat(String filePath) {
        String[] directories=filePath.split("/");
        Node current=root;
        for (String directory:directories){
            if(directory.isEmpty()){
                continue;
            }
            current=current.children.get(directory);
            if(current==null){
                System.out.println("File out found: "+filePath);
                return;
            }
        }
        if(current.content!=null){
            System.out.println(current.content);
        }else{
            System.out.println("Not a file: "+filePath);
        }
    }
    public void find(String path, String[] expression) {
        List<String> resultPaths = new ArrayList<>();
        findHelper(root, path, expression, ".", resultPaths); // Pass an empty string as the relative path

        // Display the total number of paths found
        System.out.println(resultPaths.size());

        // Display all the paths found
        for (String resultPath : resultPaths) {
            System.out.println(resultPath);
        }
    }

    private void findHelper(Node node, String currentPath, String[] expression, String relativePath, List<String> resultPaths) {
        // Check if the current node matches the expression
        boolean match = true;
        if (expression.length >= 2) {
            // Check the expression as before
        }

        // If the node matches the expression, add its relative path to the result list
        if (match) {
            resultPaths.add(relativePath); // Add the relative path instead of the current path
        }

        // Recursively traverse child directories
        for (Node child : node.children.values()) {
            String newRelativePath = relativePath.isEmpty() ? child.name : relativePath + "/" + child.name; // Update the relative path
            String newPath = currentPath.equals("/") ? currentPath + child.name : currentPath + "/" + child.name;
            findHelper(child, newPath, expression, newRelativePath, resultPaths); // Pass the updated relative path
        }
    }
public int findNodesByName(Node node, String name, String currentPath, List<String> resultPaths) {
    // Initialize a counter to count the number of matching nodes
    int count = 0;

    // Check if the current node matches the name
    if (node.name.equals(name)) {
        // Increment the counter
        count++;
        // Add the current path to the result paths list
        resultPaths.add(currentPath);
    }

    // Recursively search child nodes
    for (Node child : node.children.values()) {
        String newPath = currentPath + "/" + child.name;
        // Increment the counter by the number of matching nodes in the subtree
        count += findNodesByName(child, name, newPath, resultPaths);
    }

    // Return the total count of matching nodes
    return count;
}



    public void findNodesByType(Node node, String type, String currentPath, List<String> resultPaths) {
        // Check if the current node matches the type
        boolean match = false;
        if (type.equals("f") && node.content != null) {
            match = true;
        } else if (type.equals("d") && !node.children.isEmpty()) {
            match = true;
        }

        if (match) {
            System.out.println("Found node with type '" + type + "' at path: " + currentPath);
            resultPaths.add(currentPath);
        }

        // Recursively search child nodes
        for (Node child : node.children.values()) {
            String newPath = currentPath + "/" + child.name;
            findNodesByType(child, type, newPath, resultPaths);
        }
    }

    public int findNodesByNameAndType(Node node, String name, String type, String currentPath, List<String> resultPaths) {
        // Check if the current node matches the name and type
            int count = 0;
        boolean match = false;
        if (node.name.equals(name)) {
            if (type.equals("f") && node.content != null) {
                match = true;
            } else if (type.equals("d") && !node.children.isEmpty()) {
                match = true;
            }
        }

        if (match) {
            count++;
            resultPaths.add(currentPath);
        }

        // Recursively search child nodes
        for (Node child : node.children.values()) {
            String newPath = currentPath + "/" + child.name;
            // Ensure that the search continues only for directories if the type is "d"
            if (type.equals("d")) {
                count+=findNodesByNameAndType(child, name, type, newPath, resultPaths);
            } else {
                // For files, continue the search regardless of the node type
                count+=findNodesByNameAndType(child, name, type, newPath, resultPaths);
            }
        }
    return count;
    }

    private static String resolveAbsolutePath(Node root, String path) {
        // Start from the root node
        Node current = root;
        // Split the path into segments
        String[] segments = path.split("/");
        // Traverse each segment of the path
        for (String segment : segments) {
            // Handle directory traversal (e.g., "..")
            if (segment.equals("..")) {
                // Move up to the parent directory
                if (current.parent != null) {
                    current = current.parent;
                } else {
                    // If the parent is null, the path is invalid
                    return null;
                }
            } else if (!segment.equals(".")) {
                // Move to the child directory
                current = current.children.get(segment);
                // If the current segment is not found or is null, the path is invalid
                if (current == null) {
                    return null;
                }
            }
        }
        // Return the absolute path
        return current.getAbsolutePath();
    }
    // Get the absolute path of the current node





    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt(); //create or update command: mkdir, echo, rm, mv
        int m=sc.nextInt(); //query command: cat, find
        sc.nextLine();
        List<String> commands = new ArrayList<>();
        for(int i=0;i<n+m;i++){
            commands.add(sc.nextLine());
        }
        Main fs=new Main();
        for(String command:commands){
            processCommand(fs,command);
        }
        sc.close();
    }
    public static void processCommand(Main fs, String command){
        String[] parts=command.split(" ");
        switch(parts[0]){
            case "mkdir":
                fs.mkdir(parts[1]);
                break;
            case "echo":
                if (parts.length == 3) {
                    fs.echo(null, parts[2]);
                } else if (parts.length == 4) {
                    fs.echo(parts[1], parts[3]);
                }
                break;
            case "rm":
                if (parts.length == 2) {
                    fs.rm(parts[1], false);
                } else if (parts.length == 3 && parts[1].equals("-rf")) {
                    fs.rm(parts[2], true);
                }
                break;
            case "mv":
                fs.mv(parts[1],parts[2]);
                break;
            case "cat":
                fs.cat(parts[1]);
                break;
            case "find":
                // Check if there are additional arguments for find
                if (parts.length == 1) {
                    // Case: find
                    fs.find("/", new String[0]); // Start from root directory with no expression
                } else if (parts.length == 2) {
                    // Perform find by name only
                    List<String> resultPaths = new ArrayList<>();
                    int count = fs.findNodesByName(fs.root, "b", "/", resultPaths);
                    System.out.println("Number of nodes with name 'b': " + count);
                    List<String> resultPathsByName = new ArrayList<>();
                    fs.findNodesByName(fs.root, parts[1],".", resultPathsByName);
                    for (String resultPath : resultPathsByName) {
                        System.out.println(resultPath.substring(resultPath.indexOf(parts[1])));
                    }
                } else if (parts.length == 3 && parts[1].equals("-type")) {
                    List<String> resultPathsByType = new ArrayList<>();
                    fs.findNodesByType(fs.root, parts[2], ".", resultPathsByType);
                } else if (parts.length == 4 && parts[2].equals("-name")) {
                    // Case: find [path] -name [name]
                    String path = parts[1];
                    String name = parts[3];
                    List<String> resultPathsByNameAndType = new ArrayList<>();
                    int count =  fs.findNodesByNameAndType(fs.root, name, "d", path, resultPathsByNameAndType);
                    System.out.println(count);
                    for (String resultPath : resultPathsByNameAndType) {
                        System.out.println(resultPath.substring(resultPath.indexOf(path)));
                    }
                } else if (parts.length == 4 && parts[2].equals("-type")) {
                    List<String> resultPathsByNameAndType = new ArrayList<>();
                    fs.findNodesByNameAndType(fs.root, parts[1], parts[3], ".", resultPathsByNameAndType);
                } else  if (parts.length == 6 && parts[2].equals("-name") && parts[4].equals("-type")) {
                    String name = parts[3];
                    String type = parts[5];
                    // Initialize a list to store the result paths
                    List<String> resultPathsByNameAndType = new ArrayList<>();
                    // Call the findNodesByNameAndType method starting from the root directory
                    int count = fs.findNodesByNameAndType(fs.root, name, type, "/", resultPathsByNameAndType);
                    // Print the count of matching nodes
                    System.out.println(count);
                    // Print the result paths if needed
                    for (String resultPath : resultPathsByNameAndType) {
                        // Replace the leading "//" with "././././"
                        String formattedPath = resultPath.replaceFirst("//", "././././");
                        System.out.println(formattedPath);
                    }
                }
                break;
            default:
                System.out.println("Invalid command: " + command);
                break;
        }
    }
}
