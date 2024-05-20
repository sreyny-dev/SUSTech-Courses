package lab12;

import java.util.ArrayList;
import java.util.Scanner;

public class GreedyActivitySelector {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
//        int n= sc.nextInt();
//        int [] startTime=new int[n+1];
//        for(int i=1;i<n+1;i++){
//            startTime[i]=sc.nextInt();
//        }
//        int [] endTime=new int[n+1];
//        for(int i=1;i<n+1;i++){
//            endTime[i]=sc.nextInt();
//        }
        int n=11;
        int [] startTime={0,1,3,0,5,3,5,6,7,8,2,12};
        int [] endTime={0,4,5,6,7,9,9,10,11,12,14,16};
        System.out.println( greedyActivitySelector(startTime, endTime,n));
    }
    public static int greedyActivitySelector(int [] s, int [] f,int n){
        ArrayList<Integer> A = new ArrayList<>();
        A.add(1);

        int k=1;
        for(int m=2;m<n+1;m++){
            if(s[m]>=f[k]){
                A.add(m);
                k=m;
            }
        }
        return A.size();
    }
}
