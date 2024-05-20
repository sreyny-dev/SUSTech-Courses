//package BinarySearchTree;
//
//// Java program to implement optimized delete in BST.
//import java.util.*;
//
//class Node {
//    int key;
//    Node left, right;
//
//    // A utility function to create a new BST node
//    Node(int item) {
//        key = item;
//        left = right = null;
//    }
//}
//
//class BinSearchTree {
//    Node root;
//
//    // A utility function to do inorder traversal of BST
//    void inorder(Node root) {
//        if (root != null) {
//            inorder(root.left);
//            System.out.print(root.key + " ");
//            inorder(root.right);
//        }
//    }
//
//    /* A utility function to insert a new node with given key in
//     * BST */
//    Node insert(Node node, int key) {
//        /* If the tree is empty, return a new node */
//        if (node == null)
//            return new Node(key);
//
//        /* Otherwise, recur down the tree */
//        if (key < node.key)
//            node.left = insert(node.left, key);
//        else if (key > node.key)
//            node.right = insert(node.right, key);
//
//        /* return the (unchanged) node pointer */
//        return node;
//    }
//
//    /* Given a binary search tree and a key, this function
//    deletes the key and returns the new root */
//    Node deleteNode(Node root, int key) {
//        // Base case
//        if (root == null)
//            return root;
//
//        // Recursive calls for ancestors of
//        // node to be deleted
//        if (root.key > key) {
//            root.left = deleteNode(root.left, key);
//            return root;
//        } else if (root.key < key) {
//            root.right = deleteNode(root.right, key);
//            return root;
//        }
//        if (root.left == null && root.right == null) {
//            return null;
//        }
//
//        // We reach here when root is the node
//        // to be deleted.
//
//        // If one of the children is empty
//        if (root.left == null) {
//            return root.right;
//        } else if (root.right == null) {
//            return root.left;
//        }else {
//            //node has both left and right child
//            Node succParent = root;
//            // Find successor
//            Node succ = root.right;
//            while (succ.left != null) {
//                succParent = succ;
//                succ = succ.left;
//            }
//            // Delete successor. Since successo is always left child of its parent ,we can safely make successor's right
//            // right child as left of its parent.
//            // If there is no succ, then assign
//            // succ.right to succParent.right
//            if (succParent != root)
//                succParent.left = succ.right;
//            else
//                succParent.right = succ.right;
//            // Copy Successor Data to root
//            root.key = succ.key;
//            // Delete Successor and return root
//            return root;
//
//        }
//    }
//
//    // Driver Code
//    public static void main(String[] args) {
//        BinSearchTree tree = new BinSearchTree();
//
//		/* Let us create following BST
//				50
//			/	 \
//			30	 70
//			/ \ / \
//		20 40 60 80 */
//        tree.root = tree.insert(tree.root, 9);
//        tree.insert(tree.root, 4);
//        tree.insert(tree.root, 10);
//        tree.insert(tree.root, 11);
//
//        System.out.print("Original BST: ");
//        tree.inorder(tree.root);
//
//        System.out.println("\n\nDelete a Leaf Node: 9");
//        tree.root = tree.deleteNode(tree.root, 9);
//        System.out.print("Modified BST tree after deleting Leaf Node:\n");
//        tree.inorder(tree.root);
//
////        System.out.println("\n\nDelete Node with single child: 4");
////        tree.root = tree.deleteNode(tree.root, 4);
////        System.out.print("Modified BST tree after deleting single child Node:\n");
////        tree.inorder(tree.root);
//
//    }
//}
