import java.util.PriorityQueue;
//import java.util.Queue;
import java.util.Stack;
import java.util.Iterator;
import java.util.Comparator;
import java.lang.Thread;

public class Solver {

    private int move = 0;
    private MinPQ<BoardPrior> pq = new MinPQ<BoardPrior>();
    private Stack<BoardPrior> orderpath = new Stack<BoardPrior>();
    private Stack<BoardPrior> searchtree = new Stack<BoardPrior>();

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

        searchtree.push(searchnode);

        while (!searchnode.boardprior.isGoal()) {

            searchnode = pq.delMin();

            move = searchnode.mvp;
            move++;

            for (Board b : searchnode.boardprior.neighbors()) {
                
                if (gradparent == null || !b.equals(gradparent.boardprior)) {               
                    curnbr = new BoardPrior(b, searchnode, move);
                    pq.insert(curnbr);
                    searchtree.push(curnbr);
                } 
            }
            gradparent = searchnode;
        }
        move--;
        isSolved = true;

        BoardPrior trace = searchnode;
        while (searchnode != null) {
            orderpath.push(searchnode);
            searchnode = searchnode.parent;
        }
        
    }
    
    private class BoardPrior implements Comparable<BoardPrior>{
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


    public boolean isSolvable() {// is the initial board solvable?
        final Board initbd = bp.boardprior;
        final Board twinbd = initbd.twin();
        boolean slv;

        Thread t1 = new Thread() {
                public void run() {
                    Solver initsv = new Solver(initbd);
                }
            };

        Thread t2 = new Thread() {
                public void run() {
                    Solver twinsv = new Solver(twinbd);
                }
            };
        
        t1.start();
        t2.start();
        

        while(t1.isAlive() && t2.isAlive()) {
            continue;
        }
     
        if (!t1.isAlive()) {
            t2.stop();
            slv = true;
            isSolved = true;
        } 

        if (!t2.isAlive()) {
            t1.stop();
            slv = false;
            isSolved = false;
        }   
        return slv;
    }
        
    public int moves() { // min number of moves to solve initial board; -1 if unsolvable
        if (!isSolved) {
            return -1;
        } else { return move; }
    }
        
    // sequence of boards in a shortest solution; null if unsolvable   
    public Iterable<Board> solution() {
        return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {
                    
                    private BoardPrior sn;

                    public boolean hasNext() { 
                        return orderpath.size() > 0;
                    }
                    public Board next() {
                        sn = (BoardPrior) orderpath.pop();
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

    public static void main(String[] args) {// solve a slider puzzle (given below)
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

        StdOut.println(initial);
        StdOut.println(initial.twin());

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {     
                StdOut.println(N);
                StdOut.println(board);
                StdOut.println();
            }
        }
        
    }
}
