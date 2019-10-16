/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] resultArray;
    private double trialInput;

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
                int row = StdRandom.uniform(0, n);
                int col = StdRandom.uniform(0, n);
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
        return StdStats.mean(resultArray);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(resultArray);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(trialInput);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(trialInput);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = 20;
        int T = 20;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        }
        PercolationStats percolationStats = new PercolationStats(n, T);
        System.out.printf("mean = %f%n", percolationStats.mean());
        System.out.printf("stddev =% f%n", percolationStats.stddev());
        System.out.printf("95%% confidence interval = %f%n", percolationStats.confidenceLo());
        System.out.printf("95%% confidence interval = %f%n", percolationStats.confidenceHi());
    }
}
