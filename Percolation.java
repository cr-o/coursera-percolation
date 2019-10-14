import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
// import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;
// import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private boolean grid[][];
    private WeightedQuickUnionUF QU;
    private int inputN;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        QU = new WeightedQuickUnionUF(n * n + 2);
        inputN = n;
        grid = new boolean[n][n]; // rows, cols
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
                if (i == 0) {
                    QU.union(n + 1, n * i + j);   // join all of top row with "top" virtual element
                }
                if (i == n - 1) {
                    QU.union(n + 2, n * i + j); // join all of top row with "bottom" virtual element
                }
                // System.out.printf(" %b", grid[i][j]);
            }
            // System.out.println();
        }
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!grid[row][col]) {
            grid[row][col] = true;
        }
        if (row - 1 >= 0) {
            QU.union(inputN * (row) + col, inputN * (row - 1) + col);
        }
        if (row + 1 < inputN) {
            QU.union(inputN * (row) + col, inputN * (row + 1) + col);
        }
        if (col - 1 >= 0) {
            QU.union(inputN * (row) + col, inputN * (row) + col - 1);
        }
        if (col + 1 < inputN) {
            QU.union(inputN * (row) + col, inputN * (row) + col + 1);
        }
        // make sure checking row is within bounds
        // connect this to all adjacent sites (left right top bottom)
        // top      grid[row-1][col]
        // bottom   grid[row+1][col]
        // left     grid[row][col-1]
        // right    grid[row][col+1]
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return QU.connected(inputN * (row) + col, inputN * inputN);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < inputN; i++) {
            for (int j = 0; j < inputN; j++) {
                if (grid[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    // // does the system percolate?
    public boolean percolates() {
        return QU.connected(inputN * inputN, inputN * inputN + 1);
    }


    // test client (optional)
    public static void main(String[] args) {
        int n = 20;
        if (args.length == 1) {
            n = Integer.parseInt(args[0]);
        }
        Percolation testPrec = new Percolation(n);


        // testPrec.open(0, 0);
        // System.out.printf("%b", testPrec.isOpen(0, 0));
        // System.out.println();
        //
        // System.out.printf("%b", testPrec.percolates());
        // System.out.println();
        //
        // testPrec.open(0, 1);
        // System.out.printf("%b", testPrec.isOpen(0, 1));
        // System.out.println();
        //
        // System.out.printf("%b", testPrec.percolates());
        // System.out.println();
        //
        // testPrec.open(0, 2);
        // System.out.printf("%b", testPrec.isOpen(0, 2));
        // System.out.println();
        //
        // System.out.printf("%b", testPrec.percolates());
        // System.out.println();

    }
}