import java.lang.IllegalArgumentException;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    private WeightedQuickUnionUF unionFind;
    private boolean[][] grid;
    private int n;
    private int openSites;
    private int virtualTop;
    private int virtualBottom;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        int N = n + 1;

        unionFind = new WeightedQuickUnionUF(N * N);
        grid = new boolean[N][N];
        this.n = n;

        virtualTop = 0;
        virtualBottom = n * n;

        for (int col = 1; col <= n; col++)
            unionFind.union(xyTo1D(1, col), virtualTop);
    }

    private int xyTo1D(int x, int y) {
         return x*n + y;
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IllegalArgumentException("(" + row + ", " + col + ")");
    }

    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) return;

        grid[row][col] = true;
        openSites++;

        int xy = xyTo1D(row, col);
        boolean connectBottom = false;
        if (row > 1 && isOpen(row - 1, col))
            unionFind.union(xy, xyTo1D(row - 1, col));
        if (row < n && isOpen(row + 1, col)) {
            if (row == n - 1) connectBottom = true;
            unionFind.union(xy, xyTo1D(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1))
            unionFind.union(xy, xyTo1D(row, col - 1));
        if (col < n && isOpen(row, col + 1))
            unionFind.union(xy, xyTo1D(row, col + 1));
        if (connectBottom && isFull(row, col))
            unionFind.union(xyTo1D(row + 1, col), virtualBottom);
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
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
        return unionFind.connected(virtualTop, virtualBottom);
    }

    public static void main(String[] args) {

    }
}
