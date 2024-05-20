package lab9;

import java.util.Scanner;

class node1 {
    char val;
    node1 leftchild;
    node1 rightchild;
    public node1(char  val){
        this.val=val;
    }
}

public class UserInput {
    public static void main(String [] args){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        node1 [] queue=new node1[n+1];
        int head=0;
        int tail=0;
        node1 root=new node1(sc.next().charAt(0));
        int cnt=1;
            queue[tail++]=root;
            while(cnt<n){
                node1 L=new node1(sc.next().charAt(0));
                cnt++;
                queue[head].leftchild=L;
                if(cnt>n){
                    break;
                }
                node1 R=new node1(sc.next().charAt(0));
                cnt++;
                queue[head].rightchild=R;
                head++;
                queue[tail++]=L;
                queue[tail++]=R;
            }
            System.out.println();
    }
}
