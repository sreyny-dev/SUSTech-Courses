import java.util.*;
class Node
{
    int key, height;
    Node left, right;
    Node (int value)
    {
        key = value;
        height = 1;
    }
}
class AVLTree{
    Node root;

    int height (Node ptr)
    {
        if (ptr == null)
            return 0;
        return ptr.height;
    }

    int max (int a, int b)
    {
        return (a > b) ? a : b;
    }

    Node rotateRight (Node b)
    {
        Node a = b.left;
        Node c = a.right;
        a.right = b;
        b.left = c;
        b.height = max (height (b.left), height (b.right)) + 1;
        a.height = max (height (a.left), height (a.right)) + 1;
        return a;
    }

    Node rotateLeft (Node a)
    {
        Node b = a.right;
        Node c = b.left;
        b.left = a;
        a.right = c;
        a.height = max (height (a.left), height (a.right)) + 1;
        b.height = max (height (b.left), height (b.right)) + 1;
        return b;
    }

    int getBalance (Node ptr)
    {
        if (ptr == null)
        {
            return 0;
        }
        return height (ptr.left) - height (ptr.right);
    }

    Node insert (Node node, int key)
    {
        if (node == null)
            return (new Node (key));

        if (key < node.key)
            node.left = insert (node.left, key);
        else if (key > node.key)
            node.right = insert (node.right, key);
        else
            return node;
        node.height = 1 + max (height (node.left), height (node.right));
        int balance = getBalance (node);

        if (balance > 1 && key < node.left.key)
            return rotateRight (node);
        if (balance < -1 && key > node.right.key)
            return rotateLeft (node);
        if (balance > 1 && key > node.left.key)
        {
            node.left = rotateLeft (node);
            return rotateRight (node);
        }

        if (balance < -1 && key < node.right.key)
        {
            node.right = rotateRight (node.right);
            return rotateLeft (node);
        }
        return node;
    }

    void preOrder (Node ptr)
    {
        if (ptr != null)
        {
            System.out.print (ptr.key + " ");
            preOrder (ptr.left);
            preOrder (ptr.right);
        }
    }

    void inOrder (Node ptr) {
//        if (ptr != null)
//        inOrder(ptr.left);
//        System.out.print(ptr.key+" ");
//        inOrder(ptr.right);
        if (ptr != null) {
            inOrder(ptr.left);
            System.out.print(ptr.key + " ");
            inOrder(ptr.right);
        }
    }
}
public class AVLInsertion{

    public static void main (String[]args)
    {
        AVLTree tree = new AVLTree ();
        tree.root = tree.insert (tree.root, 5);
        tree.root = tree.insert (tree.root, 3);
        tree.root = tree.insert (tree.root, 7);
//        tree.root = tree.insert (tree.root, 6);
//        tree.root = tree.insert (tree.root, 2);
//        tree.root = tree.insert (tree.root, 1);
        System.out.println ("Tree Traversal");
//        tree.preOrder (tree.root);
//        System.out.println();
        tree.inOrder (tree.root);
    }
} 