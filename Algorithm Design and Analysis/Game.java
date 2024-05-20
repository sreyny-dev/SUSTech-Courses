package lab5;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Game {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long N = Long.parseLong(st.nextToken());
        long I = Long.parseLong(st.nextToken());
        long M = Long.parseLong(st.nextToken());
        long K = Long.parseLong(st.nextToken());
        long[] prices = new long[(int) M];
        st = new StringTokenizer(br.readLine()); // Reset the tokenizer for prices
        for (int i = 0; i < M; i++) {
            prices[i] = Long.parseLong(st.nextToken());
        }
        minDay(prices, N, I, M, K);
    }
    public static long getTotalPrice(long [] prices){
        long sum=0;
        for(int i=0;i<prices.length;i++){
            sum+=prices[i];
        }
        return sum;
    }
    public static void minDay(long[] prices, long N, long I, long M, long K){
        long day=1;
        long totalPrice=getTotalPrice(prices);
        long deposit=N;
        Arrays.sort(prices);
        int pointer=0;
        if(K>0) {
            while (deposit < totalPrice) {
                day++;
                if (pointer < prices.length) {
                    while (prices[pointer] - (day - 1) * K <= 0) {
                        if (pointer == prices.length - 1) {
                            break;
                        }
                        pointer++;
                    }
                }
                if (pointer == prices.length - 1) {
                    pointer++;
                }
                    if (pointer <= prices.length - 1) {
                        totalPrice = totalPrice - (M - pointer) * K;  //skip caculating after check last index
                }
                deposit = N + I * day;
            }
            System.out.print(day+" ");
            if(deposit-I<totalPrice){
                System.out.print("evening");
            }else {
                System.out.print("morning");
            }
        }else if (K==0){
            //just solve equation
            day= (long) Math.ceil((double) (totalPrice - N) /I);
            System.out.print(day+" ");
            System.out.print("evening");

        }

    }
}