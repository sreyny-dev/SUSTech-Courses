package lab11;
public class InvestmentDp {
    public static int computeMaximumReturn(int[] prices, int n) {
        // Initialize arrays for maximum return up to day k and ongoing investment up to day k
        int[] A = new int[n+1];
        int[] B = new int[n+1];
        //Initialize base cases
        A[0] = prices[0];
        B[0] = 0;
        // Bottom-up dynamic programming
        for (int i = 1; i < n+1; i++) {
            A[i] = Math.max(prices[i], A[i - 1] + prices[i]);
            B[i] = Math.max(B[i - 1], A[i - 1]);
        }
        return B[n];
    }

    public static void main(String[] args) {
//        int[] prices = {5, -3, -4, 8, -1, 12, -6, 4, 4, -14, 2, 8};
        int n = 19;
        int [] prices=new int[n+1];
        prices[1]=5;
        prices[2]=-3;
        prices[3]=-4;
        prices[4]=8;
        prices[5]=-1;
        prices[6]=12;
        prices[7]=-6;
        prices[8]=4;
        prices[9]=4;
        prices[10]=-14;
        prices[11]=2;
        prices[12]=8;

//      for(int i=1; i<prices.length;i++){
//          System.out.print(i+" "+prices[i]+ " ");
//          System.out.println();
//      }
        int maxReturn = computeMaximumReturn(prices,n);
        System.out.println("Maximum return: " + maxReturn);
    }
}

