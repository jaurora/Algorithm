import java.util.Arrays;

public class Brute {

    public static void main(String[] args) {

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        In in  = new In(args[0]); // input file
        int N = in.readInt();
        Point[] pts = new Point[N];

        // turn on animation mode
        StdDraw.show();
        
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            pts[i] = p;
            p.draw();
        }

        Arrays.sort(pts);

        for (int p = 0; p < N; p++) {
            for (int q = p + 1; q < N; q++) {
                for (int r = q + 1; r < N; r++) {
                    for (int s = r + 1; s < N; s++) {
                        
                        if (pts[p].slopeTo(pts[q]) == pts[p].slopeTo(pts[r]) 
                            && pts[p].slopeTo(pts[r]) == pts[p].slopeTo(pts[s])) {
                         
                            //System.out.println(pts[p]);
                            //System.out.println(pts[s]);

                            pts[p].drawTo(pts[s]);
                            StdOut.println(pts[p].toString() 
                            + " -> " + pts[q].toString() 
                            + " -> " + pts[r].toString() 
                            + " -> " + pts[s].toString());
                        }
                    }
                }
            }
        }

        // display to screen all at once
        StdDraw.show(0);
        // reset the pen radius
        StdDraw.setPenRadius();
    }
}
