import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("argument is null");

        int n = points.length;
        ArrayList<Point> list = new ArrayList<>();

        for (Point point : points) {
            if (point == null)
                throw new IllegalArgumentException("point is null");
        }

        for (int p = 0; p < n; p++) {
            Point pp = points[p];

            for (int q = 0; q < n; q++) {
                if (q == p) continue;
                Point pq = points[q];
                if (pp.compareTo(pq) == 0)
                    throw new IllegalArgumentException("duplicate point");

                double pqSlope = pp.slopeTo(pq);
                for (int r = 0; r < n; r++) {
                    if (r == q || r == p) continue;
                    Point pr = points[r];
                    double prSlope = pp.slopeTo(pr);
                    if (Double.compare(pqSlope, prSlope) != 0) continue;

                    for (int s = 0; s < n; s++) {
                        if (s == r || s == q || s == p) continue;
                        Point ps = points[s];

                        if (Double.compare(pp.slopeTo(ps), prSlope) == 0) {
                            Point start = pp, end = pp;
                            if (start.compareTo(pq) > 0) start = pq;
                            if (start.compareTo(pr) > 0) start = pr;
                            if (start.compareTo(ps) > 0) start = ps;
                            if (end.compareTo(pq) < 0)   end = pq;
                            if (end.compareTo(pr) < 0)   end = pr;
                            if (end.compareTo(ps) < 0)   end = ps;
                            list.add(start);
                            list.add(end);
                        }
                    }
                }
            }
        }

        ArrayList<LineSegment> segList = new ArrayList<>();
        ArrayList<Point> uniquePts = new ArrayList<>();
        for (int idx = 0; idx < list.size() - 1; idx += 2) {
            boolean found = false;
            Point p = list.get(idx), q = list.get(idx + 1);
            for (int idx2 = 0; idx2 < uniquePts.size() - 1; idx2 += 2) {
                if (p.compareTo(uniquePts.get(idx2)) == 0
                    && q.compareTo(uniquePts.get(idx2 + 1)) == 0) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                uniquePts.add(p);
                uniquePts.add(q);
                segList.add(new LineSegment(p, q));
            }
        }

        segments = segList.toArray(new LineSegment[segList.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    public static void main(String[] args) {
        if (args.length != 1)
            return;

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
