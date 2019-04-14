package bearmaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class pointTest {
    List<Point> points;
    Random r;

    public pointTest(int max, int n) {
        r = new Random();
        this.points = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            double x = r.nextDouble() * max;
            double y = r.nextDouble() * max;

            Point p = new Point(x, y);
            points.add(p);
        }
    }

    public List<Point> getPoints() {
        return points;
    }
}
