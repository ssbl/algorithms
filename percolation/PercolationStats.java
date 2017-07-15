import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        Percolation percolation;
        double[] samples = new double[trials];

        for (int trial = 0; trial < trials; trial++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int randomX = StdRandom.uniform(1, n + 1);
                int randomY = StdRandom.uniform(1, n + 1);
                percolation.open(randomX, randomY);
            }
            samples[trial] = (double)percolation.numberOfOpenSites() / (n*n);
        }

        mean = StdStats.mean(samples);
        stddev = StdStats.stddev(samples);
        confidenceLo = mean - 1.96*stddev / Math.sqrt(trials);
        confidenceHi = mean + 1.96*stddev / Math.sqrt(trials);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLo;
    }

    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        int n = 0;
        int trials = 0;

        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        } else System.exit(1);

        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = ["
                       + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
