import drawing.AwtDrawingApi;
import drawing.DrawingApi;
import drawing.FxDrawingApi;
import graph.Graph;
import graph.ListGraph;
import graph.MatrixGraph;

public class Main {
    public static void main(String[] args) {
        DrawingApi drawingApi;
        Graph graph;
        int width = 700;
        int height = 700;

        switch (args[0]) {
            case "awt" -> drawingApi = new AwtDrawingApi(width, height);
            case "fx" -> drawingApi = new FxDrawingApi(width, height);
            default -> throw new IllegalArgumentException("first arg should be awt or fx");
        }

        switch (args[1]) {
            case "list" -> graph = new ListGraph(drawingApi);
            case "matrix" -> graph = new MatrixGraph(drawingApi);
            default -> throw new IllegalArgumentException("second arg should be list or matrix");
        }
        graph.draw();
    }
}
