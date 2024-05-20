package Lab15;

import java.util.*;

class Node {
    int vertex;
    long distance;

    public Node(int vertex, long distance) {
        this.vertex = vertex;
        this.distance = distance;
    }
}

class MinPriorityQueue {
    private final List<Node> heap;
    private final Map<Integer, Integer> nodeIndexMap;

    public MinPriorityQueue(int capacity) {
        heap = new ArrayList<>(capacity);
        nodeIndexMap = new HashMap<>();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void decreaseKey(int vertex, long distance) {
        int index = nodeIndexMap.get(vertex);
        heap.get(index).distance = distance;
        bubbleUp(index);
    }

    public Node extractMin() {
        Node min = heap.get(0);
        Node lastNode = heap.remove(heap.size() - 1);
        nodeIndexMap.remove(min.vertex);

        if (!heap.isEmpty()) {
            heap.set(0, lastNode);
            nodeIndexMap.put(lastNode.vertex, 0);
            bubbleDown(0);
        }

        return min;
    }

    public void insert(Node node) {
        heap.add(node);
        int index = heap.size() - 1;
        nodeIndexMap.put(node.vertex, index);
        bubbleUp(index);
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).distance < heap.get(parentIndex).distance) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void bubbleDown(int index) {
        int minIndex = index;

        while (true) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = 2 * index + 2;

            if (leftChildIndex < heap.size() && heap.get(leftChildIndex).distance < heap.get(minIndex).distance) {
                minIndex = leftChildIndex;
            }

            if (rightChildIndex < heap.size() && heap.get(rightChildIndex).distance < heap.get(minIndex).distance) {
                minIndex = rightChildIndex;
            }

            if (minIndex != index) {
                swap(index, minIndex);
                index = minIndex;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        Collections.swap(heap, i, j);
        nodeIndexMap.put(heap.get(i).vertex, i);
        nodeIndexMap.put(heap.get(j).vertex, j);
    }
}

public class DijkstraAlgorithm {
    private static final long INF = Long.MAX_VALUE;

    public static void dijkstra(List<List<Node>> graph, int source, long[] distances, int[] parents) {
        int n = graph.size();
        MinPriorityQueue minPriorityQueue = new MinPriorityQueue(n);

        for (int i = 0; i < n; i++) {
            distances[i] = INF;
            parents[i] = -1; //initialize parents=null
            minPriorityQueue.insert(new Node(i, distances[i]));
        }

        distances[source] = 0;
        minPriorityQueue.decreaseKey(source, 0);

        while (!minPriorityQueue.isEmpty()) {
            Node u = minPriorityQueue.extractMin();

            for (Node v : graph.get(u.vertex)) {
                long alt = u.distance + v.distance;

                if (alt < distances[v.vertex]) {
                    distances[v.vertex] = alt;
                    parents[v.vertex] = u.vertex;
                    minPriorityQueue.decreaseKey(v.vertex, alt);
                }
            }
        }
    }

    public static List<Integer> printPath(int[] parents, int source, int destination) {
        List<Integer> path = new ArrayList<>();
        for (int at = destination; at != -1; at = parents[at]) {
            path.add(at + 1); // Adding 1 because vertex numbering starts from 1
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int M = scanner.nextInt();

        List<List<Node>> graph = new ArrayList<>(N);

        for (int i = 0; i < N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            int vi = scanner.nextInt() - 1; // Adjusting to 0-based indexing
            int vj = scanner.nextInt() - 1; // Adjusting to 0-based indexing
            long weight = scanner.nextLong();
            graph.get(vi).add(new Node(vj, weight));
        }

        int source = scanner.nextInt() - 1; // Adjusting to 0-based indexing
        int destination = scanner.nextInt() - 1; // Adjusting to 0-based indexing

        long[] distances = new long[N];
        int[] parents = new int[N];

        dijkstra(graph, source, distances, parents);

        List<Integer> path = printPath(parents, source, destination);

        for (int vertex : path) {
            System.out.print(vertex + " ");
        }
    }
}
