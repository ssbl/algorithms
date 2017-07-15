import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF unionFind;
    private boolean[][] grid;
    private int n;
    private int openSites;
    private int virtualTop;
    private int virtualBottom;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        unionFind = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n][n];
        this.n = n;
        virtualTop = n * n;
        virtualBottom = virtualTop + 1;

        for (int col = 1; col <= n; col++) {
            unionFind.union(xyTo1D(1, col), virtualTop);
            unionFind.union(xyTo1D(n, col), virtualBottom);
        }
    }

    private int xyTo1D(int x, int y) {
        return (x - 1) * n + (y - 1);
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IllegalArgumentException("(" + row + ", " + col + ")");
    }

    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) return;

        grid[row - 1][col - 1] = true;
        openSites++;

        int p = xyTo1D(row, col);
        if (row > 1 && isOpen(row - 1, col)) {
            // if (row == n && isFull(row - 1, col))
            //     unionFind.union(p, virtualBottom);
            unionFind.union(p, xyTo1D(row - 1, col));
        }
        if (row < n && isOpen(row + 1, col)) {
            // if (row + 1 == n && isFull(row, col))
            //     unionFind.union(xyTo1D(row + 1, col), virtualBottom);
            unionFind.union(p, xyTo1D(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1))
            unionFind.union(p, xyTo1D(row, col - 1));
        if (col < n && isOpen(row, col + 1))
            unionFind.union(p, xyTo1D(row, col + 1));
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) return false;
        return unionFind.connected(virtualTop, xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        if (n == 1 && isOpen(1, 1)) return true;
        return unionFind.connected(virtualTop, virtualBottom);
    }

    public static void main(String[] args) { }
}
