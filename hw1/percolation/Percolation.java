public class Percolation {
   
    private int[] grid;
    private WeightedQuickUnionUF uf;
    private int num;
    private int percod = 0;

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

            int max;

            int multi = ir * num;
            int center = multi + jr;

            int x1, x2;

            if (num == 1) { 
                percod = 1; 
                grid[center] = 2;
                return;
            }

            if (i == 1) {
                grid[center] = 2; 
            } else {
                if (i == num) {
                    grid[center] = 3;
                } else {
                    grid[center] = 1;
                }
            }




            if (jr >= 1) {
                if (isOpen(i, jr)) {
                    x1 = uf.find(multi + jr -1);
                    x2 = center;

                    uf.union(x1, x2); 
                    if (grid[x1] + grid[x2] != 5) {
                        max = Math.max(grid[x1], grid[x2]);
                        grid[x1] = max;
                        grid[x2] = max;
                    } else {
                        percod = 1;
                        grid[x1] = 2;
                        grid[x2] = 2;
                    }                    
                    
                }
            }
        
            if (j+1 <= num) {
                if (isOpen(i, j+1)) {
                    x1 = uf.find(multi + jr +1);
                    x2 = uf.find(center);
                    uf.union(x1, x2);
                    if (grid[x1] + grid[x2] != 5) {
                        max = Math.max(grid[x1], grid[x2]);
                        grid[x1] = max;
                        grid[x2] = max;

                    } else {
                        percod = 1;
                        grid[x1] = 2;
                        grid[x2] = 2;
                    }   
                }
            }


            if (ir >= 1) {
                if (isOpen(ir, j)) {
                    x1 = uf.find(multi - num + jr);
                    x2 = uf.find(center);

                    uf.union(x1, x2);

                    if (grid[x1] + grid[x2] != 5) {
                        max = Math.max(grid[x1], grid[x2]);
                        grid[x1] = max;
                        grid[x2] = max;
                    } else {
                        percod = 1;
                        grid[x1] = 2;
                        grid[x2] = 2;
                    }   
                }
            }
      
            if (i+1 <= num) {
                if (isOpen(i+1, j)) {
                    x1 = uf.find(multi + num + jr);
                    x2 = uf.find(center);

                    uf.union(x1, x2);

                    if (grid[x1] + grid[x2] != 5) {
                        max = Math.max(grid[x1], grid[x2]);
                        grid[x1] = max;
                        grid[x2] = max;
                    } else {
                        percod = 1;
                        grid[x1] = 2;
                        grid[x2] = 2;
                    }   
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

        return grid[uf.find((i-1)*num+j-1)] == 2;

    }


    public boolean percolates() {
        return percod == 1;
    }
   


    public static void main(String[] args) {
        Percolation p = new Percolation(6);
        System.out.println("isPer? "+ p.isFull(1, 1));
        p.open(1, 6);
 
    }
}
