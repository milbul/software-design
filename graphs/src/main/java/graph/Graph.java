package graph;

import drawing.DrawingApi;
import drawing.FxDrawingApi;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public abstract class Graph {
    private final DrawingApi drawingApi;
    private final Point2D center;
    private final double radius;

    private final Map<Integer, Point2D> verticesCoord = new HashMap<>();


    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
        long w = drawingApi.getDrawingAreaWidth();
        long h = drawingApi.getDrawingAreaHeight();
        center = new Point2D.Double(w / 2.0, h / 2.0);
        radius = w * 0.3;
    }

    public abstract void readGraph();

    public abstract void drawGraph();

    public void draw() {
        readGraph();
        drawGraph();
        drawingApi.drawAll();
    }

    void drawVertex(int a, int vertices) {
        double verticesRadius = (double) drawingApi.getDrawingAreaWidth() / Math.max(vertices, 15) * 0.7;
        double angle = a * 2 * Math.PI / vertices;
        double x = center.getX() + radius * Math.cos(angle);
        double y = center.getY() + radius * Math.sin(angle);
        drawingApi.drawCircle(x, y, verticesRadius);
        verticesCoord.put(a, new Point2D.Double(x, y));
    }

    void drawEdge(int u, int v) {
        Point2D a = verticesCoord.get(u);
        Point2D b = verticesCoord.get(v);
        drawingApi.drawLine(a.getX(), a.getY(), b.getX(), b.getY());
    }
}
