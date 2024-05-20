import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MaxSum {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        long m = Long.parseLong(st.nextToken());
        st = new StringTokenizer(br.readLine());
        long [] arr=new long[(int) (n+1)];
        for (int i = 1; i < n+1; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            long l = Long.parseLong(st.nextToken());
            long r = Long.parseLong(st.nextToken());
            long result=maxSum(arr,l,r);
            System.out.println(result);
        }
    }
    public static long maxSum(long [] arr, long l, long r){
        long maxSum=Integer.MIN_VALUE;
        long max_temp=0;
        for(int i = (int) l; i<=r; i++){
            max_temp+=arr[i];
            if(max_temp>maxSum){
                maxSum=max_temp;
            }
            if(max_temp<0){
                max_temp=0;
            }
        }
        return maxSum;
    }
}