import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final WeightedQuickUnionUF qU;
    private final int inputN;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                    "This grid size is not valid. Please enter a value greater than 0.");
        }
        qU = new WeightedQuickUnionUF(n * n + 2);
        inputN = n;
        grid = new boolean[n][n]; // rows, cols
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
                if (i == 0) {
                    qU.union(n * i + j, n * n); // join all of top row with "top" virtual element
                }
                if (i == n - 1) {
                    qU.union(n * i + j, n * n + 1); // join bottom row with "bottom" virtual element
                }
                // System.out.printf(" %b", grid[i][j]);
            }
            // System.out.println();
        }
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > inputN || col < 1 || col > inputN) {
            throw new IllegalArgumentException("Arguments are out of bounds");
        }

        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
        }
        if (row - 2 >= 0) {
            if (isOpen(row - 1, col)) {
                qU.union(inputN * (row - 1) + col - 1, inputN * (row - 2) + col - 1);
            }
        }
        if (row < inputN) {
            if (isOpen(row + 1, col)) {
                qU.union(inputN * (row - 1) + col - 1, inputN * (row) + col - 1);
            }
        }
        if (col - 2 >= 0) {
            if (isOpen(row, col - 1)) {
                qU.union(inputN * (row - 1) + col - 1, inputN * (row - 1) + col - 2);
            }
        }
        if (col < inputN) {
            if (isOpen(row, col + 1)) {
                qU.union(inputN * (row - 1) + col - 1, inputN * (row - 1) + col);
            }
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
        if (row < 1 || row > inputN || col < 1 || col > inputN) {
            throw new IllegalArgumentException("Arguments are out of bounds");
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > inputN || col < 1 || col > inputN) {
            throw new IllegalArgumentException("Arguments are out of bounds");
        }
        if (!isOpen(row, col)) {
            return false;
        }
        else {
            return qU.connected(inputN * (row - 1) + col - 1, inputN * inputN);
        }
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
        return qU.connected(inputN * inputN, inputN * inputN + 1);
    }


    // test client (optional)
    public static void main(String[] args) {
        // blank
    }
}
