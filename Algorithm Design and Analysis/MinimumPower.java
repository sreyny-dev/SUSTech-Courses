import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MinimumPower {

    public static long minPower(long[] power) {
        int n = power.length;
        boolean[] visited = new boolean[n];
        long[] minCost = new long[n];
        Arrays.fill(minCost, Long.MAX_VALUE);
        minCost[0] = 0;

        long minPower = 0;

        for (int i = 0; i < n; i++) {
            long minDist = Long.MAX_VALUE;
            int u = -1;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && minCost[j] < minDist) {
                    minDist = minCost[j];
                    u = j;
                }
            }
            if (u == -1) break; // All vertices are visited

            visited[u] = true;
            minPower += minDist;

            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    long cost = Math.abs(u - v) * Math.abs(power[u] - power[v]);
                    minCost[v] = Math.min(minCost[v], cost);
                }
            }
        }

        return minPower;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        long t = Long.parseLong(br.readLine()); // Number of test cases
        for (int k = 0; k < t; k++) {
            long n = Long.parseLong(br.readLine()); // Size of array
            long[] power = new long[(int) n];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                power[i] = Long.parseLong(st.nextToken());
            }
            System.out.println(minPower(power));
        }
    }
}
