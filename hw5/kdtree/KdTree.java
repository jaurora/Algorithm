import java.util.Iterator;
import java.util.LinkedList;

public class KdTree {
    private Node root;
    private RectHV rectHV = new RectHV(0.0, 0.0, 1.0, 1.0);
    private LinkedList<Point2D> inside = new LinkedList<Point2D>();
    private int count = 0;

    public KdTree() { }  // construct an empty set of points        
  
    private static class Node {
        private Point2D p; // the point
        // the axis-aligned rectangle corresponding to this node
        private RectHV rect; 
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree
        private boolean vertical;
        private Node() { }
    }
    
    public boolean isEmpty() { // is the set empty?
        return count == 0;
    }

    public int size() { // number of points in the set
        return count;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) { 

        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        if (!contains(p) && rectHV.contains(p)) {
            
            if (isEmpty()) {
                root = new Node();
                root.p = p;
                root.vertical = false;
                root.lb = null;
                root.rt = null;
                root.rect = rectHV;
            } else {
                Node thisRoot = find(root, p);
                if (thisRoot.vertical) {
                    if (p.y() < thisRoot.p.y()) {
                        Node nd = new Node();
                        nd.vertical = false;
                        nd.p = p;
                        RectHV tmp = 
                            new RectHV(thisRoot.rect.xmin(), thisRoot.rect.ymin(),
                                   thisRoot.rect.xmax(), thisRoot.p.y());
                        nd.rect = tmp;
                        nd.lb = null;
                        nd.rt = null;
                        thisRoot.lb = nd;
                    } else {
                        Node nd = new Node();
                        nd.vertical = false;
                        nd.p = p;
                        RectHV tmp = 
                            new RectHV(thisRoot.rect.xmin(), thisRoot.p.y(),
                                       thisRoot.rect.xmax(), thisRoot.rect.ymax());
                        nd.rect = tmp;
                        nd.lb = null;
                        nd.rt = null;
                    thisRoot.rt = nd;
                    }
                } else {
                    if (p.x() < thisRoot.p.x()) {
                        Node nd = new Node();
                        nd.vertical = true;
                        nd.p = p;
                        RectHV tmp = 
                            new RectHV(thisRoot.rect.xmin(), thisRoot.rect.ymin(),
                                       thisRoot.p.x(), thisRoot.rect.ymax());
                        nd.rect = tmp;
                        nd.lb = null;
                        nd.rt = null;
                        thisRoot.lb = nd;
                    } else {
                        Node nd = new Node();
                        nd.vertical = true; 
                        nd.p = p;
                        RectHV tmp = 
                            new RectHV(thisRoot.p.x(), thisRoot.rect.ymin(),
                                   thisRoot.rect.xmax(), thisRoot.rect.ymax());
                        nd.rect = tmp;
                        nd.lb = null;
                        nd.rt = null;
                        thisRoot.rt = nd;
                    }
                }
            }
            count++;
        }
    }


    private Node find(Node n, Point2D p) {
        if (n.vertical) {
            if (p.y() < n.p.y()) {
                if (n.lb != null) return find(n.lb, p);
                else return n; 
            } else {
                if (n.rt != null) return find(n.rt, p);
                else return n; 
            }         
        } else {
            if (p.x() < n.p.x()) {
                if (n.lb != null) return find(n.lb, p);
                else return n; 
            } else {
                if (n.rt != null) return find(n.rt, p);
                else return n; 
            }      
        }
    }


    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
 
        if (!rectHV.contains(p)) {
            return false;
        } else {
            return get(root, p) != null;
        }
    }

    private Node get(Node n, Point2D p) {

        if (n == null) return null;
        if (n.p.equals(p)) {
            return n;
        } else {
            if (n.vertical) {
                if (p.y() < n.p.y()) return get(n.lb, p);
                else return get(n.rt, p);
            } else {
                if (p.x() < n.p.x()) return get(n.lb, p);
                else return get(n.rt, p);
            }
        }
    }



    public void draw() { // draw all points to standard draw
        draw(root);
    }

    private void draw(Node n) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        n.p.draw();
        StdDraw.setPenRadius();
        if (n.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
        }
        if (n.lb != null) draw(n.lb);
        if (n.rt != null) draw(n.rt);
    }

    // all points that are inside the rectangle        
    public Iterable<Point2D> range(RectHV rect) {

        if (inside.size() != 0) inside.clear();
        
        Node tmp = search(rect, root);
        if (tmp != null) { inside.add(tmp.p); }

        return new Iterable<Point2D>() {
            public Iterator<Point2D> iterator() {
             
                return new Iterator<Point2D>() {
                    private int count = inside.size();
                    public boolean hasNext() { return count > 0; }
                    public Point2D next() {
                        count--;
                        return inside.poll();
                    }
                    public void remove() {
                        throw new 
                    java.lang.UnsupportedOperationException();
                    }
                };
            }
        };
    }


    private Node search(RectHV rect, Node n) {
        if (n == null) {
            return null;
        } else {
            if (n.rect.intersects(rect)) {
                Node tmp = search(rect, n.lb);
                if (tmp != null) { inside.add(tmp.p); }
                
                tmp = search(rect, n.rt);
                if (tmp != null) { inside.add(tmp.p); }
            }

            if (rect.contains(n.p)) { 
                return n;
            } else { return null; }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) { 
        if (p == null) {
            throw new java.lang.NullPointerException();
        }     

        if (size() == 0) {
            return null;
        } else {
            return nearsearch(p, root, root.p);
        }
    }

    private Point2D nearsearch(Point2D p, Node n, Point2D tmp) {
        Point2D close = tmp;
            
        if (n.p.distanceTo(p) < close.distanceTo(p)) {
            close = n.p;
        }

        if (n.rect.distanceTo(p) < close.distanceTo(p)) {
            
            if (n.vertical) {
                if (p.y() < n.p.y()) {
                    if (n.lb != null) { close = nearsearch(p, n.lb, close); }
                    if (n.rt != null) { close = nearsearch(p, n.rt, close); }
                } else {
                    if (n.rt != null) { close = nearsearch(p, n.rt, close); }
                    if (n.lb != null) { close = nearsearch(p, n.lb, close); }
                }
            } else {
                if (p.x() < n.p.x()) {
                    if (n.lb != null) { close = nearsearch(p, n.lb, close); }
                    if (n.rt != null) { close = nearsearch(p, n.rt, close); }
                } else {
                    if (n.rt != null) { close = nearsearch(p, n.rt, close); }
                    if (n.lb != null) { close = nearsearch(p, n.lb, close); }
                }
            }
        }
        return close;       
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}
