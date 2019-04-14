package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private pointNode root;

    private class pointNode {
        private Point p;
        private pointNode left;
        private pointNode right;
        private int depth;

        public pointNode(Point p, int depth) {
            this.p = p;
            this.left = null;
            this.right = null;
            this.depth = depth; //depth in tree.  odd depth compare x, even depth compare y
        }
    }


    public KDTree(List<Point> points) { // assume points is non null;
        root = new pointNode(points.get(0), 1);

        for (int i = 1; i < points.size(); i++) {
            put(points.get(i));
        }
    }

    public void put(Point point) {
        if (point == null) throw new IllegalArgumentException();
        this.root = put(root, point, 1);
    }

    private pointNode put(pointNode root, Point point, int depth) {
        if (root == null) return new pointNode(point, depth);
        double compare = compare(root, point);


        if (root.p.equals(point)) {
            root.p = point;

        } else if (compare < 0) {
            root.left = put(root.left, point, depth + 1);

        } else {
            root.right = put(root.right, point, depth + 1);

        }

        return root;
    }

    private double compare(pointNode p1, Point p2) {
        if (p1.depth % 2 != 0) {
            return p2.getX() - p1.p.getX();
        } else {
            return p2.getY() - p1.p.getY();
        }
    }

    private double distance(pointNode point, Point goal) {
        double distance = Point.distance(point.p, goal);

        return Math.sqrt(distance);
    }


    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);

        return nearest(root, goal, root).p;
    }

    private pointNode nearest(pointNode n, Point goal, pointNode best) {
        if (n == null) return best;
        if (distance(n, goal) < distance(best, goal)) best = n;

        pointNode goodSide = null;
        pointNode badSide = null;

        if (compare(n, goal) < 0) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }

        best = nearest(goodSide, goal, best);

        if (badSide != null && (Math.abs(compare(badSide, goal)) < distance(best, goal))) {
            best = nearest(badSide, goal, best);
        }

        return best;
    }
}
