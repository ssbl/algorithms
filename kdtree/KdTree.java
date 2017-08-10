import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

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
        if (p == null)
            throw new IllegalArgumentException("argument is null");
        if (root == null) {
            root = new TreeNode(p, VERTICAL);
            return;
        }

        insert2D(root, p);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument is null");
        if (root == null) return false;
        return contains2D(root, p);
    }

    public void draw() {
        draw2D(root);
    }

    private void draw2D(TreeNode node) {
        if (node == null) return;
        node.point.draw();
        draw2D(node.left);
        draw2D(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("argument is null");

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

        double xmin = space.xmin();
        double xmax = space.xmax();
        double ymin = space.ymin();
        double ymax = space.ymax();

        if (node.alignment == HORIZONTAL) {
            double y = node.point.y();
            if (rect.intersects(new RectHV(xmin, y, xmax, y))) {
                range2D(rect,
                        node.left,
                        new RectHV(xmin, ymin, xmax, y),
                        list);
                range2D(rect,
                        node.right,
                        new RectHV(xmin, y, xmax, ymax),
                        list);
            }
            else {
                double up = Double.POSITIVE_INFINITY;
                double down = Double.POSITIVE_INFINITY;
                if (node.left != null) down = rect.distanceTo(node.left.point);
                if (node.right != null) up = rect.distanceTo(node.right.point);

                if (up < down)
                    range2D(rect, node.right,
                            new RectHV(xmin, y, xmax, ymax), list);
                else
                    range2D(rect, node.left,
                            new RectHV(xmin, ymin, xmax, y), list);
            }
        }
        else {
            double x = node.point.x();
            if (rect.intersects(new RectHV(x, ymin, x, ymax))) {
                range2D(rect,
                        node.left,
                        new RectHV(xmin, ymin, x, ymax),
                        list);
                range2D(rect,
                        node.right,
                        new RectHV(x, ymin, xmax, ymax),
                        list);
            }
            else {
                double left = Double.POSITIVE_INFINITY;
                double right = Double.POSITIVE_INFINITY;
                if (node.left != null) left = rect.distanceTo(node.left.point);
                if (node.right != null) right = rect.distanceTo(node.right.point);

                if (right < left)
                    range2D(rect, node.right,
                            new RectHV(x, ymin, xmax, ymax), list);
                else
                    range2D(rect, node.left,
                            new RectHV(xmin, ymin, x, ymax), list);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument is null");

        return null;
    }

    private void insert2D(TreeNode node, Point2D p) {
        double dimNode = node.point.x();
        double dimPoint = p.x();
        boolean newAlignment = !node.alignment;

        if (node.alignment == HORIZONTAL) {
            dimNode = node.point.y();
            dimPoint = p.y();
        }

        if (dimPoint < dimNode) {
            if (node.left != null) insert2D(node.left, p);
            else                   node.left = new TreeNode(p, newAlignment);
        }
        else {
            if (node.right != null) insert2D(node.right, p);
            else                    node.right = new TreeNode(p, newAlignment);
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

    }
}
