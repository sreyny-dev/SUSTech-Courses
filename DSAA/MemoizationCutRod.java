package lab11;

import java.util.Arrays;
import java.util.Scanner;

public class MemoizationCutRod {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = 4;
//        int n =sc.nextInt();
//        int [] price=new int[n+1];
//        for(int i=1; i<n+1;i++){
//            if(sc.hasNextInt()){
//                price[i]=sc.nextInt();
//            }
//        }
        int[] price = new int[50];
        price[1] = 2;
        price[2] = 5;
        price[3] = 9;
        price[4] = 10;
        price[5] = 10;
        price[6] = 17;
        price[7] = 18;
        price[8] = 20;
        price[9] = 24;
        price[10] = 30;
        for (int i = 11; i < 50; i++) {
            price[i] = price[i - 1] + 5;
        }
        memoizedCutRod(price, n);
    }

    public static int countPieces(int[] s, int n) {
        int count = 0;
        while (n > 0) {
            count++;
            n = n - s[n];
        }
        return count;
    }

    public static void memoizedCutRod(int[] price, int n) {
        int[] memo = new int[n + 1];
        int[] s = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            memo[i] = Integer.MIN_VALUE;
        }
        int maxRevenue = memoizedCutRodAux(price, n, memo, s);
        int pieceCount = countPieces(s, n);
        System.out.println(maxRevenue);
        System.out.println(pieceCount);
        while (n > 0) {
            System.out.print(s[n] + " ");
            n = n - s[n];
        }
        System.out.println(Arrays.toString(s));
    }
    public static int memoizedCutRodAux(int[] price, int n, int[] memo, int[] s) {
        int maxRevenue;
        if (memo[n] >= 0)
            return memo[n]; //if r[1] already calculated, just return that value, no recalculate
        if (n == 0) {
            maxRevenue = 0; //n=0, no revenue
        } else {
            maxRevenue = Integer.MIN_VALUE; //maxR=-infinity
            for (int cut = 1; cut <= n; cut++) {
                int revenue = price[cut] + memoizedCutRodAux(price, n - cut, memo, s);
                if (maxRevenue < revenue) {
                    maxRevenue = revenue;
                    s[n] = cut;
                }
                memo[n] = maxRevenue; //store the maxRevenue obtained into r array
            }
        }
        return maxRevenue;
    }
}