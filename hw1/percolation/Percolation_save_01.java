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

            int flag = 0;
            grid[center] = 1;

            int x1, x2;

            if (i == 1 ) {
                grid[center] = 2; 
            }


            if (jr >= 1) {
                if (isOpen(i, jr)) {
                    if (grid[uf.find(left)] == 2 || grid[center] == 2) 
                        { flag = 1; }
                    uf.union(center, left);
                    if (flag == 1) { grid[uf.find(center)] = 2; }
                }
                    
            }
        
            flag = 0;
            if (j+1 <= num) {
                if (isOpen(i, j+1)) {
                    if (grid[uf.find(right)] == 2 || grid[uf.find(center)] == 2) 
                        { flag = 1; }
                    uf.union(center, right);
                    if (flag == 1) { grid[uf.find(center)] = 2; }
                }   
            }

            flag = 0;
            if (ir >= 1) {
                if (isOpen(ir, j)) {
                     if (grid[uf.find(up)] == 2 || grid[uf.find(center)] == 2) 
                        { flag = 1; }
                    uf.union(center, up);
                    if (flag == 1) { grid[uf.find(center)] = 2; }
                }   
            }
      
            flag = 0;
            if (i+1 <= num) {
                if (isOpen(i+1, j)) {
                    if (grid[uf.find(down)] == 2 || grid[uf.find(center)] == 2) 
                        { flag = 1; }
                    uf.union(center, down);
                    if (flag == 1) { grid[uf.find(center)] = 2; }
                }   
            }

        }
    }

    public boolean isOpen(int i, int j) {
        if (i <= 0 || i > num || j <= 0 || j > num) throw new 
        IndexOutOfBoundsException("row index i out of bounds");
        int index = (i-1)*num+j-1;
        return grid[index] > 0;
    }

    
    public boolean isFull(int i, int j) {

        if (i <= 0 || i > num || j <= 0 || j > num) throw new 
        IndexOutOfBoundsException("row index i out of bounds");

        int index = (i-1)*num+j-1;
        if (grid[uf.find(index)] == 2) {
            return true;
        } else {
            return false;
        }

    }


    public boolean percolates() {

        boolean perco = false;

        for (int ibottom = 1; ibottom <= num; ibottom++) {
            if (grid[uf.find(num*(num-1)+ibottom-1)] == 2) {
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
