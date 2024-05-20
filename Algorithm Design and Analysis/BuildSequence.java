import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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
    }

    public void printGraph() {
        System.out.println("Graph:");
        for (int i = 1; i <= vertices; i++) {
            for (int neighbor : adjacencyList[i]) {
                System.out.println(i + " " + neighbor);
            }
        }
    }
}

public class BuildSequence {

    public static void main(String[] args)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int t = Integer.parseInt(st.nextToken()); // number of test cases
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken()); // number of integers
            int m =  Integer.parseInt(st.nextToken()); // number of constraints

            Graph G = new Graph(n);

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int a =  Integer.parseInt(st.nextToken());
                int b =  Integer.parseInt(st.nextToken());
                G.addEdge(b, a);
            }

            List<Integer> result = lexicographicallyLargestTopoSort(G.adjacencyList, n);
            if (result == null) {
                System.out.println("-1");
            } else {
                for (int i = result.size()-1; i >=0; i--) {
                    System.out.print((result.get(i)) + " ");
                }
                System.out.println();
            }
        }
    }

    private static List<Integer> lexicographicallyLargestTopoSort(LinkedList<Integer>[] adjacencyList, int n) {
        List<Integer> result=new ArrayList<>();// start form index 0
        PriorityQueue<Integer> queue=new PriorityQueue<>(Collections.reverseOrder()); //maxheap
        boolean[] isVisited=new boolean[n+1];
        int[] inDegree=new int[n+1];

        for(int i=1;i<n+1;i++){
            for(int neighbor:adjacencyList[i]){
                inDegree[neighbor]++;  //inDegree[neighbor]=inDegree[neighbor]=+1
            }
        }
        for(int i=1;i<n+1;i++){
            if(inDegree[i]==0){
                queue.add(i);
            }
        }
        while(!queue.isEmpty()){
            int current=queue.poll();
            result.add(current);
            isVisited[current]=true;
            for(int neighbor:adjacencyList[current]) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0 && !isVisited[neighbor]) {
                    queue.add(neighbor);
                }
            }
        }
        if(result.size()!=n){
            return null;
        }
        return result;
    }
}
