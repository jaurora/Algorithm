/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SLPOrder();
  
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    private class SLPOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
        if (p1 == null || p2 == null) throw new
         java.lang.NullPointerException("Null is not allowed!");
            double slp1 = slopeTo(p1);
            double slp2 = slopeTo(p2);
            if (slp1 > slp2) {
                return 1;
            } else {
                if (slp1 < slp2) {
                    return -1;
                } else { return 0; }
            }
        }
    }

    // YOUR DEFINITION HERE
    
    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (that == null) throw new
         java.lang.NullPointerException("Null is not allowed!");
        
        double inf = Double.POSITIVE_INFINITY;

        if (that.x == this.x) {
            if (that.y != this.y) {
                return inf;
            } else { 
                return -inf; }
        } else {
            if (that.y != this.y) {
                return (double) (that.y - this.y) / (that.x - this.x);
            } else {
                return +0.0;
            }
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (that == null) throw new
         java.lang.NullPointerException("Null is not allowed!");

        if (this.y != that.y) {
            return (this.y - that.y);
        } else {
            return (this.x - that.x);
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p0 = new Point(5, 5);
        
        Point p1 = new Point(5, -1);

        Point p2 = new Point(2, 5);

        p0.draw();
        p0.drawTo(p1);
        p0.drawTo(p2);

        StdOut.println(p0.compareTo(p1));
        StdOut.println(p0.slopeTo(p1));
        
        //Comparator<Point> sl = p0.SLOPE_ORDER;
        //int a = sl.compare(p2, p1);

        //StdOut.println(a);      

    }
}
