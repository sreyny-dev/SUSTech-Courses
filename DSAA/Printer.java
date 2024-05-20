//package lab8;
//
////import java.util.ArrayDeque;
//import java.util.Iterator;
//import java.util.Scanner;
//
//public class Printer {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        int[] request = new int[n];
//        for (int i = 0; i < n; i++) {
//            request[i] = sc.nextInt();
//        }
//        printerJob(request);
//    }
//    public static void printerJob(int[] request) {
//        Queue q= new Queue();
//        for(int i=0; i<request.length;i++) {
//            int r = request[i];
//            if (q.isEmpty() && r == 0) {
//                System.out.println("underflow");
//                // Ignore the request if the queue is empty and the request is 0
//                continue;
//            }
//            if(q.isFull() && r!=0){
//                System.out.println("overflow");
//                continue;
//            }
//            if (r == 0) {
//                if (!q.isEmpty()) {
//                    q.dequeue();
//                }
//            } else {
//                q.enqueue(r);
//            }
//
//            if(q.isEmpty()){
//                System.out.println("empty");
//            }else {
//                for (int element : q) {
//                    System.out.print(element + " ");
//                }
//                System.out.println();
//            }
//        }
//    }
//}
//
////
//class Queue implements Iterable<Integer> {
//    private int[] queue;
//    private int head;
//    private int tail;
//    private int size;
//    private final int MAX_SIZE = 10;
//
//    public Queue() {
//        this.queue = new int[MAX_SIZE];
//        this.head = -1;
//        this.tail = -1;
//        this.size = 0;
//    }
//
//    public boolean isEmpty() {
//        return size == 0;
//    }
//
//    public boolean isFull() {
//        return size == MAX_SIZE;
//    }
//
//    public int getSize() {
//        return size;
//    }
//
//    public void enqueue(int item) {
//        if (isFull()) {
//            return;
//        } else {
//            tail = (tail + 1) % MAX_SIZE;
//            queue[tail] = item;
//            size++;
//        }
//    }
//
//    public int dequeue() {
//        if (isEmpty()) {
//            return -1; // t0 throw an exception
//        } else {
//            head = (head+1) % MAX_SIZE;
//            int dequeuedItem = queue[head];
//            size--;
//            return dequeuedItem;
//        }
//    }
//
//
//    @Override
//    public Iterator<Integer> iterator() {
//        return new QueueIterator();
//    }
//    private class QueueIterator implements Iterator<Integer> {
//        private int currentIndex = head;
//
//        @Override
//        public boolean hasNext() {
//            return currentIndex != tail;
//        }
//
//        @Override
//        public Integer next() {
//            currentIndex = (currentIndex+1) % MAX_SIZE;
//            return queue[currentIndex];
//        }
//    }
//
//}
