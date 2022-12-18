package graph;

import drawing.DrawingApi;

import java.util.ArrayList;
import java.util.Scanner;

public class ListGraph extends Graph {

    private ArrayList<Integer>[] graph;
    private int vertices;

    public ListGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void readGraph() {
        Scanner in = new Scanner(System.in);
        System.out.println("Vertices number: ");
        vertices = in.nextInt();
        System.out.println("Edges number: ");
        int edges = in.nextInt();
        System.out.println("Edges: ");
        graph = new ArrayList[vertices];
        for (int i = 0; i < vertices; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < edges; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            graph[v - 1].add(u - 1);
        }
    }

    @Override
    public void drawGraph() {
        for (int i = 0; i < vertices; i++) {
            drawVertex(i, vertices);
        }
        for (int i = 0; i < vertices; i++) {
            for (int j : graph[i]) {
                drawEdge(i, j);
            }
        }
    }
}
