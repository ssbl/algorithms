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
        if (row > 1) unionFind.union(xy, xyTo1D(row - 1, col));
        if (row < n) unionFind.union(xy, xyTo1D(row + 1, col));
        if (col > 1) unionFind.union(xy, xyTo1D(row, col - 1));
        if (col < n) unionFind.union(xy, xyTo1D(row, col + 1));
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
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
