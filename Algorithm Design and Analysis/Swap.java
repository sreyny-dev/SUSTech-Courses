//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.StringTokenizer;
//
//public class Swap {
//    public static void main(String[] args)throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StringTokenizer st = new StringTokenizer(br.readLine());
//        long n = Long.parseLong(st.nextToken());
//        long [] A=new long[(int) (n)];
//        long [] B=new long[(int) (n)];
//        long[] indicesA = new long[A.length];
//        long[] indicesB = new long[A.length];
//        for (int i = 0; i < A.length; i++) {
//            indicesA[i] = i;
//        }
//        for (int i = 0; i < A.length; i++) {
//            indicesB[i] = i;
//        }
//        st = new StringTokenizer(br.readLine());
//        for (int i = 0; i < n; i++) {
//            A[i] = Long.parseLong(st.nextToken());
//        }
//        st = new StringTokenizer(br.readLine());
//        for (int i = 0; i < n; i++) {
//            B[i]= Long.parseLong(st.nextToken());
//        }
//        quickSortWithIndex(A,indicesA,0,A.length-1);
//        quickSortWithIndex(B,indicesB,0,B.length-1);
//        long [] C=arrayCount(indicesA,indicesB);
//        long cnt=mergeSortAndCount(C,0,C.length-1);
//        System.out.println(cnt);
//
////        for (int i=0;i<C.length;i++) {
////            System.out.print(C[i] + " ");
////        }
////        System.out.println("Sorted array:");
////        for (long num : A) {
////            System.out.print(num + " ");
////        }
////        System.out.println("\nIndices of original elements:");
////        for (long index : indicesA) {
////            System.out.print(index + " ");
////        }
//
//    }
//    public static void quickSortWithIndex(long[] arr, long[] indices, long low, long high) {
//        if (low < high) {
//            long pi = partition(arr, indices, low, high);
//            quickSortWithIndex(arr, indices, low, pi - 1);
//            quickSortWithIndex(arr, indices, pi + 1, high);
//        }
//    }
//
//    public static long partition(long[] arr, long[] indices, long low, long high) {
//        long pivot = arr[(int) high];
//        long i = (low - 1);
//        for (int j = (int) low; j < high; j++) {
//            if (arr[j] < pivot) {
//                i++;
//                // Swap arr[i] and arr[j]
//                long temp = arr[(int) i];
//                arr[(int) i] = arr[j];
//                arr[j] = temp;
//
//                // Swap indices[i] and indices[j]
//                temp = indices[(int) i];
//                indices[(int) i] = indices[j];
//                indices[j] = temp;
//            }
//        }
//        // Swap arr[i+1] and arr[high] (or pivot)
//        long temp = arr[(int) (i + 1)];
//        arr[(int) (i + 1)] = arr[(int) high];
//        arr[(int) high] = temp;
//        // Swap indices[i+1] and indices[high]
//        temp = indices[(int) (i + 1)];
//        indices[(int) (i + 1)] = indices[(int) high];
//        indices[(int) high] = temp;
//        return i + 1;
//    }
//    public static long [] arrayCount(long []indicesA,long []indicesB){
//        long [] C=new long[indicesA.length];
//        for(int i=0;i<indicesA.length;i++){
//            C[(int) indicesA[i]]=indicesB[i];
//        }
//        return C;
//    }
//    public static long mergeAndCount(long[] arr, long p, long q, long r){
//        long n1=q-p+1;
//        long n2=r-q;
//        long [] left=new long [(int) n1];
//        long [] right=new long [(int) n2];
//        for(int i=0;i<n1;i++){
//            left[i]=arr[(int) (p+i)];
//        }
//        for(int j=0;j<n2;j++){
//            right[j]=arr[(int) (q+j+1)];
//        }
//        long swap=0;
//        int i=0,j=0, k= (int) p;
//        while(i<n1 && j<n2){
//            if(left[i]<=right[j]){
//                arr[k]=left[i];
//                i++;
//            }else{
//                arr[k]=right[j];
//                j++;
//                swap+=(q+1)-(p+i);
//            }
//            k++;
//        }
//        while(i<n1){
//            arr[k]=left[i];
//            i++;
//            k++;
//        }
//        while(j<n2){
//            arr[k]=right[j];
//            j++;
//            k++;
//        }
//        return swap;
//    }
//    public static long mergeSortAndCount(long [] arr, long p, long r){
//        long cnt=0;
//        //using merge sort to find inversion cnt
//        if(p<r){
//            long q=(p+r)/2;
//            cnt+=mergeSortAndCount(arr,p,q);
//            cnt+=mergeSortAndCount(arr,q+1,r);
//            cnt+=mergeAndCount(arr,p,q,r);
//        }
//        return cnt;
//    }
//}

//Code above will have stack overflow
//submitted this version
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Swap {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        long[] A = new long[(int) (n)];
        long[] B = new long[(int) (n)];
        long[] indicesA = new long[A.length];
        long[] indicesB = new long[A.length];
        for (int i = 0; i < A.length; i++) {
            indicesA[i] = i;
        }
        for (int i = 0; i < A.length; i++) {
            indicesB[i] = i;
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            A[i] = Long.parseLong(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            B[i] = Long.parseLong(st.nextToken());
        }
        quickSortWithIndex(A, indicesA, 0, A.length - 1);
        quickSortWithIndex(B, indicesB, 0, B.length - 1);
        long[] C = arrayCount(indicesA, indicesB);
        long cnt = mergeAndCount(C);
        System.out.println(cnt);
    }

    public static void quickSortWithIndex(long[] arr, long[] indices, int low, int high) {
        int[] stack = new int[high - low + 1];
        int top = -1;
        stack[++top] = low;
        stack[++top] = high;
        while (top >= 0) {
            high = stack[top--];
            low = stack[top--];
            int p = partition(arr, indices, low, high);
            if (p - 1 > low) {
                stack[++top] = low;
                stack[++top] = p - 1;
            }
            if (p + 1 < high) {
                stack[++top] = p + 1;
                stack[++top] = high;
            }
        }
    }

    public static int partition(long[] arr, long[] indices, int low, int high) {
        long pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                long temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                temp = indices[i];
                indices[i] = indices[j];
                indices[j] = temp;
            }
        }
        long temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        temp = indices[i + 1];
        indices[i + 1] = indices[high];
        indices[high] = temp;

        return i + 1;
    }

    public static long[] arrayCount(long[] indicesA, long[] indicesB) {
        long[] C = new long[indicesA.length];
        for (int i = 0; i < indicesA.length; i++) {
            C[(int) indicesA[i]] = indicesB[i];
        }
        return C;
    }

    public static long mergeAndCount(long[] arr) {
        long[] temp = new long[arr.length];
        return mergeSortAndCount(arr, temp, 0, arr.length - 1);
    }

    public static long mergeSortAndCount(long[] arr, long[] temp, int left, int right) {
        long count = 0;
        if (left < right) {
            int mid = (left + right) / 2;
            count += mergeSortAndCount(arr, temp, left, mid);
            count += mergeSortAndCount(arr, temp, mid + 1, right);
            count += merge(arr, temp, left, mid, right);
        }
        return count;
    }

    public static long merge(long[] arr, long[] temp, int left, int mid, int right) {
        long count = 0;
        int i = left;
        int j = mid + 1;
        int k = left;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                count += (mid - i + 1);
            }
        }
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        for (i = left; i <= right; i++) {
            arr[i] = temp[i];
        }
        return count;
    }
}
