public class Percolation {
   
    private int[] grid;
    private WeightedQuickUnionUF uf;
    private int num;
    
    public Percolation(int N) {

        if (N <= 0) throw new 
        IllegalArgumentException("grid index N out of bounds");
        
        grid = new int[N*N];
        uf = new WeightedQuickUnionUF(N*N);
        num = N;
    }

    public void open(int i, int j) {

        if (i <= 0 || i > num || j <= 0 || j > num) throw new 
        IndexOutOfBoundsException("row index i or j out of bounds");

        if (!isOpen(i, j)) {

            int ir = i-1;
            int jr = j-1;

            int center = ir*num + jr;
            int left  =  ir*num + jr -1;
            int right =  ir*num + jr +1;
            int up    = (ir-1)*num + jr;
            int down  = (ir+1)*num + jr;

            grid[center] = 1;

            if (jr >= 1) {
                if (isOpen(i, jr)) {
                    uf.union(center, left);
                }
            }
        
            if (j+1 <= num) {
                if (isOpen(i, j+1)) {
                    uf.union(center, right);
                }
            }

            if (ir >= 1) {
                if (isOpen(ir, j)) {
                    uf.union(center, up);
                }
            }

            if (i+1 <= num) {
                if (isOpen(i+1, j)) {
                    uf.union(center, down);
                }
            }
        }
    }

    public boolean isOpen(int i, int j) {
        if (i <= 0 || i > num || j <= 0 || j > num) throw new 
        IndexOutOfBoundsException("row index i out of bounds");
        int index = (i-1)*num+j-1;
        return grid[index] == 1;
    }

    
    public boolean isFull(int i, int j) {

        if (i <= 0 || i > num || j <= 0 || j > num) throw new 
        IndexOutOfBoundsException("row index i out of bounds");

        boolean full = false;

        int index = (i-1)*num+j-1;
        if (grid[index] == 1) {
            int center = (i-1)*num+(j-1);

            for (int itop = 0; itop < num; itop++) {    
                if (uf.connected(center, itop)) { 
                    full = true; 
                    grid[index] == 2;    // flag 2 for full sites
                    break;
                }
            }
        }
        return full;
    }


    public boolean percolates() {

        boolean perco = false;

        for (int ibottom = 1; ibottom <= num; ibottom++) {
            if (grid[uf.find(num, ibottom)] == 2) {
                perco = true;
                break;
            }
        }
        return perco;
    }
   


    public static void main(String[] args) {
        Percolation p = new Percolation(6);
        System.out.println("isPer? "+ p.isFull(1, 1));
        p.open(1, 6);
 
    }
}
