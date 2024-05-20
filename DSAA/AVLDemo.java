package lab10;

import java.util.Scanner;

public class AVLDemo {

    private class Node {

        int key;
        Node left;
        Node right;
        int height;

        public Node(int key) {
            this.key = key;
            this.height = 1;
        }
    }

    private Node root;

    public void insert(int item) {
        this.root = insert(this.root, item);
    }

    private Node insert(Node node, int item) {

        if (node == null) {
            Node nn = new Node(item);
            return nn;
        }

        if (item > node.key) {
            node.right = insert(node.right, item);
        } else if (item < node.key) {
            node.left = insert(node.left, item);
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int bf = bf(node);

        // LL Case
        if (bf > 1 && item < node.left.key) {
            return rightRotate(node);
        }

        // RR Case
        if (bf < -1 && item > node.right.key) {
            return leftRotate(node);
        }

        // LR Case
        if (bf > 1 && item > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL Case
        if (bf < -1 && item < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;

    }

    private int height(Node node) {
        if (node == null) {
            return 0;
        }

        return node.height;
    }

    private int bf(Node node) {
        if (node == null) {
            return 0;
        }

        return height(node.left) - height(node.right);
    }

    private Node rightRotate(Node c) {

        Node b = c.left;
        Node T3 = b.right;

        // rotate
        b.right = c;
        c.left = T3;

        // ht update
        c.height = Math.max(height(c.left), height(c.right)) + 1;
        b.height = Math.max(height(b.left), height(b.right)) + 1;

        return b;
    }

    private Node leftRotate(Node c) {

        Node b = c.right;
        Node T2 = b.left;

        // rotate
        b.left = c;
        c.right = T2;

        // ht update
        c.height = Math.max(height(c.left), height(c.right)) + 1;
        b.height = Math.max(height(b.left), height(b.right)) + 1;

        return b;
    }

    public void display() {
        display(this.root);
    }

    private void display(Node node) {
        if (node != null) {
            display(node.left);
            System.out.print(node.key + " ");
            display(node.right);
        }

    }
    Node minValueNode(Node node)
    {
        Node current = node;

        /* loop down to find the leftmost leaf */
        while (current.left != null)
            current = current.left;

        return current;
    }
    Node deleteNode(Node root, int key) {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (key < root.key)
            root.left = deleteNode(root.left, key);

            // If the key to be deleted is greater than the
            // root's key, then it lies in right subtree
        else if (key > root.key)
            root.right = deleteNode(root.right, key);

            // if key is same as root's key, then this is the node
            // to be deleted
        else
        {

            // node with only one child or no child
            if ((root.left == null) || (root.right == null))
            {
                Node temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                // No child case
                if (temp == null)
                {
                    temp = root;
                    root = null;
                }
                else // One child case
                    root = temp; // Copy the contents of
                // the non-empty child
            }
            else
            {

                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                Node temp = minValueNode(root.right);

                // Copy the inorder successor's data to this node
                root.key = temp.key;

                // Delete the inorder successor
                root.right = deleteNode(root.right, temp.key);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = bf(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && bf(root.left) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && bf(root.left) < 0)
        {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && bf(root.right) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && bf(root.right) > 0)
        {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }
    public static void main(String[] args) {
        AVLDemo tree = new AVLDemo();
        Scanner sc=new Scanner(System.in);
        int N=sc.nextInt();
        for(int i=0;i<N;i++){
            tree.insert(sc.nextInt());
        }
//        tree.insert(3);
//        tree.insert(5);
//        tree.insert(6);
        int M=sc.nextInt();
        for(int i=0; i<M;i++) {
            tree.root = tree.deleteNode(tree.root,sc.nextInt());
        }
        tree.display();

    }

}