import java.util.Iterator;

public class Solver {

    private int move = 0;
    private MinPQ<BoardPrior> pq = new MinPQ<BoardPrior>();
    private BoardPrior[] orderpath;

    private BoardPrior bp;
    private boolean isSolved = false;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) { 
        if (initial == null) { throw new
                java.lang.NullPointerException(); }

        bp = new BoardPrior(initial, null, move);
        BoardPrior searchnode = bp;
        BoardPrior curnbr = null;        
        BoardPrior gradparent = null;

        pq.insert(searchnode);       

        // solve twin board
        MinPQ<BoardPrior> twpq = new MinPQ<BoardPrior>();
        BoardPrior twsearchnode = new BoardPrior(initial.twin(), null, move);
        BoardPrior twcurnbr = null;        
        BoardPrior twgradparent = null;
        int twmove = 0;
        boolean twinsolved = false;
        int twselect = 0;
        twpq.insert(twsearchnode);
        // end solving twin board

        while (!searchnode.boardprior.isGoal() && !twinsolved) {

            searchnode = pq.delMin();
            
            gradparent = searchnode.parent;
            move = searchnode.mvp;
            move++;

            for (Board b : searchnode.boardprior.neighbors()) {
                if (gradparent == null
                     || !b.equals(gradparent.boardprior)) {               
                    curnbr = new BoardPrior(b, searchnode, move);
                    pq.insert(curnbr);
                } 
            }
            

            if (twselect % 4 == 0) {
                // solve for the twin board
                twsearchnode = twpq.delMin();
                twgradparent = twsearchnode.parent;
                twmove = twsearchnode.mvp;
                twmove++;    
                for (Board b : twsearchnode.boardprior.neighbors()) {
                    if (twgradparent == null 
                        || !b.equals(twgradparent.boardprior)) {               
                        twcurnbr = 
                            new BoardPrior(b, twsearchnode, twmove);
                        twpq.insert(twcurnbr);
                    } 
                }               
                twinsolved = twsearchnode.boardprior.isGoal();
                // end for solving twin board
            }
            twselect++;
        }
        
        if (!twinsolved) {
            if (move > 0) move--;
            isSolved = true;
            
            orderpath  = new BoardPrior[move+1];
            BoardPrior trace = searchnode;
            int count = move;
            while (trace != null && count >= 0) {
                orderpath[count] = trace;
                trace = trace.parent;
                count--;
            }
        } else { 
            isSolved = false; 
        }
    }
    
    private class BoardPrior implements Comparable<BoardPrior> {
        private Board boardprior;
        private BoardPrior parent;
        private int prior;
        private int mvp;
        private int man;
        private int ham;


        private BoardPrior(Board bd, BoardPrior pd, int mv) {
            parent = pd;
            boardprior = bd;
            mvp = mv;
            man = boardprior.manhattan();
            ham = boardprior.hamming();
            prior = mv + man;
          
        }

        public int compareTo(BoardPrior thatbd) {
            int thisPrior = prior;
            int thatPrior = thatbd.prior;

            if (thisPrior > thatPrior) {
                return 1;
            } else {
                if (thisPrior < thatPrior) {
                    return -1;
                } else { 
                    if (man > thatbd.man) {
                        return 1;
                    } else {
                        if (man < thatbd.man) {
                            return -1;
                        } else {
                            if (ham > thatbd.ham) {
                                return 1;
                            } else {
                                if (ham < thatbd.ham) {
                                    return -1;
                                } else { 
                                    if (mvp > thatbd.mvp) {
                                        return 1;
                                    } else {
                                        return -1;
                                    }
                                }
                            }
                        }                      
                    }
                }
            }
        }          
    }


    public boolean isSolvable() { // is the initial board solvable?
        return isSolved;
    }
        
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() { 
        if (!isSolved) {
            return -1;
        } else { return move; }
    }
        
    // sequence of boards in a shortest solution; null if unsolvable   
    public Iterable<Board> solution() {
        if (!isSolved) {
            return null; 
        } else {
            return new Iterable<Board>() {
                public Iterator<Board> iterator() {
                    return new Iterator<Board>() {
                        private int count = 0;                      
                        public boolean hasNext() {
                            return count <= move;
                        }
                        public Board next() {
                            BoardPrior sn = orderpath[count];
                            count++;
                            return sn.boardprior;
                        }
                        public void remove() {
                            throw new 
                         java.lang.UnsupportedOperationException();
                        }
                    };
                }
            };
        }
    }     
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        // solve the slider puzzle
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves() + "\n");
            for (Board board : solver.solution()) {     
                StdOut.println(board.toString());
                StdOut.println();
            }
        }
        
    }
}
