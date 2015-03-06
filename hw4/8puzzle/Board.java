import java.util.Iterator;

public class Board {

    private int[] board;
    private int[] arr = new int[4];
    private int nn = 0;    
    private int izero = 0;
    private int ndim = 0;
    // construct a board from an N-by-N array of blocks
    public Board(int[][] blocks) {           
        if (blocks == null) throw new
         java.lang.NullPointerException("Null is not allowed!");
        ndim = blocks.length;
        board = new int[ndim*ndim];

        int ix = 0;
        int iy = 0;

        for (int i = 0; i < ndim; i++) {
             for (int j = 0; j < ndim; j++) {
                 board[i*ndim+j] = blocks[i][j];
                 if (board[i*ndim+j] == 0) {
                     izero = i*ndim+j;
                     ix = i;
                     iy = j;
                 }
             }
        }
            

        if (ix > 0) {
            arr[0] = (ix-1)*ndim + iy;
            nn++;
        } else {
            arr[0] = -1;
        }

        if (iy > 0) {
            arr[1] = ix*ndim + iy-1;
            nn++;
        } else {
            arr[1] = -1;
        }   

        if (ix < ndim-1) {
            arr[2] = (ix+1)*ndim + iy;
             nn++;
        } else {
            arr[2] = -1;
        }
        
        if (iy < ndim-1) {
            arr[3] = ix*ndim + iy + 1;
            nn++;
        } else {
            arr[3] = -1;
        }         
    }

    
    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {                 // board dimension N
        return  ndim;
     }
    
    public int hamming() {                  // number of blocks out of place
        int count = 0;
        for (int i = 0; i < ndim*ndim; i++) {
            if (board[i] != i+1 && board[i] != 0) count++;
        }   
        return count;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {                
        int dist = 0;
        for (int i = 0; i < ndim; i++) {
            for (int j = 0; j < ndim; j++) {
                int p = i*ndim+j;
                if (board[p] != p+1 && board[p] != 0) {
                    int nrow = (board[p]-1)/ndim;
                    int ncol = board[p]-1-nrow*ndim;
                    dist = dist + Math.abs(i - nrow) + Math.abs(j - ncol);
                }
            }           
        }
        return dist;       
    }

    public boolean isGoal() {               // is this board the goal board?
        boolean goal = true;
        for (int i = 0; i < ndim*ndim; i++) {
            if (board[i] != i+1 && board[i] != 0) {
                goal = false;
                break;
            }
        }
        return goal;  
    }

    // a boadr that is obtained by exchanging two adjacent blocks in the same row
   public Board twin() {                
        int[][] tw = new int[ndim][ndim]; 
        for (int i = 0; i < ndim; i++) {
            for (int j = 0; j < ndim; j++) {                        
                tw[i][j] = board[i*ndim+j];
            }
        }
        
        if (izero >= ndim) {
            tw[0][0] = board[1];
            tw[0][1] = board[0];
        } else {
            tw[1][0] = board[ndim+1];
            tw[1][1] = board[ndim];  
        }
        return new Board(tw);
    }

    public boolean equals(Object y) {       // does this board equal y?
        
        if (y == null || y.getClass() != this.getClass()) { 
            return false;
        } else {
            String boardString = this.toString();
            return boardString.equals(y.toString());          
        }

    }

    public Iterable<Board> neighbors() {    // all neighboring boards
        return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {
                    private int cnt = 0;
                    private int tnn = nn;
                    public boolean hasNext() { return tnn > 0; }
                    public Board next() {
                        if (tnn < 1) {
                         throw new java.util.NoSuchElementException(); }
      
                        int[][] nboard = new int[ndim][ndim];
                        for (int i = 0; i < ndim; i++) {
                            for (int j = 0; j < ndim; j++) {                        
                                nboard[i][j] = board[i*ndim+j];
                            }
                        }
                       
                        tnn--;

                        int nx = arr[cnt];                        
                        while (nx < 0 && cnt < 4) {
                            cnt++;
                            nx = arr[cnt];
                        }
                        cnt++;
                        
                        int ir = nx/ndim;
                        int ic = nx - ir*ndim;

                        int ix = izero/ndim;
                        int iy = izero - ix*ndim;

                        nboard[ir][ic] = board[izero];
                        nboard[ix][iy] = board[nx];                        
                        return new Board(nboard);
                    }
                    public void remove() {
                        throw new 
                    java.lang.UnsupportedOperationException();
                    }
                };
            }
        };
    }
    // string representation of this board (in the output format specified below)
    public String toString() {  
             
        String s = String.format("%d", ndim);

        for (int i = 0; i < ndim; i++) {
            s = s + "\n";
            for (int j = 0; j < ndim; j++) {
                int tmp = board[i*ndim+j]; 
                if (j == 0) {
                    s = s + String.format("%2d", tmp);
                } else {
                    s = s + String.format("%3d", tmp);
                }
            }
        }
        return s;
    }


    public static void main(String[] args) { // unit tests (not graded)
        
        String filename = "puzzle00.txt";
        In in = new In(filename);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
                //StdOut.println(tiles[i][j]);
            }
        }
    }
}

