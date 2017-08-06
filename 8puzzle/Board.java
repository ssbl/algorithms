import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private int moves;
    private String repr;

    private final int n;
    private final int nn;
    private final int[][] blocks;

    public Board(int[][] blocks) {
        this.moves = 0;
        this.n = blocks.length;
        this.nn = n*n;
        this.blocks = new int[n][n];
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                this.blocks[row][col] = blocks[row][col];
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int distance = moves;

        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                if (blocks[row][col] > 0
                    && blocks[row][col] != (row*n + col + 1) % nn)
                    distance++;

        return distance;
    }

    public int manhattan() {
        int distance = moves;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (blocks[row][col] == 0) continue;
                int item = blocks[row][col] - 1;
                distance += Math.abs(item / n - row);
                distance += Math.abs(item % n - col);
            }
        }

        return distance;
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

        while (block2 == block1)
            block2 = StdRandom.uniform(nn);

        int i1 = block1 / n;
        int i2 = block2 / n;
        int j1 = block1 % n;
        int j2 = block2 % n;
        int[][] blocks = boardCopy();

        blocks[i1][j1] = this.blocks[i2][j2];
        blocks[i2][j2] = this.blocks[i1][j1];

        return new Board(blocks);
    }

    @Override
    public boolean equals(Object y) {
        Board that = (Board) y;

        return this.toString().equals(that.toString());
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
            return neighbors.size() != 0;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Board next() {
            return neighbors.remove(0);
        }
    }

    public String toString() {
        int max = -1;
        int spaces = 1;
        StringBuilder repr = new StringBuilder();

        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                if (blocks[row][col] > max)
                    max = blocks[row][col];

        while (max > 0) {
            spaces++;
            max /= 10;
        }
        String fmt = "%" + spaces + "d ";

        repr.append(n);
        repr.append("\n");
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++)
                repr.append(String.format(fmt, blocks[row][col]));
            repr.append("\n");
        }

        return repr.toString();
    }

    private int[][] boardCopy() {
        int[][] copy = new int[n][];

        for (int row = 0; row < n; row++)
            copy[row] = Arrays.copyOf(blocks[row], n);

        return copy;
    }

    public static void main(String[] args) {
        int blocks[][] = {
            { 8, 1, 3 },
            { 4, 0, 2 },
            { 7, 6, 5 },
        };

        int blocks2[][] = {
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
