import java.util.Iterator;
import java.util.Arrays;

public class Board {

    private int ndim = 0;
    private int[][] board;
    private int[][] arr = new int[4][2];
    private int nn = 0;
    
    private int ix = 0;
    private int iy = 0;

    public Board(int[][] blocks) {           // construct a board from an N-by-N array of blocks
        if (blocks == null) throw new
         java.lang.NullPointerException("Null is not allowed!");
        ndim = blocks.length;
        board = new int[ndim][ndim];

        for (int i = 0; i < ndim; i++) {
             for (int j= 0; j < ndim; j++) {
                 board[i][j] = blocks[i][j];
             }
        }

        for (int i = 0; i < ndim; i++) {
            for (int j = 0; j < ndim; j++) {
                if (board[i][j] == 0) {
                    ix = i;
                    iy = j;
                }
            }
        }

        //StdOut.println(board[ix][iy]);
        

        if (ix > 0) {
            arr[0][0] = ix - 1;
            arr[0][1] = iy;
            nn++;
        } else {
            arr[0][0] = -1;
            arr[0][1] = -1;
        }
                
        if (ix < ndim-1) {
            arr[1][0] = ix + 1;
            arr[1][1] = iy;
            nn++;
        } else {
            arr[1][0] = -1;
            arr[1][1] = -1;
        }
        
        if (iy > 0) {
            arr[2][0] = ix;
            arr[2][1] = iy - 1;
            nn++;
        } else {
            arr[2][0] = -1;
            arr[2][1] = -1;
        }     

        if (iy < ndim-1) {
            arr[3][0] = ix;
            arr[3][1] = iy + 1;
            nn++;
        } else {
            arr[3][0] = -1;
            arr[3][1] = -1;
        }         
       
    }

    
    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {                 // board dimension N
        return ndim;
     }

    public int hamming() {                  // number of blocks out of place
        int count = 0;
        for (int i = 0; i < ndim; i++) {
            for (int j = 0; j < ndim; j++) {
                if (board[i][j] != ndim*i + j +1 && board[i][j] != 0) count++;
            }
        }
        return count;
    }

    public int manhattan() {                 // sum of Manhattan distances between blocks and goal
        int dist = 0;
        for (int i = 0; i < ndim; i++) {
            for (int j = 0; j < ndim; j++) {
                if (board[i][j] != ndim*i + j +1 && board[i][j] != 0) {
                    int irow = (int) Math.ceil((float) board[i][j] / ndim) - 1;
                    int icol = board[i][j] - irow*ndim -1;
                    dist = dist + Math.abs(i - irow) + Math.abs(j - icol);
                }
            }
        }
        return dist;       
    }

    public boolean isGoal() {               // is this board the goal board?
        boolean goal = true;
        for (int i = 0; i < ndim; i++) {
            for (int j = 0; j < ndim; j++) {
                if (board[i][j] != ndim*i + j +1 && board[i][j] != 0) {
                    goal = false;
                    break;
                }
            }
        }
        return goal;  
    }

    public Board twin() {                   // a boadr that is obtained by exchanging two adjacent blocks in the same row
        int[][] tw = new int[ndim][ndim]; 
        for (int i = 0; i < ndim; i++) {
            for (int j = 0; j < ndim; j++) {                        
                tw[i][j] = board[i][j];
            }
        }

        if (ix != 0) {
            tw[0][0] = board[0][1];
            tw[0][1] = board[0][0];
        } else {
            tw[1][0] = board[1][1];
            tw[1][1] = board[1][0];  
        }
        return new Board(tw);
    }

    public boolean equals(Object y) {       // does this board equal y?
        if (y.getClass() != this.getClass()) { 
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
                    private int tnn = nn;
                    private int cnt = 0;

                    public boolean hasNext() { return tnn >= 1; }
                    
                    public Board next() {
                        if (tnn < 1) {
                            throw new
                                java.util.NoSuchElementException("No more item to iterate!");
                        }
                        
                        int[][] nboard = new int[ndim][ndim];
                        for (int i = 0; i < ndim; i++) {
                            for (int j = 0; j < ndim; j++) {                        
                                nboard[i][j] = board[i][j];
                            }
                        }
                        
                        tnn--;

                        int nx = -1;
                        int ny = -1;
                        
                        while(nx < 0 && cnt < 4) {
                            nx = arr[cnt][0];
                            ny = arr[cnt][1];
                            cnt++;
                        }
           
                        nboard[nx][ny] = board[ix][iy];
                        nboard[ix][iy] = board[nx][ny];
                        
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

    public String toString() {               // string representation of this board (in the output format specified below)
        String s = "";
        for (int i = 0; i < ndim; i++) {
            if (i != 0) s = s + "\n";
            for (int j = 0; j < ndim; j++) {
                Integer tmp = board[i][j]; 
                if (j != 0) s = s + " ";
                s = s + tmp.toString();
            }
        }
        return s;
    }


    public static void main(String[] args) { // unit tests (not graded)
        
        String filename="puzzle00.txt";
        In in = new In(filename);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
                //StdOut.println(tiles[i][j]);
            }
        }
        
        //StdOut.println(tiles.length);


        // solve the slider puzzle
        Board il = new Board(tiles);
        
        //StdOut.println(il.dimension());
        //StdOut.println(il.hamming());
        //StdOut.println(il.manhattan());
        //StdOut.println(il.isGoal());

        int[][] y = new int[2][2];
        y[0][0] = 2;
        y[0][1] = 0;
        y[1][0] = 1;
        y[1][1] = 3;
        Board yy = new Board(y);
        //StdOut.println(il.equals(y));

        for (Board b : il.neighbors()) {
            StdOut.println(b.toString());
            StdOut.println("\n");
        }
    }
}

