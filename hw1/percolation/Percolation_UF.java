public class Percolation {
   
    public int[][] grid;
    public UF uf;
    public int Num;
    
    public Percolation (int N) {
	grid = new int[N][N];
	uf = new UF(N*N);
	Num = N;

        for (int i=0; i<N; i++) {
	    for (int j=0; j<N; j++) {
		grid[i][j] = 1;
	    }
	}
    }

    public void open(int i, int j) {

	if (i <= 0 || i > Num) throw new IndexOutOfBoundsException("row index i out of bounds");
	if (j <= 0 || j > Num) throw new IndexOutOfBoundsException("col index j out of bounds");

	int ir = i-1;
	int jr = j-1;

	grid[ir][jr] = 0;

	int center = ir*Num + jr;
	int left  =  ir*Num + jr -1;
	int right =  ir*Num + jr +1;
	int up    = (ir-1)*Num + jr;
	int down  = (ir+1)*Num + jr;
	 
	if (j-1 >= 1) {
	    if (isOpen(i,j-1)) {
		uf.union(center,left);
	    }
	}

	
	if (j+1 <= Num) {
	    if (isOpen(i,j+1)) {
		uf.union(center,right);
	    }
	}
	

	if (i-1 >= 1) {
	    if (isOpen(i-1,j)) {
		uf.union(center,up);
	    }
	}

 	if (i+1 <= Num) {
	    if (isOpen(i+1,j)) {
		uf.union(center,down);
	    }
	}

    }
   
    public boolean isOpen(int i, int j) {
	if (i <= 0 || i > Num) throw new IndexOutOfBoundsException("row index i out of bounds");
	if (j <= 0 || j > Num) throw new IndexOutOfBoundsException("col index j out of bounds");
	return grid[i-1][j-1] == 0;
    }

    
    public boolean isFull(int i, int j) {

	if (i <= 0 || i > Num) throw new IndexOutOfBoundsException("row index i out of bounds");
	if (j <= 0 || j > Num) throw new IndexOutOfBoundsException("col index j out of bounds");

	boolean full = false;
	
	int ir = i-1;
	int jr = j-1;

	int center = ir*Num+jr;
	for (int itop=0; itop<Num; itop++) {    
	    if (uf.connected(center,itop)) { 
		full = true; 
	    }
	}
   
    	return full;
    }




    public boolean percolates() {
	
	boolean perco = false;

	for (int ibottom=0; ibottom<Num; ibottom++) {
	    for (int itop=0; itop<Num; itop++){
		int bottom = Num*(Num-1)+ibottom;
		int top = itop;
		if (uf.connected(bottom,top)){
		    perco = true;}
	    }

	}
	
	return perco;
    }

    public static void main(String[] args) {
	Percolation p = new Percolation(3);

	System.out.println("isPer? "+ p.uf.count());

	p.open(1,1);
       	System.out.println("isOpen? "+p.isOpen(1,1));
	p.open(2,1);
	System.out.println("isFull? "+p.isFull(2,1));	
	System.out.println("isFull? "+p.isFull(3,1));	
	p.open(3,1);
	System.out.println("isFull? "+ p.isFull(3,1));
	System.out.println("isPer? "+ p.percolates());
	//p.open(2,3);
	System.out.println("isPer? "+ p.uf.count());


    }


}
