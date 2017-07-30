import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("argument is null");
        if (points.length <= 2)
            throw new IllegalArgumentException("need more points");

        ArrayList<Point> segs = new ArrayList<>();
        Point[] others = new Point[points.length - 1];

        for (int p = 0; p < points.length; p++) {
            Point pp = points[p];

            if (pp == null)
                throw new IllegalArgumentException("point is null");

            for (int q = 0, index = 0; q < points.length; q++) {
                if (q != p) {
                    if (pp.compareTo(points[q]) == 0)
                        throw new IllegalArgumentException("duplicate point");
                    others[index++] = points[q];
                }
            }

            Arrays.sort(others, pp.slopeOrder());

            int count = 0;
            Point start = pp, end = pp;
            double slope = pp.slopeTo(others[0]);
            for (Point pq : others) {
                double diff = pp.slopeTo(pq) - slope;
                if (diff >= 0 && diff <= 0.005) {
                    count++;
                    if (start.compareTo(pq) > 0) start = pq;
                    if (end.compareTo(pq) < 0) end = pq;
                }
                else {
                    if (count >= 3) {
                        segs.add(start);
                        segs.add(end);
                    }
                    slope = pp.slopeTo(pq);
                    count = 1;
                    start = end = pp;
                    if (pp.compareTo(pq) > 0) start = pq;
                    if (pp.compareTo(pq) < 0) end = pq;
                }
            }
        }

        ArrayList<LineSegment> segList = new ArrayList<>();
        ArrayList<Point> uniquePts = new ArrayList<>();
        for (int idx = 0; idx < segs.size() - 1; idx += 2) {
            boolean found = false;
            for (int idx2 = 0; idx2 < uniquePts.size() - 1; idx2 += 2) {
                if (segs.get(idx).compareTo(uniquePts.get(idx2)) == 0) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                uniquePts.add(segs.get(idx));
                uniquePts.add(segs.get(idx + 1));
                segList.add(new LineSegment(segs.get(idx), segs.get(idx + 1)));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
