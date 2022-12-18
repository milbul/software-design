package drawing;

import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class AwtDrawingApi extends Frame implements DrawingApi {
    private final int width;
    private final int height;
    private final java.util.List<Ellipse2D> circles = new ArrayList<>();
    private final List<Line2D> lines = new ArrayList<>();

    public AwtDrawingApi(int width, int height) {
        this.width = width;
        this.height = height;
    }

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
        circles.add(new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r));
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        lines.add(new Line2D.Double(x1, y1, x2, y2));
    }

    @Override
    public void drawAll() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        setSize(width, height);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D ga = (Graphics2D) g;
        for (Line2D line : lines) {
            ga.setPaint(Color.BLACK);
            ga.draw(line);
        }
        for (Ellipse2D circle : circles) {
            ga.setPaint(Color.ORANGE);
            ga.fill(circle);
        }
    }
}
