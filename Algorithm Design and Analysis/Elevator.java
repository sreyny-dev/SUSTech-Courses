package lab5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

class Node {
    long floor;
    boolean in;

    public Node(long floor, boolean in) {
        this.floor = floor;
        this.in = in;
    }

    @Override
    public String toString() {
        return "Node{" +
                "floor=" + floor +
                ", in=" + in +
                '}';
    }
}

public class Elevator {
    public static long findMinTime(ArrayList<Node> upList, ArrayList<Node> downList, long n, long m, long k) {
        ArrayList<Long> downFloorList = new ArrayList<>(); //for current floor
        long count = 0;
        for (int i = 0; i < downList.size(); i++) {
            if (downList.get(i).in){
                count++;
            } else if (!downList.get(i).in){
                count--;
            }
            if (count > downFloorList.size() * m) {
                downFloorList.add(downList.get(i).floor - 1);
            }
        }

        ArrayList<Long> upFloorList = new ArrayList<>();
        count = 0;
        for (int i = 0; i < upList.size(); i++) {
            if (upList.get(i).in) {
                count++;
            } else if (!upList.get(i).in) {
                count--;
            }
            if (count > upFloorList.size() * m) {
                upFloorList.add(upList.get(i).floor - 1);
            }
        }

        while(downFloorList.size()>upFloorList.size()){
            upFloorList.add(0L);
        }
        while(downFloorList.size()<upFloorList.size()){
            downFloorList.add(0L);
        }
        long result=0;
        for(int i=0;i<downFloorList.size();i++){
            result+=Math.max(downFloorList.get(i),upFloorList.get(i))*2;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        long m = Long.parseLong(st.nextToken());
        long k = Long.parseLong(st.nextToken());

        ArrayList<Node> downList = new ArrayList<Node>();
        ArrayList<Node> upList = new ArrayList<Node>();
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            long enterNode = Long.parseLong(st.nextToken());
            long outNode = Long.parseLong(st.nextToken());

            if (enterNode < outNode) {
                Node node1 = new Node(enterNode, false);
                upList.add(node1);
                Node node2 = new Node(outNode, true);
                upList.add(node2);
            } else if (enterNode > outNode) {
                Node node1 = new Node(enterNode, true);
                downList.add(node1);
                Node node2 = new Node(outNode, false);
                downList.add(node2);
            }
        }

        Collections.sort(downList, new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                if (node1.floor == node2.floor) {
                    return node1.in == node2.in ? 0 : node1.in ? 1 : -1;
                }
                return Long.compare(node2.floor, node1.floor);
            }
        });

        Collections.sort(upList, new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                if (node1.floor == node2.floor) {
                    return node1.in == node2.in ? 0 : node1.in ? 1 : -1;
                }
                return Long.compare(node2.floor, node1.floor);
            }
        });

        long minTime = findMinTime(upList, downList, n, m, k);
        System.out.println(minTime);

//        for (Node node : upList) {
//            System.out.println(node.floor + " : " + node.in);
//        }
//        System.out.println();
//        for (Node node : downList) {
//            System.out.println(node.floor + " : " + node.in);
//        }
//        System.out.println();


    }
}
