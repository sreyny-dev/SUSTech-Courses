package BinarySearchTree;
class Node{
    int key;
    Node leftChild;
    Node rightChild;
    public Node(int key){
        this.key=key;
        leftChild=rightChild=null;
    }
}
public class BST {
    Node root; //root of BST
    //Constructor
    BST(){
        root=null;
    }
    //method to insert
    Node insert(Node root, int key){
        if(root==null) {
            root = new Node(key);
            return root;
        }
        else if(key<root.key){
            root.leftChild=insert(root.leftChild, key);
        }
        else if(key>root.key){
            root.rightChild=insert(root.rightChild, key);
        }
        return root;
    }
    //method to print Inorder traversal
    void inOrder(Node root){
        if(root!=null){
            inOrder(root.leftChild);
            System.out.print(root.key+" ");
            inOrder(root.rightChild);
        }
    }
    //method to search in BST
    Boolean search(Node root, int key){
    if(root==null){
        return false;
    }else if(root.key==key){
        return true;
    }

    if(key<root.key){
        return search(root.leftChild, key);
    }
    else
        return search(root.rightChild, key);

    }

    void searchPrint(int key){
        if(search(root, key)==null) {
            System.out.println(key+ " not found");
        }
        else
            System.out.println(key+ " found");

    }

    //Find Successor of node
    Node successor(Node root){//least value below right child of the root node
        root=root.rightChild;
        while(root.leftChild!=null){
            root=root.leftChild;
        }
        return root;
    }
    //find Predecessor
    Node predecessor(Node root){//least value below right child of the root node
        root=root.leftChild;
        while(root.rightChild!=null){
            root=root.rightChild;
        }
        return root;
    }
    Node deleteNode(Node root, int key) {
        // Base case
        if (root == null)
            return root;

        if (root.leftChild == null && root.rightChild == null) {
            return null;
        }
        // Recursive calls for ancestors of
        // return node to be deleted
        if (root.key > key) {
            root.leftChild = deleteNode(root.leftChild, key);
            return root;
        } else if (root.key < key) {
            root.rightChild = deleteNode(root.rightChild, key);
            return root;
        }

        // We reach here when root is the node
        // to be deleted.

        // If one of the children is empty
        if (root.leftChild == null) {
            return root.rightChild;
        } else if (root.rightChild == null) {
            return root.leftChild;
        }

        // If both children exist
        else {
            Node succParent = root;
            // Find successor
            Node succ = root.rightChild;
            while (succ.leftChild != null) {
                succParent = succ;
                succ = succ.leftChild;
            }
            // Delete successor.  Since successor
            // is always left child of its parent
            // we can safely make successor's right
            // right child as left of its parent.
            // If there is no succ, then assign
            // succ.right to succParent.right
            if (succParent != root)
                succParent.leftChild = succ.rightChild;
            else
                succParent.rightChild = succ.rightChild;

            // Copy Successor Data to root
            root.key = succ.key;

            // Delete Successor and return root
            return root;
        }
    }

    public void removeNode(int z){
        if(search(root, z)){
           root=deleteNode(root, z);
        }
        else
            System.out.println(z+" cound not be found!");
}

    public static void main(String [] args){
        //create an object of BST
    BST tree=new BST();
       tree.root= tree.insert(tree.root,3);
        tree.root=tree.insert(tree.root,6);
        tree.root=tree.insert(tree.root,5);
        tree.root= tree.insert(tree.root,7);
        tree.root=tree.insert(tree.root,2);
        tree.root=tree.insert(tree.root,1);
        System.out.print("Search key in BST: ");
        tree.searchPrint(4);
        System.out.print("Print key inOrder: ");
        tree.inOrder(tree.root);
        System.out.println();
        System.out.print("Print key inOrder after delete 3: ");
        tree.removeNode(3);
        tree.inOrder(tree.root);
        System.out.println();
        System.out.print("Print key inOrder after delete 5: ");
        tree.removeNode(5);
        tree.inOrder(tree.root);

    }
}
