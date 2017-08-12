import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;

    private TreeNode root;
    private int size;

    private class TreeNode {
        Point2D point;
        TreeNode left, right;
        boolean alignment;

        public TreeNode(Point2D p, boolean alignment) {
            this.point = p;
            this.alignment = alignment;
        }
    }

    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        validate(p);
        if (root == null) {
            root = new TreeNode(p, VERTICAL);
            return;
        }

        insert2D(root, p);
    }

    public boolean contains(Point2D p) {
        validate(p);
        if (root == null) return false;
        return contains2D(root, p);
    }

    public void draw() {
        draw2D(root, new RectHV(0.0, 0.0, 1.0, 1.0));
    }

    private void draw2D(TreeNode node, RectHV space) {
        if (node == null) return;

        double x = node.point.x();
        double y = node.point.y();
        double xmin = space.xmin();
        double ymin = space.ymin();
        double xmax = space.xmax();
        double ymax = space.ymax();

        // Draw the line, then draw the point.
        if (node.alignment == HORIZONTAL) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xmin, y, xmax, y);
            StdDraw.setPenColor();
            StdDraw.setPenRadius(0.02);
            node.point.draw();
            StdDraw.setPenRadius();
            draw2D(node.left, new RectHV(xmin, ymin, xmax, y));
            draw2D(node.right, new RectHV(xmin, y, xmax, ymax));
        }
        else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x, ymin, x, ymax);
            StdDraw.setPenColor();
            StdDraw.setPenRadius(0.02);
            node.point.draw();
            StdDraw.setPenRadius();
            draw2D(node.left, new RectHV(xmin, ymin, x, ymax));
            draw2D(node.right, new RectHV(x, ymin, xmax, ymax));
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        validate(rect);
        List<Point2D> list = new ArrayList<>();
        range2D(rect, root, new RectHV(0.0, 0.0, 1.0, 1.0), list);
        return list;
    }

    private void range2D(RectHV rect,
                         TreeNode node,
                         RectHV space,
                         List<Point2D> list) {
        if (node == null) return;
        if (rect.contains(node.point)) list.add(node.point);
        if (node.left == null && node.right == null) return;

        double xmin = space.xmin();
        double xmax = space.xmax();
        double ymin = space.ymin();
        double ymax = space.ymax();

        if (node.alignment == HORIZONTAL) {
            double y = node.point.y();
            RectHV upSpace = new RectHV(xmin, y, xmax, ymax);
            RectHV downSpace = new RectHV(xmin, ymin, xmax, y);
            if (rect.intersects(upSpace))
                range2D(rect, node.right, upSpace, list);
            if (rect.intersects(downSpace))
                range2D(rect, node.left, downSpace, list);
        }
        else {
            double x = node.point.x();
            RectHV leftSpace = new RectHV(xmin, ymin, x, ymax);
            RectHV rightSpace = new RectHV(x, ymin, xmax, ymax);
            if (rect.intersects(leftSpace))
                range2D(rect, node.left, leftSpace, list);
            if (rect.intersects(rightSpace))
                range2D(rect, node.right, rightSpace, list);
        }
    }

    public Point2D nearest(Point2D p) {
        validate(p);
        return null;
    }

    private void validate(Object arg) {
        if (arg == null)
            throw new IllegalArgumentException("argument is null");
    }

    private void insert2D(TreeNode node, Point2D p) {
        if (node.point.equals(p)) return;

        double dimNode = node.point.x();
        double dimPoint = p.x();
        boolean newAlignment = !node.alignment;

        if (node.alignment == HORIZONTAL) {
            dimNode = node.point.y();
            dimPoint = p.y();
        }

        if (dimPoint < dimNode) {
            if (node.left != null) insert2D(node.left, p);
            else {
                node.left = new TreeNode(p, newAlignment);
                size++;
            }
        }
        else {
            if (node.right != null) insert2D(node.right, p);
            else {
                node.right = new TreeNode(p, newAlignment);
                size++;
            }
        }
    }

    private boolean contains2D(TreeNode node, Point2D p) {
        if (node == null) return false;

        double dimNode = node.point.x();
        double dimPoint = p.x();
        int cmp = Double.compare(node.point.y(), p.y());

        if (node.alignment == HORIZONTAL) {
            cmp = Double.compare(dimNode, dimPoint);
            dimNode = node.point.y();
            dimPoint = p.y();
        }

        if (dimPoint < dimNode) return contains2D(node.left, p);
        if (dimPoint > dimNode) return contains2D(node.right, p);
        return cmp == 0;
    }

    public static void main(String[] args) {
        KdTree kdtree = new KdTree();
        kdtree.insert(new Point2D(0.7, 0.2));
        kdtree.insert(new Point2D(0.5, 0.4));
        kdtree.insert(new Point2D(0.2, 0.3));
        kdtree.insert(new Point2D(0.4, 0.7));
        kdtree.insert(new Point2D(0.9, 0.6));
        kdtree.draw();
        StdOut.println(kdtree.contains(new Point2D(0.2, 0.3)));
        StdOut.println(kdtree.contains(new Point2D(0.1, 0.2)));
        for (Point2D p : kdtree.range(new RectHV(0.3, 0.6, 0.6, 0.9)))
            StdOut.println(p);
    }
}
