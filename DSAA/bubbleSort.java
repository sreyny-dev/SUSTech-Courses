package lab6;

import java.util.Scanner;

public class bubbleSort {
    public static void main (String [] args){
        //int array[]={8, 9, 1, 3 , 4 , 5, 6, 6, 8};

        Scanner sc=new Scanner(System.in);
        int number=sc.nextInt();
        int[] array=new int[number];
        for(int i=0; i<number;i++){
            array[i]=sc.nextInt();
        }

        BubbleSort(array);
        for(int i=0;i<array.length;i++){
            System.out.print(array[i]+ " ");

        }
    }
    public static void BubbleSort(int array[]){
        for(int i=0; i<array.length-1;i++){
            for(int j=0; j<array.length-i-1;j++){
                if(array[j]>array[j+1]){
                  int temp = array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                }
            }
            for(int k=0;k<array.length;k++)
                System.out.print(array[k]+ " ");
            System.out.println();
        }
    }
}
