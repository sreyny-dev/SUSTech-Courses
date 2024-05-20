package lab8;

// Java program for checking
// balanced brackets
import java.util.*;
//class Stack<T> {
//    private ArrayList<T> elements;
//
//    public Stack() {
//        this.elements = new ArrayList<>();
//    }
//
//    public void push(T item) {
//        elements.add(item);
//    }
//
//    public T pop() {
//        if (isEmpty()) {
//            throw new EmptyStackException();
//        }
//        return elements.remove(elements.size() - 1);
//    }
//
//    public T peek() {
//        if (isEmpty()) {
//            throw new EmptyStackException();
//        }
//        return elements.get(elements.size() - 1);
//    }
//
//    public boolean isEmpty() {
//        return elements.isEmpty();
//    }
//
//    public int size() {
//        return elements.size();
//    }
//}
public class BalancedBrackets {

    // function to check if brackets are balanced
    static boolean areBracketsBalanced(String expr)
    {
        //using Stack class
        Stack <Character> stack= new Stack<Character>();

        // Traversing the Expression
        for (int i = 0; i < expr.length(); i++)
        {
            char x = expr.charAt(i);

            if (x == '(' || x == '[' || x == '{')
            {
                // Push the element in the stack
                stack.push(x);
                continue;
            }

            // If current character is not opening
            // bracket, then it must be closing. So stack
            // cannot be empty at this point.
            if (stack.isEmpty())
                return false;
            char check;
            switch (x) {
                case ')':
                    check = stack.pop();
                    if (check == '{' || check == '[')
                        return false;
                    break;

                case '}':
                    check = stack.pop();
                    if (check == '(' || check == '[')
                        return false;
                    break;

                case ']':
                    check = stack.pop();
                    if (check == '(' || check == '{')
                        return false;
                    break;
            }
        }

        // Check Empty Stack
        return (stack.isEmpty());
    }

    // Driver code
    public static void main(String[] args)
    {
        String expr = "([]){}()";

        // Function call
        if (areBracketsBalanced(expr))
            System.out.println("Balanced ");
        else
            System.out.println("Not Balanced ");
    }
}
