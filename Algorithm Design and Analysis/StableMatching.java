import java.util.*;

class Main {
    public static int n;
    public static String[] menNames;
    public static String[] womenNames;
    public static int[][] menPref;
    public static int[][] womenPref;
    public static int[][] womenPrefInverse;
    public static int[] menGirlFriends;
    public static int[] womenBoyFriends;
    public static Map<String, Integer> menMap;
    public static Map<String, Integer> womenMap;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        scanner.nextLine();
        menNames = new String[n + 1];
        womenNames = new String[n + 1];
        menMap = new HashMap<>();
        womenMap = new HashMap<>();
        menPref = new int[n + 1][n + 1];
        womenPref = new int[n + 1][n + 1];
        womenPrefInverse = new int[n + 1][n + 1];
        menGirlFriends = new int[n + 1];
        womenBoyFriends = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            String name = scanner.next();
            menMap.put(name, i);//name become integer
            menNames[i] = name; //assign integer into menNames
        }

        for (int i = 1; i <= n; i++) {
            String name = scanner.next();
            womenMap.put(name, i);
            womenNames[i] = name;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                String name = scanner.next();
                menPref[i][j] = womenMap.get(name);
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                String name = scanner.next();
                womenPref[i][j] = menMap.get(name);
                womenPrefInverse[i][menMap.get(name)] = j;
            }
        }
        System.out.println();
        System.out.println(menMap);
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


        stableMatch();
        printStableMatch();
    }

//    public static void stableMatch() {
//        Queue<Integer> q = new LinkedList<>();
//        for (int i = 1; i <= n; i++) q.add(i);
//
//        int[] times = new int[n + 1];
//
//        while (!q.isEmpty()) {
//            int freeMen = q.poll();
//
//            int freeWomen = menPref[freeMen][++times[freeMen]];
//            int currentBoyFriend = womenBoyFriends[freeWomen];
//
//            if (currentBoyFriend == 0) {
//                menGirlFriends[freeMen] = freeWomen;
//                womenBoyFriends[freeWomen] = freeMen;
//            } else {
//                if (womenPrefInverse[freeWomen][freeMen] < womenPrefInverse[freeWomen][currentBoyFriend]) {
//                    q.add(currentBoyFriend);
//                    menGirlFriends[freeMen] = freeWomen;
//                    womenBoyFriends[freeWomen] = freeMen;
//                } else {
//                    q.add(freeMen);
//                }
//            }
//        }
//    }
public static void stableMatch() {
    Queue<Integer> freeMen = new LinkedList<>();
    for (int i = 1; i <= n; i++) {
        freeMen.add(i);
    }

    while (!freeMen.isEmpty()) {
        int m = freeMen.poll();
        boolean engaged = false;
        for (int i = 1; i <= n; i++) {
            int w = menPref[m][i];
            if (womenBoyFriends[w] == 0) {
                menGirlFriends[m] = w;
                womenBoyFriends[w] = m;
                engaged = true;
                break;
            } else {
                int m1 = womenBoyFriends[w]; //current bf
                if (womenPrefInverse[w][m] < womenPrefInverse[w][m1]) {
                    menGirlFriends[m] = w;
                    womenBoyFriends[w] = m;
                    freeMen.add(m1);//free m1 add him to queue
                    engaged = true; //that two became engaged
                    break;
                }
            }
        }
        if (!engaged) menGirlFriends[m] = -1;//null
    }
}
    public static void printStableMatch(){
        for (int i = 1; i <= n; i++) {
            System.out.println(menNames[i] + " " + womenNames[menGirlFriends[i]]);
        }
    }
}



