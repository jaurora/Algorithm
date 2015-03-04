public class PercolationStats {

    private double[] threshold;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {  

        if (N <= 0 || T <= 0) throw new 
        IllegalArgumentException("N T out of bounds");

        threshold = new double[T];
        double overnn = 1./(N*N);
        Percolation p;
        
        for (int it = 0; it < T; it++) {
            p = new Percolation(N);
            while (!p.percolates()) {
                int i = StdRandom.uniform(N);
                int j = StdRandom.uniform(N);
                i = i + 1;
                j = j + 1;
                if (!p.isOpen(i, j)) {
                    p.open(i, j);
                    threshold[it] = threshold[it] + 1;
                }
            }
            threshold[it] = threshold[it]*overnn;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    } 

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }
    
    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        int nT = threshold.length;
        return this.mean()-1.96*this.stddev()/Math.sqrt(nT);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        int nT = threshold.length;
        return this.mean()+1.96*this.stddev()/Math.sqrt(nT);
    }

    // test client (described below)
    public static void main(String[] args) {

        Stopwatch stopwatch = new Stopwatch();
        PercolationStats ps = new PercolationStats(200, 100);
        System.out.println("mean = " + ps.mean());
        System.out.println("dev = " + ps.stddev());
        System.out.println("95% confidence interval = "
          + ps.confidenceLo()+' '+ ps.confidenceHi());
        System.out.println("time used = " + stopwatch.elapsedTime());
    } 
}
