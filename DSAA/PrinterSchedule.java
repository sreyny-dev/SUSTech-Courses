package lab8;
import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.Queue;
public class PrinterSchedule {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] request = new int[n];
        for (int i = 0; i < n; i++) {
            request[i] = sc.nextInt();
        }
        printerJob(request);
    }
    public static void printerJob(int[] request) {
        Queue<Integer> q = new ArrayDeque<>();
        for(int i=0; i<request.length;i++) {
            int r = request[i];
            if (q.isEmpty() && r == 0) {
                System.out.println("underflow");
                // Ignore the request if the queue is empty and the request is 0
                continue;
            }
            if(q.size()==9 && r!=0){
                System.out.println("overflow");
                continue;
            }
            if (r == 0) {
                if (!q.isEmpty()) {
                    q.poll();
                }
            } else {
                q.offer(r);
            }

            if(q.isEmpty()){
                System.out.println("empty");
            }else {
                for (int element : q) {
                    System.out.print(element + " ");
                }
                System.out.println();
            }
        }
    }
}
