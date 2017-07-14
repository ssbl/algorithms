import java.lang.IllegalArgumentException;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats
{
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
    }

    public double mean() {
        return 0.0;
    }

    public double stddev() {
        return 0.0;
    }

    public double confidenceLo() {
        return 0.0;
    }

    public double confidenceHi() {
        return 0.0;
    }

    public static void main(String[] args) {

    }
}
