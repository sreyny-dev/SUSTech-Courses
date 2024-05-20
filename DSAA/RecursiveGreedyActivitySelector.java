package lab12;

import java.util.ArrayList;
import java.util.Scanner;

public class RecursiveGreedyActivitySelector {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
//        int k=0;
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
        int k=0;
        int [] startTime={0,1,3,0,5,3,5,6,7,8,2,12};
        int [] endTime={0,4,5,6,7,9,9,10,11,12,14,16};

        ArrayList<Integer> A =recursiveGreedyActivitySelector(startTime, endTime,k,n);
        System.out.println(A.size());
    }
    public static ArrayList<Integer>  recursiveGreedyActivitySelector(int []s, int [] f, int k, int n){
        ArrayList<Integer> A =new ArrayList<>();
    int m=k+1;
    while(m<=n && s[m]<f[k]){
        m=m+1;
    }
    if(m<=n){
        System.out.println("m "+ m);
        A.add(m);
        A.addAll(recursiveGreedyActivitySelector(s,f,m,n));
    }
        return A;
    }
}
