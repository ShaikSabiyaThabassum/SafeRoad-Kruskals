
import java.util.*;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    Edge(int s, int d, int w) {
        src = s;
        dest = d;
        weight = w;
    }

    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class Subset {
    int parent, rank;
}

public class SafeRoadKruskal {

    int V, E;
    Edge[] edges;

    SafeRoadKruskal(int v, int e) {
        V = v;
        E = e;
        edges = new Edge[e];
    }

    int find(Subset subsets[], int i) {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);

        return subsets[i].parent;
    }

    void union(Subset subsets[], int x, int y) {

        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;

        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;

        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    void kruskalMST() {

        Edge result[] = new Edge[V - 1];

        Arrays.sort(edges);

        Subset subsets[] = new Subset[V];

        for (int i = 0; i < V; i++) {
            subsets[i] = new Subset();
            subsets[i].parent = i;
            subsets[i].rank = 0;
        }

        int e = 0;
        int i = 0;

        while (e < V - 1 && i < E) {

            Edge nextEdge = edges[i++];

            int x = find(subsets, nextEdge.src);
            int y = find(subsets, nextEdge.dest);

            if (x != y) {

                result[e++] = nextEdge;

                System.out.println(
                        "Accepted Edge: " +
                        nextEdge.src + " - " +
                        nextEdge.dest + " Cost = " +
                        nextEdge.weight);

                union(subsets, x, y);

            } else {

                System.out.println(
                        "Rejected Edge: " +
                        nextEdge.src + " - " +
                        nextEdge.dest + " (Cycle)");
            }
        }

        int totalCost = 0;

        System.out.println("\nMST EDGES:");

        for (i = 0; i < e; i++) {

            System.out.println(
                    result[i].src + " -- " +
                    result[i].dest +
                    " == " + result[i].weight);

            totalCost += result[i].weight;
        }

        System.out.println("\nTotal MST Cost = " + totalCost);
    }

    public static void main(String[] args) {

        /*
        Vertex Mapping

        0 = C (Control Hub)
        1 = A (Airport Corridor)
        2 = H (Highway Junction)
        3 = S (Smart Signal Zone)
        4 = I (Industrial Area)
        5 = R (Ring Road)
        6 = M (Market District)
        */

        SafeRoadKruskal graph =
                new SafeRoadKruskal(7, 12);

        graph.edges[0] = new Edge(0, 1, 4); // C-A
        graph.edges[1] = new Edge(0, 2, 6); // C-H
        graph.edges[2] = new Edge(0, 3, 5); // C-S
        graph.edges[3] = new Edge(1, 2, 2); // A-H
        graph.edges[4] = new Edge(1, 4, 7); // A-I
        graph.edges[5] = new Edge(2, 3, 3); // H-S
        graph.edges[6] = new Edge(2, 5, 8); // H-R
        graph.edges[7] = new Edge(3, 4, 4); // S-I
        graph.edges[8] = new Edge(3, 6, 6); // S-M
        graph.edges[9] = new Edge(4, 5, 5); // I-R
        graph.edges[10] = new Edge(4, 6, 7); // I-M
        graph.edges[11] = new Edge(5, 6, 9); // R-M

        System.out.println(
                "SafeRoad - Minimum Cost Smart Traffic Sensor Network\n");

        graph.kruskalMST();
    }
}