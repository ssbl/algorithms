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

        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException("point is null");
        }

        for (int p = 0; p < points.length; p++)
            for (int q = 0; q < points.length; q++)
                if (p != q && points[q].compareTo(points[p]) == 0)
                    throw new IllegalArgumentException("duplicate point");

        if (points.length < 4) {
            segments = new LineSegment[0];
            return;
        }

        ArrayList<Point> segs = new ArrayList<>();
        Point[] others = new Point[points.length - 1];

        for (int p = 0; p < points.length; p++) {
            Point pp = points[p];

            for (int q = 0, index = 0; q < points.length; q++)
                if (q != p) others[index++] = points[q];
            Arrays.sort(others, pp.slopeOrder());

            int count = 0;
            Point start = pp, end = pp;
            for (int q = 1; q < others.length; q++) {
                if (Double.compare(pp.slopeTo(others[q]),
                                   pp.slopeTo(others[q-1])) == 0) {
                    count++;
                    if (start.compareTo(others[q]) > 0) start = others[q];
                    if (start.compareTo(others[q-1]) > 0) start = others[q-1];
                    if (end.compareTo(others[q]) < 0) end = others[q];
                    if (end.compareTo(others[q-1]) < 0) end = others[q-1];
                }
                else {
                    if (count >= 2) {
                        segs.add(start);
                        segs.add(end);
                    }
                    count = 0;
                    start = end = pp;
                }
            }

            if (count >= 2) {
                segs.add(start);
                segs.add(end);
            }
        }

        ArrayList<LineSegment> segList = new ArrayList<>();
        ArrayList<Point> uniquePts = new ArrayList<>();
        for (int idx = 0; idx < segs.size() - 1; idx += 2) {
            boolean found = false;
            Point p = segs.get(idx), q = segs.get(idx + 1);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
