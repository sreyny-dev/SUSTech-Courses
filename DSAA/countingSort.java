package lab7;
import java.util.Scanner;

public class countingSort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // numbers to be sorted
        int K = sc.nextInt(); // maximum value
        int[] B = new int[N + 1]; // Initialize the output array with 1-based index
        int[] A = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            A[i] = sc.nextInt();
        }

        CountingSort(A, B, N, K);

        for (int i = 1; i <= N; i++) {
            System.out.print(B[i] + " ");
        }
    }

    public static void CountingSort(int[] A, int[] B, int N, int K) {
        int[] C = new int[K + 1];

        // Initialize counting array C
        for (int i = 0; i <= K; i++) {
            C[i] = 0;
        }


        // Count the occurrences of each value in A
        for (int j = 1; j <= N; j++) {
            C[A[j]]++;
        }

        // Modify the counting array to represent the last index of each value
        for (int i = 1; i <= K; i++) {
            C[i] += C[i - 1];
        }

        // Build the sorted array B
        for (int j = N; j >= 1; j--) {
            B[C[A[j]]] = A[j];
            C[A[j]]--;
        }
    }
}
