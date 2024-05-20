package lab9;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Scanner;
//import java.util.Stack;

//node of the binary tree
class node {
    char val;
    node leftChild;
    node rightChild;
    public node(char val) {
        this.val = val;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String exp = sc.nextLine();
        Stack<node> stack = new Stack<>(); //stack to store node
        node root = new node(exp.charAt(0));
        stack.push(root);
        for (int i = 1; i < exp.length(); i++) {
            char x = exp.charAt(i);
            if (x == '(' || x == ',' || x == ')')
                continue;
            if ((x == '+') || (x == '-') || (x == '*') || (x == '/')) {
                node parent = stack.peek();
                if (parent.leftChild == null) {
                    node t = new node(x);
                    parent.leftChild = t;
                    stack.push(t);
                } else {
                    node t = new node(x);
                    parent.rightChild = t;
                    stack.pop();
                    stack.push(t);
                }
            } else {
                node parent = stack.peek();
                if (parent.leftChild == null) {
                    node t = new node(x);
                    parent.leftChild = t;
                } else {
                    node t = new node(x);
                    parent.rightChild = t;
                    stack.pop();
                }

            }
        }
//        System.out.println(); // to debug the tree structure
        //create a tree
        Main tree=new Main();
        tree.inOrder(root);
        System.out.println();
        tree.preOrder(root);
        System.out.println();
        tree.postOrder(root);

    }
    public void inOrder(node node){
        if(node==null) {
            return;
        }
        boolean isOperator = (node.val == '+' || node.val == '-' || node.val == '*' || node.val == '/');
        if(isOperator){
            System.out.print("(");
        }
        inOrder(node.leftChild);
        System.out.print(node.val+"");
        inOrder(node.rightChild);
        if(isOperator){
            System.out.print(")");
        }
    }
    public void preOrder(node node){
        if(node==null)
            return;
        System.out.print(node.val+"");
        preOrder(node.leftChild);
        preOrder(node.rightChild);
    }
    public void postOrder(node node){
        if(node==null){
            return;
        }
            postOrder(node.leftChild);
            postOrder(node.rightChild);
            System.out.print(node.val+"");

    }
}
class Stack<T> {
    private ArrayList<T> elements;

    public Stack() {
        this.elements = new ArrayList<>();
    }

    public void push(T item) {
        elements.add(item);
    }

    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.remove(elements.size() - 1);
    }

    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.get(elements.size() - 1);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() {
        return elements.size();
    }
}
