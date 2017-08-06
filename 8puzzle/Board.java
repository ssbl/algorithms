import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int moves;
    private String repr;

    private final int n;
    private final int[][] blocks;

    public Board(int[][] blocks) {
        this.moves = 0;
        this.n = blocks.length;
        this.blocks = new int[n][n];
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                this.blocks[row][col] = blocks[row][col];
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int nn = n*n;
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
        int nn = n*n;

        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                if (blocks[row][col] != (row*n + col + 1) % nn)
                    return false;

        return true;
    }

    public Board twin() {
        return null;
    }

    public boolean equals(Object y) {
        return false;
    }

    public Iterable<Board> neighbors() {
        return null;
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

    public static void main(String[] args) {
        int blocks[][] = {
            { 8, 1, 3 },
            { 4, 0, 2 },
            { 7, 6, 5 },
        };

        Board board = new Board(blocks);
        StdOut.print(board);
        StdOut.println("hamming   = " + board.hamming());
        StdOut.println("manhattan = " + board.manhattan());
    }

}
