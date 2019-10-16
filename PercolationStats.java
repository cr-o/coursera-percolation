import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] resultArray;
    private final double trialInput;

    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        double count;
        Percolation percolation;
        trialInput = (double) trials;
        resultArray = new double[trials];
        for (int i = 0; i < trials; i++) {
            count = 0;
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    count++;
                }
            }
            count = count / (n * n);
            resultArray[i] = count;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(resultArray);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(resultArray);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (CONFIDENCE_95 * stddev) / Math.sqrt(trialInput);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (CONFIDENCE_95 * stddev) / Math.sqrt(trialInput);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = 20;
        int t = 20;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        }
        PercolationStats percolationStats = new PercolationStats(n, t);
        System.out.printf("mean                     = %f%n", percolationStats.mean());
        System.out.printf("stddev                   = %f%n", percolationStats.stddev());
        System.out.printf("95%% confidence interval  = [%f, %f]", percolationStats.confidenceLo(),
                          percolationStats.confidenceHi());
    }
}
