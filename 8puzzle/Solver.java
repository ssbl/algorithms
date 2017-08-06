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
        public int moves;
        public Board prev;
        public Board board;

        public BoardState(Board board, Board prev, int moves) {
            this.moves = moves;
            this.prev = prev;
            this.board = board;
        }

        public BoardState(Board board) {
            this.moves = 0;
            this.prev = null;
            this.board = board;
        }

    }

    private class ManhattanPriority implements Comparator<BoardState> {
        @Override
        public int compare(BoardState a, BoardState b) {
            int distance1 = a.board.manhattan() + a.moves + 1;
            int distance2 = b.board.manhattan() + b.moves + 1;

            return Integer.compare(distance1, distance2);
        }
    }

    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("argument is null");

        // Current game tree state.
        MinPQ<BoardState> pq;
        BoardState other;
        BoardState current;
        ArrayList<Board> solution;
        boolean ourTurnNext;

        // Game tree, move history for the initial board and its twin.
        MinPQ<BoardState> ourPQ = new MinPQ<>(new ManhattanPriority());
        MinPQ<BoardState> twinPQ = new MinPQ<>(new ManhattanPriority());
        ArrayList<Board> ourMoves = new ArrayList<>();
        ArrayList<Board> twinMoves = new ArrayList<>();

        // Initial state.
        other = new BoardState(initial.twin());
        current = new BoardState(initial);
        pq = ourPQ;
        solution = ourMoves;
        ourTurnNext = false;

        while (!current.board.isGoal()) {
            for (Board neighbor : current.board.neighbors())
                if (!neighbor.equals(current.prev))
                    pq.insert(new BoardState(neighbor,
                                             current.board,
                                             current.moves + 1));

            solution.add(current.board);
            current = other;
            other = pq.delMin();

            if (ourTurnNext) {
                pq = ourPQ;
                solution = ourMoves;
            }
            else {
                pq = twinPQ;
                solution = twinMoves;
            }

            ourTurnNext = !ourTurnNext;
        }

        if (ourTurnNext) {
            solvable = false;
            this.solution = null;
            this.moves = -1;
        } else {
            solution.add(current.board);
            solvable = true;
            this.solution = solution;
            this.moves = current.moves;
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
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
