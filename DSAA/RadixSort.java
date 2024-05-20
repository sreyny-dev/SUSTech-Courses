package lab7;


import java.util.Scanner;

public class RadixSort {
    public static void radixSort(String[] arr, int d, int n) {
        for (int i = d - 1; i >= 0; i--) {
            countingSort(arr, i);
        }
    }

    private static void countingSort(String[] arr, int exp) {
        int n = arr.length;
        String[] output = new String[n];
        int[] count = new int[26]; // Assuming only lowercase English letters

        for (int i = 0; i < n; i++) {
            int index = arr[i].charAt(exp) - 'a';
            count[index]++;
        }
        for (int i = 1; i < 26; i++) {
            count[i] += count[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            int index = arr[i].charAt(exp) - 'a';
            output[count[index] - 1] = arr[i];
            count[index]--;
        }
        System.arraycopy(output, 0, arr, 0, n);
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int n= sc.nextInt();  // Number of words
        int d=sc.nextInt(); // Length of each word
        String[] dictionary=new String[n];
        sc.nextLine();
        for(int i=0; i<n ; i++){
            dictionary[i]=sc.nextLine();

        }
        radixSort(dictionary, d, n);
//        System.out.println("After sorting: " + Arrays.toString(dictionary));
        for (String word : dictionary) {
            System.out.println(word);
        }
    }
}