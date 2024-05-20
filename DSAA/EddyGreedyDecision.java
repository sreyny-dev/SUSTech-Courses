package lab12;

public class EddyGreedyDecision {
    public static int minimalStops(int[] distances, int maxDistance, int n) {
        int stops = 0;
        int currentDistance = 0; //l
        for (int i = 1; i < n+1; ) {
            //if l >= d Eddy will not stop
            if (currentDistance + distances[i] <= maxDistance) {
                currentDistance += distances[i]; //update location of Eddy as he moves to next stop
                i++;
            } else { //l<d Eddy have to stop s[i]
                stops++; //increase number of stops
                System.out.println("stop at : s" + i);
                currentDistance = 0; //reset to start a new decision
            }
        }
        return stops;
    }

    public static void main(String[] args) {
        int n=9;
        int maxDistance=100;
        // Example usage
        int[] distances = new int [n+1];
        distances[1]=50;// distance between each stop
        distances[2]=80;
        distances[3]=50;
        distances[4]=50;
        distances[5]=90;
        distances[6]=10;
        distances[7]=90;
        distances[8]=20;
        distances[9]=20;

        int result = minimalStops(distances, maxDistance, n);
        System.out.println("Minimal number of stops: " + result);
    }
}
