import java.util.Arrays;

public class Fast {

    public static void main(String[] args) {

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        In in  = new In(args[0]); // input file
        int N = in.readInt();
        Point[] pts = new Point[N];
        double minf = Double.NEGATIVE_INFINITY;

        Point[] saveP = new Point[N*N];
        double[] saveS = new double[N*N];
        int ic = 0;

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

        for (int i = 0; i < N; i++) {
            
            Point[] tmp = Arrays.copyOf(pts, N);
            Arrays.sort(tmp, i+1, N, pts[i].SLOPE_ORDER);

            double preslp = minf;            

            for (int j = i+1; j < N-2; j++) {

                int add = 0;
                if (pts[i].slopeTo(tmp[j]) == preslp) { continue; }

                if (pts[i].slopeTo(tmp[j]) != minf
                    && pts[i].slopeTo(tmp[j]) == pts[i].slopeTo(tmp[j + 2])) {
                    add = j + 2 + 1;
                    while (add < N && pts[i].slopeTo(tmp[add]) 
                           == pts[i].slopeTo(tmp[j])) {
                        add++; 
                    }
              
                    tmp[j-1] = tmp[i];
                    Arrays.sort(tmp, j-1, add);
                    

                    int flag = 0;
                    for (int p = 0; p < ic; p++) {
                        if (saveP[p] == tmp[add - 1] 
                        && saveS[p] == pts[i].slopeTo(tmp[j])) { 
                            flag = 1; 
                        }                       
                    }   


                    if (flag == 0) {
                            
                        if (add - j + 1 > 4) {
                            saveP[ic] = tmp[add - 1];
                            saveS[ic] = pts[i].slopeTo(tmp[j]);
                            ic++;
                        }

                        StdOut.print(tmp[j-1].toString());
                        for (int k = j; k < add; k++) {
                            System.out.print(" -> " + tmp[k].toString());
                        }

                        tmp[j-1].drawTo(tmp[add-1]);
                        System.out.print("\n");
                    }

                    preslp = pts[i].slopeTo(tmp[j]);
                    
                }
            }   
        }
    }
}
