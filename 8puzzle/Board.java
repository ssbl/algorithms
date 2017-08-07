import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private int n;
    private short[] blocks;
    private int hamming;
    private int manhattan;

    public Board(int[][] blocks) {
        init(flattenShort(blocks));
    }

    private Board(short[] blocks) {
        init(blocks);
    }

    private void init(short[] blocks) {
        this.n = (int) Math.sqrt(blocks.length);
        this.blocks = blocks;
        int hammingDistance = 0;
        int manhattanDistance = 0;

        for (int block = 0; block < n*n; block++) {
            if (blocks[block] == 0) continue;
            int item = blocks[block] - 1;
            manhattanDistance += Math.abs((item / n) - (block / n));
            manhattanDistance += Math.abs((item % n) - (block % n));
            if (item != block)
                hammingDistance++;
        }

        this.hamming = hammingDistance;
        this.manhattan = manhattanDistance;
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
        int nn = n*n;
        for (int block = 0; block < nn; block++)
            if (blocks[block] != (block + 1) % nn)
                return false;

        return true;
    }

    public Board twin() {
        int nn = n*n;
        int block1 = StdRandom.uniform(nn);
        int block2 = StdRandom.uniform(nn);

        while (blocks[block1] == 0)
            block1 = StdRandom.uniform(nn);

        while (blocks[block2] == 0 || block2 == block1)
            block2 = StdRandom.uniform(nn);

        short[] copy = boardCopy();

        copy[block1] = blocks[block2];
        copy[block2] = blocks[block1];

        return new Board(copy);
    }

    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (this.n != that.n) return false;
        for (int block = 0; block < n*n; block++)
            if (this.blocks[block] != that.blocks[block])
                    return false;
        return true;
    }

    public Iterable<Board> neighbors() {
        int zi = 0;
        int zj = 0;
        int zeroIdx = 0;
        ArrayList<Board> neighbors = new ArrayList<>();

        for (int block = 0; block < n*n; block++) {
            if (blocks[block] == 0) {
                zi = block / n;
                zj = block % n;
                zeroIdx = block;
                break;
            }
        }

        if (zi > 0) {
            short[] copy = boardCopy();
            copy[zeroIdx] = blocks[zeroIdx - n];
            copy[zeroIdx - n] = 0;
            neighbors.add(new Board(copy));
        }
        if (zi < n - 1) {
            short[] copy = boardCopy();
            copy[zeroIdx] = blocks[zeroIdx + n];
            copy[zeroIdx + n] = 0;
            neighbors.add(new Board(copy));
        }
        if (zj > 0) {
            short[] copy = boardCopy();
            copy[zeroIdx] = blocks[zeroIdx - 1];
            copy[zeroIdx - 1] = 0;
            neighbors.add(new Board(copy));
        }
        if (zj < n - 1) {
            short[] copy = boardCopy();
            copy[zeroIdx] = blocks[zeroIdx + 1];
            copy[zeroIdx + 1] = 0;
            neighbors.add(new Board(copy));
        }

        return neighbors;
    }

    public String toString() {
        StringBuilder str  = new StringBuilder();

        str.append(n);
        for (int block = 0; block < n*n; block++) {
            if (block % n == 0) str.append("\n");
            str.append(String.format("%2d ", blocks[block]));
        }
        str.append("\n");

        return str.toString();
    }

    private short[] flattenShort(int[][] matrix) {
        int len = matrix.length;
        short[] flat = new short[len*len];

        for (int row = 0; row < len; row++)
            for (int col = 0; col < len; col++)
                flat[row*len + col] = (short) matrix[row][col];

        return flat;
    }

    private short[] boardCopy() {
        return Arrays.copyOf(blocks, n*n);
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
