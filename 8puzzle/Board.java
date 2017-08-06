import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int n;
    private final int nn;
    private final int hamming;
    private final int manhattan;
    private final int[][] blocks;
    private final String repr;

    public Board(int[][] blocks) {
        this.n = blocks.length;
        this.nn = n*n;
        this.blocks = new int[n][];
        for (int row = 0; row < n; row++)
            this.blocks[row] = Arrays.copyOf(blocks[row], n);

        int hammingDistance = 0;
        int manhattanDistance = 0;
        StringBuilder str  = new StringBuilder();

        str.append(n + "\n");
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                str.append(String.format("%2d ", blocks[row][col]));
                if (blocks[row][col] == 0) continue;
                int item = blocks[row][col] - 1;
                manhattanDistance += Math.abs(item / n - row);
                manhattanDistance += Math.abs(item % n - col);
                hammingDistance++;
            }
            str.append("\n");
        }

        this.hamming = hammingDistance;
        this.manhattan = manhattanDistance;
        this.repr = str.toString();
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                if (blocks[row][col] != (row*n + col + 1) % nn)
                    return false;

        return true;
    }

    public Board twin() {
        int block1 = StdRandom.uniform(nn);
        int block2 = StdRandom.uniform(nn);

        while (blocks[block1 / n][block1 % n] == 0)
            block1 = StdRandom.uniform(nn);

        while (blocks[block2 / n][block2 % n] == 0 || block2 == block1)
            block2 = StdRandom.uniform(nn);

        int i1 = block1 / n;
        int i2 = block2 / n;
        int j1 = block1 % n;
        int j2 = block2 % n;
        int[][] copy = boardCopy();

        copy[i1][j1] = blocks[i2][j2];
        copy[i2][j2] = blocks[i1][j1];

        return new Board(copy);
    }

    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (this.n != that.n) return false;
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                if (this.blocks[row][col] != that.blocks[row][col])
                    return false;
        return true;
    }

    public Iterable<Board> neighbors() {
        return new Neighbors();
    }

    private class Neighbors implements Iterable<Board> {
        @Override
        public Iterator<Board> iterator() {
            return new NeighborIterator();
        }
    }

    private class NeighborIterator implements Iterator<Board> {
        private ArrayList<Board> neighbors;

        public NeighborIterator() {
            int zi = 0;
            int zj = 0;

            for (int row = 0; row < n; row++) {
                boolean found = false;
                for (int col = 0; col < n; col++) {
                    if (blocks[row][col] == 0) {
                        zi = row;
                        zj = col;
                        found = true;
                    }
                }
                if (found) break;
            }

            neighbors = new ArrayList<>();
            if (zi > 0) {
                int[][] copy = boardCopy();
                copy[zi][zj] = blocks[zi - 1][zj];
                copy[zi - 1][zj] = 0;
                neighbors.add(new Board(copy));
            }
            if (zi < n - 1) {
                int[][] copy = boardCopy();
                copy[zi][zj] = blocks[zi + 1][zj];
                copy[zi + 1][zj] = 0;
                neighbors.add(new Board(copy));
            }
            if (zj > 0) {
                int[][] copy = boardCopy();
                copy[zi][zj] = blocks[zi][zj - 1];
                copy[zi][zj - 1] = 0;
                neighbors.add(new Board(copy));
            }
            if (zj < n - 1) {
                int[][] copy = boardCopy();
                copy[zi][zj] = blocks[zi][zj + 1];
                copy[zi][zj + 1] = 0;
                neighbors.add(new Board(copy));
            }
        }

        @Override
        public boolean hasNext() {
            return !neighbors.isEmpty();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Board next() {
            if (!hasNext()) throw new NoSuchElementException();
            return neighbors.remove(0);
        }
    }

    public String toString() {
        return repr;
    }

    private int[][] boardCopy() {
        int[][] copy = new int[n][];

        for (int row = 0; row < n; row++)
            copy[row] = Arrays.copyOf(blocks[row], n);

        return copy;
    }

    public static void main(String[] args) {
        int[][] blocks = {
            { 8, 1, 3 },
            { 4, 0, 2 },
            { 7, 6, 5 },
        };

        int[][] blocks2 = {
            { 8, 1, 3 },
            { 4, 0, 2 },
            { 7, 6, 2 },
        };

        Board board = new Board(blocks);
        Board board2 = new Board(blocks2);
        StdOut.print(board);
        StdOut.print(board2);
        StdOut.println("hamming   = " + board.hamming());
        StdOut.println("manhattan = " + board.manhattan());
        StdOut.println("boards equal? " + board.equals(board2));

        for (Board neighbor : board.neighbors())
            StdOut.println(neighbor);
    }

}
