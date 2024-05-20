package lab12;

import java.util.ArrayList;
//
//public class EddyStop {
//    public static void main(String[] args) {
//        int [] length={30,20,10,20,50};
//        int [] distance={10,20,40,30,40};
//        int startPoint = 0;
//        int numStop = 0;
//        System.out.println(greedyStop(length, distance, startPoint, numStop));
//    }
//    public static int greedyStop(int [] length, int [] distance, int startPoint, int numStop){
//        ArrayList<Integer> length=new ArrayList<>();
//        ArrayList<Integer> distant=new ArrayList<>();
//        numStop=0;
//        for(int i=1;i<distant.size()+1;i++){
//            if(length.get(i) > distant.get(i)){
//                startPoint++;
//                numStop++;
//            }
//
//        }        return numStop;
//    }
//}
import java.util.Arrays;
import java.util.Arrays;

public class EddyCycleRace {
    public static int eddyGreedyCycleRace(int[] distances, int cyclingLimit, int n) {

        int currentPosition = 0;  // Starting point
        int numStops = 0;
        n=1;
        while (n <= 6) {
            if (distances[n] < cyclingLimit) {
//                currentPosition+=distances[n];
                if (distances[n] + distances[n+1] < cyclingLimit) {
                    n++;
                    currentPosition += distances[n]+distances[n+1];
                    System.out.println("n: "+n +"current location: "+currentPosition);
                } else {
                    numStops++;
                    System.out.println("numStop: "+numStops);
                }
            }
//            else {
//                numStops++;
//                System.out.println(currentPosition);
//            }
        }
        return numStops;
    }

    public static void main(String[] args) {
        int n=6;
        // Example usage
        int[] distances = new int [n+1];
        distances[1]=2;// distance between each stop
        distances[2]=2;
        distances[3]=3;
        distances[4]=2;
        distances[5]=5;
        distances[6]=4;
        int cyclingLimit = 6;  // l of Eddy
        int result = eddyGreedyCycleRace(distances, cyclingLimit,n);
        System.out.println("Minimum number of stops: " + result);
    }
}




//public class EddyStop {
//
//    public static void main(String[] args) {
//        int[] length = {0,20,25,30,35};
//        int[] distance = {0,10, 20, 30,40};
//        int startPoint = 0;
//        int numStop = 0;
//        System.out.println(greedyStop(length, distance, startPoint, numStop));
//    }
//
//    public static int greedyStop(int[] lengths, int[] distances, int startPoint, int numStop) {
//        ArrayList<Integer> lengthList = new ArrayList<>();
//        ArrayList<Integer> distanceList = new ArrayList<>();
//
//        for (int len : lengths) {
//            lengthList.add(len);
//        }
//
//        for (int dist : distances) {
//            distanceList.add(dist);
//        }
//
//        numStop = 0;
//
//        for (int i = 0; i < distanceList.size(); i++) {
//            if (lengthList.get(i) < distanceList.get(i)) {
//                startPoint++;
//                numStop++;
//            }
//        }
//
//        return numStop;
//    }
//}

