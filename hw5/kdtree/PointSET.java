import java.util.TreeSet;
import java.util.Iterator;
import java.util.LinkedList;

public class PointSET {

    private TreeSet<Point2D> tree = new TreeSet<Point2D>();

    private RectHV rt = new RectHV(0.0, 0.0, 1.0, 1.0);

    public PointSET() {  // construct an empty set of points
    }
    public boolean isEmpty() { // is the set empty?
        return tree.size() == 0;
    }

    public int size() { // number of points in the set
        return tree.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) { 
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        
        if (!rt.contains(p)) {
            throw new IllegalArgumentException();
        } else {
            if (!tree.contains(p)) { tree.add(p); }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) { 
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        return tree.contains(p);
    }

    public void draw() { // draw all points to standard draw
        Iterator it = tree.iterator();
        while (it.hasNext()) {
            Point2D pt = (Point2D) it.next();
            pt.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) { 
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }

        final LinkedList<Point2D> subll = new LinkedList<Point2D>();
        
        Iterator it = tree.iterator(); 
        while (it.hasNext()) {
            Point2D pt = (Point2D) it.next();
            if (rect.contains(pt)) {
                subll.add(pt);
            }
        }   


        return new Iterable<Point2D>() {
            public Iterator<Point2D> iterator() {
             
                return new Iterator<Point2D>() {
                    private int count = subll.size();
                    public boolean hasNext() { return count > 0; }
                    public Point2D next() {
                        count--;
                        return subll.poll();
                    }
                    public void remove() {
                        throw new 
                    java.lang.UnsupportedOperationException();
                    }
                };
            }
        };
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) { 
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        double dist = 0;
        Point2D np = null;
        boolean first = true;

        Iterator it = tree.iterator(); 
        while (it.hasNext()) {
            Point2D pt = (Point2D) it.next();
            if (first) { 
                dist = pt.distanceTo(p);
                np = pt;
                first = false;
            }
            if (pt.distanceTo(p) < dist) {
                dist = pt.distanceTo(p);
                np = pt;
            }
        }   
        return np;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
