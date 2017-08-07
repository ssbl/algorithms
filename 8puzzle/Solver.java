import java.util.ArrayList;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private int moves;
    private boolean solvable;
    private ArrayList<Board> solution;

    private class BoardState {
        private int moves;
        private Board board;
        private BoardState prev;
        private int priority;

        public BoardState(Board board, BoardState prev, int moves) {
            this.moves = moves;
            this.board = board;
            this.prev = prev;
            this.priority = moves + board.manhattan();
        }

        public BoardState(Board board) {
            this.moves = 0;
            this.prev = null;
            this.board = board;
            this.priority = moves + board.manhattan();
        }
    }

    private class ManhattanPriority implements Comparator<BoardState> {
        @Override
        public int compare(BoardState a, BoardState b) {
            return Integer.compare(a.priority, b.priority);
        }
    }

    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("argument is null");

        // Current game tree state.
        MinPQ<BoardState> pq;
        BoardState other;
        BoardState current;
        ArrayList<Board> moveHistory;
        boolean ourTurnNext;

        // Game tree, move history for the initial board and its twin.
        MinPQ<BoardState> ourPQ = new MinPQ<>(new ManhattanPriority());
        MinPQ<BoardState> twinPQ = new MinPQ<>(new ManhattanPriority());

        // Initial state.
        other = new BoardState(initial.twin());
        current = new BoardState(initial);
        pq = ourPQ;
        ourTurnNext = false;

        while (!current.board.isGoal()) {
            for (Board neighbor : current.board.neighbors())
                if (current.prev == null
                    || !neighbor.equals(current.prev.board))
                    pq.insert(new BoardState(neighbor,
                                             current,
                                             current.moves + 1));

            current = other;
            other = pq.delMin();

            if (ourTurnNext) pq = ourPQ;
            else             pq = twinPQ;
            ourTurnNext = !ourTurnNext;
        }

        if (ourTurnNext) {
            this.solvable = false;
            this.solution = null;
            this.moves = -1;
        }
        else {
            this.moves = current.moves;
            moveHistory = new ArrayList<>();
            while (current.prev != null) {
                moveHistory.add(0, current.board);
                current = current.prev;
            }
            moveHistory.add(0, initial);
            this.solution = moveHistory;
            this.solvable = true;
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        if (!solvable) return null;
        return new ArrayList<>(solution);
    }


    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            int length = 0;
            for (Board board : solver.solution()) {
                length++;
                StdOut.println(board);
            }
            StdOut.println("Length of solution      = " + length);
            StdOut.println("Minimum number of moves = " + solver.moves());
        }
    }
}
