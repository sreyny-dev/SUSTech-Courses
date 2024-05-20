package Lab14;
import java.util.*;

public class TopologicalSort {
    private static int N;
    private static List<Integer>[] graph;
    private static String[] color;
    private static int[] discoveryTime;
    private static int[] finishTime;
    private static int time;
    private static Stack<Integer> stack;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        N = scanner.nextInt();
        int M = scanner.nextInt();

        graph = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            int vi = scanner.nextInt();
            int vj = scanner.nextInt();
            graph[vi].add(vj);
        }

        scanner.close();

        // Initialize data structures
        color = new String[N + 1];
        discoveryTime = new int[N + 1];
        finishTime = new int[N + 1];
        time = 0;
        stack = new Stack<>();

        // Perform DFS and print topological sort or -1
        if (topologicalSort()) {
            while (!stack.isEmpty()) {
                System.out.print(stack.pop() + " ");
            }
        } else {
            System.out.println("-1");
        }
    }

    private static boolean topologicalSort() {
        for (int u = 1; u <= N; u++) {
            if (color[u] == null) {
                if (!dfsVisit(u)) {
                    return false; // Cycle detected
                }
            }
        }
        return true;
    }

    private static boolean dfsVisit(int u) {
        color[u] = "gray";
        time++;
        discoveryTime[u] = time;

        for (int v : graph[u]) {
            if (color[v] == null) {
                if (!dfsVisit(v)) {
                    return false;
                }
            } else if (color[v].equals("gray")) {
                return false; // Cycle detected
            }
        }

        color[u] = "black";
        time++;
        finishTime[u] = time;
        stack.push(u);

        return true;
    }
}
