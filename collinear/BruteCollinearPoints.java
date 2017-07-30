import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("argument is null");

        int n = points.length;
        ArrayList<LineSegment> list = new ArrayList<>();

        for (int p = 0; p < n; p++) {
            Point pp = points[p];
            for (int q = p + 1; q < n; q++) {
                Point pq = points[q];
                if (pp.compareTo(pq) == 0)
                    throw new IllegalArgumentException("duplicate point");

                double pqSlope = pp.slopeTo(pq);
                for (int r = q + 1; r < n; r++) {
                    Point pr = points[r];
                    double qrSlope = pq.slopeTo(pr);
                    if (pqSlope != qrSlope) continue;

                    for (int s = r + 1; s < n; s++) {
                        Point ps = points[s];

                        double diff = pr.slopeTo(ps) - qrSlope;
                        if (diff >= 0 && diff <= 0.005)
                            list.add(new LineSegment(pp, ps));
                    }
                }
            }
        }

        segments = list.toArray(new LineSegment[list.size()]);
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
