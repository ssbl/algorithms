import java.util.ArrayList;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("argument is null");

        ArrayList<LineSegment> list = new ArrayList<>();

        for (int p = 0; p < points.length; p++) {
            Point pp = points[p];
            for (int q = p + 1; q < points.length; q++) {
                Point pq = points[q];
                if (pp.compareTo(pq) == 0)
                    throw new IllegalArgumentException("duplicate point");

                double pqSlope = pp.slopeTo(pq);
                for (int r = q + 1; r < points.length; r++) {
                    Point pr = points[r];
                    double qrSlope = pq.slopeTo(pr);
                    if (pqSlope != qrSlope) continue;

                    for (int s = r + 1; r < points.length; s++) {
                        Point ps = points[s];
                        if (pp.compareTo(ps) == 0
                            || pq.compareTo(ps) == 0
                            || pr.compareTo(ps) == 0)
                            throw new IllegalArgumentException("duplicate point");

                        double rsSlope = pr.slopeTo(ps);
                        if (rsSlope == qrSlope)
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
        return segments;
    }
}
