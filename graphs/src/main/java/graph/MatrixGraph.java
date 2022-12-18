package graph;

import drawing.DrawingApi;

import java.util.Scanner;

public class MatrixGraph extends Graph {
    private int[][] matrix;
    private int vertices;


    public MatrixGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void readGraph() {
        Scanner in = new Scanner(System.in);
        System.out.println("Vertices number:");
        vertices = in.nextInt();
        System.out.println("Matrix:");
        matrix = new int[vertices][vertices];
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                matrix[i][j] = in.nextInt();
            }
        }
    }

    @Override
    public void drawGraph() {
        for (int i = 0; i < vertices; i++) {
            drawVertex(i, vertices);
        }
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                if (matrix[i][j] == 1) {
                    drawEdge(i, j);
                }
            }
        }
    }
}
