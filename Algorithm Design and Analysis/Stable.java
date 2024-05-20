import java.util.*;

public class Stable {
    public static void main(String arg[]){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        String [] menName=new String[n+1];
        String [] womenName=new String[n+1];
        int [][] menPref=new int[n+1][n+1];
        int [][] womenPref=new int[n+1][n+1];
        int [][] womenPrefInv=new int [n+1][n+1];
        int [] menGf=new int[n+1];
        int [] womenBf=new int[n+1];
        Map<String,Integer> menMap = new HashMap<>();
        Map<String, Integer> womenMap=new HashMap<>();
       for(int i=1;i<n+1;i++){
           String name=sc.next();
           menMap.put(name,i);
           menName[i]=name;
       }
       for(int i=1;i<n+1;i++){
           String name=sc.next();
           womenMap.put(name,i);
           womenName[i]=name;
       }
       for(int i=1;i<n+1;i++){
           for(int j=1;j<n+1;j++){
               String name=sc.next();
               menPref[i][j]=womenMap.get(name);
           }
       }
       for(int i=1;i<n+1;i++){
           for(int j = 1; j<n+1; j++){
               String name=sc.next();
               womenPref[i][j]=menMap.get(name);
               womenPrefInv[i][menMap.get(name)]=j;
           }
       }
        System.out.println();
        System.out.print(menMap);
        System.out.println();
        System.out.println(womenMap);
        System.out.println("menPref list: ");
        for(int i=1;i<n+1;i++){
            for(int j=1;j<n+1;j++) {
                System.out.print(menPref[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("womenPref list: ");
        for(int i=1;i<n+1;i++){
            for(int j=1;j<n+1;j++) {
                System.out.print(womenPref[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("womenPrefPrev list: ");
        for(int i=1;i<n+1;i++){
            for(int j=1;j<n+1;j++) {
                System.out.print(womenPrefInv[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        stableMatch(n,menPref,womenPrefInv,womenBf,menGf);
        printStable(n,menName, womenName,menGf);

    }
    public static void stableMatch(int n, int menPref[][], int womenPrefInv[][],int womenBf[],int menGf[]){
        Queue<Integer> freeMen=new LinkedList<>();
        for(int i=1;i<n+1;i++){
            freeMen.add(i);
        }
        while(!freeMen.isEmpty()){
            boolean engage=false;
            int m=freeMen.poll(); //select first man from freemen
            for(int i=1;i<n+1;i++){
                int w=menPref[m][i];
                if(womenBf[w]==0){
                    menGf[m]=w;
                    womenBf[w]=m;
                    engage=true;
                    break;
                }
                else{
                    int m1=womenBf[w];
                    if(womenPrefInv[w][m]<womenPrefInv[w][m1]){
                        menGf[m]=w;
                        womenBf[w]=m;
                        freeMen.add(m1);
                        engage=true;
                        break;
                    }
                }

            }
            if(!engage){
                menGf[m]=-1;
            }
        }

    }
    public static void printStable(int n, String menName[], String womenName[], int menGf[]){
        for(int i=1;i<n+1;i++){
            System.out.println(menName[i]+" "+womenName[menGf[i]]);
        }
    }
}
