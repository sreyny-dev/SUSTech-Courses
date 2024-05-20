package lab8;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Scanner;

class PosfixCalculator {
    // Method to evaluate value of a postfix expression
    static int evaluatePostfix(String exp)
    {
        // Create a stack
        Stack<Integer> stack = new Stack<>();

        // Scan all characters one by one
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (c == ' ')
                continue;

                // If the scanned character is an operand
                // (number here),extract the number
                // Push it to the stack.
            else if (Character.isDigit(c)) {
                int n = 0;

                // Extract the characters and store it in num
                while (Character.isDigit(c)) {
                    n = n * 10 + (int)(c - '0');
                    i++;
                    c = exp.charAt(i);
                }
                i--;
                // Push the number in stack
                stack.push(n);
            }

            // If the scanned character is an operator, pop
            // two elements from stack apply the operator
            else {
                int val1 = stack.pop();
                int val2 = stack.pop();

                switch (c) {
                    case '+':
                        stack.push(val2 + val1);
                        break;
                    case '-':
                        stack.push(val2 - val1);
                        break;
                    case '/':
                        stack.push(val2 / val1);
                        break;
                    case '*':
                        stack.push(val2 * val1);
                        break;
                }
            }
        }
        return stack.pop();
    }

    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        sc.nextLine();
        String exp=sc.nextLine();
        int result= evaluatePostfix(exp);
        System.out.println(result);
    }
}
//Build the stack
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

