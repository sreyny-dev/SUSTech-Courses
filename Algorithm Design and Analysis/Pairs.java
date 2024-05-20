
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Pairs {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        long L = Long.parseLong(st.nextToken());
        long R = Long.parseLong(st.nextToken());
        long[] A = new long[(int) (n)];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            A[i] = Long.parseLong(st.nextToken());
        }
        long [] prefixSum=findPrefixSum(A);
        long cnt = mergeAndCount(prefixSum,L,R);
        System.out.println(cnt);
    }
    public static long[] findPrefixSum(long [] arr){
        long [] prefixSum = new long[arr.length+1];
        prefixSum[0] = 0;
        for(int i=1;i<=arr.length;i++){
            prefixSum[i] = prefixSum[i-1] + arr[i-1];
        }
        return prefixSum;
    }

    public static long mergeAndCount(long[] arr,long L,long R) {
        long[] temp = new long[arr.length];
        return mergeSortAndCount(arr, temp, 0, arr.length - 1,L,R);
    }

    public static long mergeSortAndCount(long[] arr, long[] temp, int left, int right, long L, long R) {
        long count=0;
        if (left < right) {
            int mid = (left + right) / 2;
            count+=mergeSortAndCount(arr, temp, left, mid,L,R);
            count+=mergeSortAndCount(arr, temp, mid + 1, right,L,R);
            count += merge(arr, temp, left, mid, right,L,R);
        }
        return count;
    }

    public static long merge(long[] prefixSum, long[] temp, int left, int mid, int right, long L, long R) {
        long count = 0;
        int i = left;
        int j = mid + 1;
        int k = left;

        int p=left;
        int jL=mid+1;
        int jR=mid+1;
        while(p<=mid){
            while (jL<=right&& prefixSum[jL] - prefixSum[p] < L) {
                jL++;
            }
            while (jR<=right&&prefixSum[jR] - prefixSum[p] <= R) {
                jR++;
            }

            count+=jR-jL;
            p++;
        }

        while (i <= mid && j <= right) {
            if (prefixSum[i] <= prefixSum[j]) {
                temp[k++] = prefixSum[i++];
            } else {
                temp[k++] = prefixSum[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = prefixSum[i++];
        }
        while (j <= right) {
            temp[k++] = prefixSum[j++];
        }
        for (i = left; i <= right; i++) {
            prefixSum[i] = temp[i];
        }

        return count;
    }
}
