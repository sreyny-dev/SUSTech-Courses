package Lab4.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.Math.abs;

public class MinimalStepsToEqualize {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        for (int t = 0; t < testCase; t++) {
            int n = Integer.parseInt(br.readLine());
            int[] arr = new int[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }
            long result = minimalStepsToEqualize(arr, n);
            System.out.println(result);
        }
    }

    public static long minimalStepsToEqualize(int[] arr, int n) {
        int[] diff = new int[n - 1];
        long sumPositiveDiff = 0;
        long sumNegativeDiff = 0;
        for (int i = 1; i < n; i++) {
            diff[i - 1] = arr[i] - arr[i - 1];
        }
        for (int i = 0; i < n - 1; i++) {
            if (diff[i] >= 0) {
                sumPositiveDiff += diff[i];
            } else {
                sumNegativeDiff += abs(diff[i]);
            }
        }
        long result = Math.min(sumPositiveDiff, abs(sumNegativeDiff)) + abs(sumPositiveDiff - abs(sumNegativeDiff));
        return result;
    }
}
