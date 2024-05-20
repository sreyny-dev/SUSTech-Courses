import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
class Graph {
    int vertices;
    LinkedList<Integer>[] adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjacencyList = new LinkedList[vertices + 1];

        for (int i = 1; i <= vertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }
    public void addEdge(int u, int v) {
        adjacencyList[u].add(v);
        adjacencyList[v].add(u);
    }
    public void printGraph() {
        System.out.println("Graph:");
        for (int i = 1; i <= vertices; i++) {
            for (int neighbor : adjacencyList[i]) {
                if (neighbor > i) { // Print only once for undirected graph
                    System.out.println(i + " " + neighbor);
                }
            }
        }
    }
}

public class findPair {
    public static boolean[] bfs(Graph G, int source, int deletedNode) {
        boolean[] visited = new boolean[G.vertices + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);
        visited[source] = true;//to skip visiting deleted node
        visited[deletedNode] = true; //to skip visiting deleted node
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int neighbor : G.adjacencyList[current]) {
                if (!visited[neighbor]) {
                    queue.offer(neighbor);
                    visited[neighbor] = true;
                }
            }
        }
        return visited;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int a= Integer.parseInt(st.nextToken());
        int b=Integer.parseInt(st.nextToken());
        Graph G = new Graph(N);
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine()); //read new line of input each time
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            G.addEdge(u, v);
        }
        boolean[] countRemovingA=bfs(G,b,a); //return visited node as boolean
        boolean[] countRemovingB=bfs(G,a,b);
        long unreachableRemoveA=0;
        long unreachableRemoveB=0;
       for(int i=1;i<=N;i++){
            if(!countRemovingA[i]){
                unreachableRemoveA++;
            }
            else if(!countRemovingB[i]){
                unreachableRemoveB++;
            }
            }
       long result=unreachableRemoveA*unreachableRemoveB;
        System.out.println(result);
        G.printGraph();
        }
    }

