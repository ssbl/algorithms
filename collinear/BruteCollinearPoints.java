import java.util.ArrayList;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        ArrayList<LineSegment> list = new ArrayList<>();

        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                if (p.x == q.x && p.y == q.y)
                    throw new IllegalArgumentException();

                double pqSlope = p.slopeTo(q);
                for (int r = q + 1; r < points.length; r++) {
                    double qrSlope = q.slopeTo(r);
                    if (pqSlope != qrSlope) continue;

                    for (int s = r + 1; r < points.length; s++) {
                        if (p.x == s.x && p.y == s.y
                            || q.x == s.x && q.y == s.y
                            || r.x == s.x && r.y == s.y)
                            throw new IllegalArgumentException();

                        double rsSlope = r.slopeTo(s);
                        if (rsSlope == qrSlope)
                            list.add(new LineSegment(p, s));
                    }
                }
            }
        }

        segments = list.toArray();
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments;
    }
}
