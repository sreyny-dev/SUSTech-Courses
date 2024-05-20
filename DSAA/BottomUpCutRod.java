package lab11;

import java.util.Scanner;

//Normal Button UP
//public class BottomUpCutRod {
//    public static int bottomUpCutRod(int[] p, int n) {
//        int[] r = new int[n + 1]; // Array to store the maximum revenue for each length
//
//        r[0] = 0;
//
//        for (int j = 1; j <= n; j++) {
//            int q = Integer.MIN_VALUE;
//            for (int i = 1; i <= j; i++) {
//                q = Math.max(q, p[i] + r[j - i]);
//            }
//            r[j] = q;
//        }
//
//        return r[n];
//    }
//
//    public static void main(String[] args) {
//        int n=4;
//        int[] p = new int[50];
//        p[1] = 2;
//        p[2] = 5;
//        p[3] = 9;
//        p[4] = 10;
//        p[5] = 10;
////        int[] p = {2,5,9,10}; // Prices for rods of length 1 to n
////        int n = p.length+1; // Length of the rod
//
//        int maxRevenue = bottomUpCutRod(p, n);
//        System.out.println("Maximum revenue: " + maxRevenue);
//    }
//}
//public class BottomUpCutRod {
//    public static int[][] extendedBottomUpCutRod(int[] p, int n) {
//        int[][] result = new int[2][n + 1];
//        int[] r = result[0];
//        int[] s = result[1];
//
//        r[0] = 0;
//
//        for (int j = 1; j <= n; j++) {
//            int q = Integer.MIN_VALUE;
//            for (int i = 1; i <= j; i++) {
//                if (q < p[i] + r[j - i]) {
//                    q = p[i] + r[j - i];
//                    s[j] = i;
//                }
//            }
//            r[j] = q;
//        }
//
//        return result;
//    }
//
//    public static void main(String[] args) {
////        int[] p = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30}; // Prices for rods of length 1 to n
////        int n = p.length - 1; // Length of the rod
//                int n=4;
//        int[] p = new int[50];
//        p[1] = 2;
//        p[2] = 5;
//        p[3] = 9;
//        p[4] = 10;
//        p[5] = 10;
//
//        int[][] result = extendedBottomUpCutRod(p, n);
//        int[] r = result[0];
//        int[] s = result[1];
//
//        // Print optimal cuts
//        int i = n;
//        while (i > 0) {
//            System.out.print(s[i] + " ");
//            i = i - s[i];
//        }
//
//        System.out.println();
//
//        System.out.println("Optimal value: " + r[n]);
//    }
//}
//BottomUp with extended version
public class BottomUpCutRod{
    public static void main(String[] args) {
        int cost=0;
        int n=4;
        int [] price =new int[n+1];
        price[1]=2;
        price[2]=5;
        price[3]=9;
        price[4]=10;
        printCutRodSolution(price,n,cost);
//        Scanner sc = new Scanner(System.in);
//                int n =sc.nextInt();
//                int c=2;
//        int [] price=new int[n+1];
//        for(int i=1; i<n+1;i++){
//            if(sc.hasNextInt()){
//                price[i]=sc.nextInt();
//            }
//        }
//        printCutRodSolution(price,n,c);
    }
    public static int[][] extendedBottomUpCutRod(int [] price, int n, int cost){
        int [][] result=new int[2][n+1];
        int [] revenue=result[0];
        int [] s=result[1];
        revenue[0]=0;
        for(int j=1;j<n+1;j++){
            int maxRevenue=Integer.MIN_VALUE;
            for(int i=1;i<=j;i++){
                if(maxRevenue<price[i]+revenue[j-i]-cost){
                    maxRevenue=price[i]+revenue[j-i]-cost;
                    s[j]=i;
                }
            }
            revenue[j]=maxRevenue;
        }
        return result;
    }
    public static int countPieces(int[] s, int n) {
        int count = 0;
        while (n > 0) {
            count++;
            n = n - s[n];
        }
        return count;
    }
    public static void printCutRodSolution(int [] price, int n,int cost){
        int [][]result=extendedBottomUpCutRod(price, n,cost);
        int [] revenue=result[0];
        int [] s=result[1];
        int maxRevenue=revenue[n];
        int pieceCount = countPieces(s, n);
        System.out.println(maxRevenue);
        System.out.println(pieceCount);
        while(n>0){
            System.out.print(s[n]+" ");;
            n=n-s[n];
        }
    }
}