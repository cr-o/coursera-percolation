/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int[] resultArray;
    private int trialInput;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        int count;
        Percolation percolation;
        trialInput = trials;
        resultArray = new int[trials];
        for (int i = 0; i < trials; i++) {
            count = 0;
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(0, n), StdRandom.uniform(0, n));
                count++;
            }
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
