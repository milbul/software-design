package drawing;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class FxDrawingApi extends Application implements DrawingApi {
    private static int width;
    private static int height;
    private static final List<Pair<Point, Double>> circles = new ArrayList<>();
    private static final List<Pair<Point, Point>> lines = new ArrayList<>();

    public FxDrawingApi(int width, int height) {
        FxDrawingApi.width = width;
        FxDrawingApi.height = height;
    }

    public FxDrawingApi() {}


    @Override
    public long getDrawingAreaWidth() {
        return width;
    }

    @Override
    public long getDrawingAreaHeight() {
        return height;
    }

    @Override
    public void drawCircle(double x, double y, double r) {
        circles.add(new Pair<>(new Point(x, y), r));
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        lines.add(new Pair<>(new Point(x1, y1), new Point(x2, y2)));
    }

    @Override
    public void drawAll() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("graph");
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        for (Pair<Point, Point> line : lines) {
            Point a = line.getKey();
            Point b = line.getValue();
            gc.strokeLine(a.getX(), a.getY(), b.getX(), b.getY());
        }
        gc.setFill(Color.ORANGE);
        for (Pair<Point, Double> circle : circles) {
            double r = circle.getValue();
            Point p = circle.getKey();
            gc.fillOval(p.getX() - r, p.getY() - r, 2.0 * r, 2.0 * r);
        }
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
