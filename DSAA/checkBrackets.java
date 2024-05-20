//package lab8;
//
//import java.util.ArrayList;
//import java.util.EmptyStackException;
//import java.util.Scanner;
//public class checkBrackets {
//    public static void main(String [] args){
//        Scanner sc=new Scanner(System.in);
//        String bracket= sc.nextLine();
//        if(isBalanced(bracket))
//            System.out.println("Yes");
//        else
//            System.out.println("No");
//    }
//    public static boolean isBalanced(String bracket){
//        Stack<Character> stack=new Stack<Character>();
//        for(int i=0;i<bracket.length();i++){
//            char x=bracket.charAt(i);
//            if(x=='(' || x=='[' || x=='{'){
//                stack.push(x);
//                continue;
//            }
//            if(stack.isEmpty())
//                return false;
//            char check;
//            switch(x){
//                case')':
//                    check=stack.pop();
//                    if(check=='{' || check=='[')
//                        return false;
//                    break;
//                case'}':
//                    check=stack.pop();
//                    if(check=='(' || check=='[')
//                        return false;
//                    break;
//                case']':
//                    check=stack.pop();
//                    if(check=='(' || check=='{')
//                        return false;
//                    break;
//            }
//
//        }
//        return (stack.isEmpty());
//    }
//
//}
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
