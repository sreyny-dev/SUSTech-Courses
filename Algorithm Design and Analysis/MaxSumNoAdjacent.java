package Lab4.src;

import java.util.*;

class Node {
    long value;
    Node next;
    Node prev;

    public Node(long value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }
}

public class MaxSumNoAdjacent {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();

        Node head = new Node(0);
        Node tail = new Node(0);
        head.next = tail;
        tail.prev = head;
        Node curr = head; // pointer
        PriorityQueue<Node> queue = new PriorityQueue<>(Collections.reverseOrder(Comparator.comparingLong(n1 -> n1.value)));

        for (int i = 0; i < n; i++) {
            Node a = new Node(sc.nextInt());
            a.prev = curr;
            a.next = tail;
            tail.prev = a;
            curr.next = a;
            curr = a; // move pointer to a
            queue.add(a);
        }

//        System.out.println("Values in the queue:");
//        for (Node node : queue) {
//            System.out.print(node.value + " ");
//        }
//        System.out.println(); // Print a newline for formatting

        Set<Node> visitedNodes = new HashSet<>();
        long result = 0;
        for (int i = 0; i < k; i++){

            if (!queue.isEmpty()) {
               Node current = queue.poll();

                if(visitedNodes.contains(current)){
                    i--;
                    continue;
                }
                if(current.value<0){
                    continue;

                }
                if (current == head || current == tail) {
                    continue;
                }
                visitedNodes.add(current);
                result += current.value;//only add to result when current is not visited, if visited continue increase i
                long regret = 0;
                if (current.prev != null) {
                    regret += current.prev.value;
                    visitedNodes.add(current.prev);
                }
                if (current.next != null) {
                    regret += current.next.value;
                    visitedNodes.add(current.next);
                }
                regret -= current.value;

                Node newNode = new Node(regret);

                if (current.prev != null && current.prev.prev != null) {
                    current.prev.prev.next = newNode;
                    newNode.prev = current.prev.prev;
                }

                if (current.next != null && current.next.next != null) {
                    current.next.next.prev = newNode;
                    newNode.next = current.next.next;
                }

                // Remove left and right nodes of current
//                queue.remove(current.prev);
//                queue.remove(current.next);
                queue.add(newNode);
            }
        }
        System.out.println(result);
    }
}
