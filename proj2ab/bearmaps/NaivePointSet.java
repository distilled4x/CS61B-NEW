package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    public Point nearest(double x, double y) {
        Point origin = new Point(x,y);
        Point p1 = points.get(0);
        double minDistance = distance(origin, p1);

        for (int i = 1; i < points.size(); i++) {
            Point p2 = points.get(i);
            double distance = distance(origin, p2);

            if (distance < minDistance) {
                minDistance = distance;
                p1 = p2;
            }
        }

        return p1;
    }

    private double distance(Point p1, Point p2) {
        double distance = Point.distance(p1, p2);

        return Math.sqrt(distance);
    }
}
