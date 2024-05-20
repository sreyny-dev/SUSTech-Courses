package lab11;

import java.util.Scanner;

public class FibonacciDp {
    public static int fibonacci(int n) {
        int[] fib = new int[n + 1]; //to store fib[i] and can use later
        fib[0] = 0;
        fib[1] = 1;
        for (int i = 2; i < n+1; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib[n];
    }
    public static void main(String[] args) {
        int n = 60;
        int result = fibonacci(n);
        System.out.println("The " + n + "th Fibonacci number is: " + result);
    }
}
//without dp
//public class FibonacciDp {
//    public static void main(String [] args){
//        Scanner sc=new Scanner(System.in);
//        int h=sc.nextInt();
//        System.out.println(1);
//        System.out.println(2);
//        for(int i=2; i<=h;i++){
//            System.out.println(i+": "+ fib(i));
//        }
//    }
//    public static int fib(int h){
//        if(h==0)
//            return 1;
//        else if(h==1)
//            return 2;
//        else
//            return fib(h-1)+fib(h-2)+1;
//    }
//}