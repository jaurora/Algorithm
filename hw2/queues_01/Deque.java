import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    private int N;
    private Node first = null;
    private Node last = null;
    
    private class Node
    {
        private Item item;
        private Node next;
        private Node pre;
    }
    
    public Deque() // construct an empty deque
    { N = 0; }
        
    public boolean isEmpty() // is the deque empty?
    { return N == 0; }
    
    public int size() // return the number of items on the deque
    { return N; }
    
    public void addFirst(Item item) // insert the item at the front
    { 
        if (item == null) throw new 
        java.lang.NullPointerException("A null item is not allowed!");

        if (N == 0) {
            first = new Node();
            first.item = item;
            first.pre = null;
            first.next = null;
            last = first;
        } else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            first.pre = null;
            oldfirst.pre = first;
        }
        N++;
    }
        
    public void addLast(Item item) // insert the item at the end
    {
        if (item == null) throw new 
        java.lang.NullPointerException("A null item is not allowed!");
        
        if (N == 0) {
            last = new Node();
            last.item = item;
            last.pre = null;
            last.next = null;
            first = last;
        } else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.pre = oldlast;
            last.next = null;
            oldlast.next = last;
        }
        N++;
    }

    public Item removeFirst() // delete and return the item at the front
    {
        if (N == 0) throw new 
        java.util.NoSuchElementException("Deque is empty!");

        Item item = first.item;

        if (N == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.pre = null;
        }
        N--;
        return item;
    }

    public Item removeLast() // delete and return the item at the end
    {
        if (N == 0) throw new 
        java.util.NoSuchElementException("Deque is empty!");

        Item item = last.item;
        if (N == 1) {
            last = null;
            first = null;
        } else {
            last = last.pre;
            last.next = null;
        }
        N--;
        return item;
    }

    public Iterator<Item> iterator() 
    // return an iterator over items in order from front to end
    { return new OrderIterator(); }

    private class OrderIterator implements Iterator<Item>
    {
        private int i = N;
        private Node ptr = first;
        public boolean hasNext() { 
            return i >= 1; 
        }
        public Item next() {
            if (i <= 0) throw new
            java.util.NoSuchElementException("No more item to iterate!");
            Item tmp = ptr.item;
            ptr = ptr.next;
            i--;
            return tmp;
        }
        public void remove() 
        { throw new 
        java.lang.UnsupportedOperationException("Iterator remove is not allowed!"); }
   
    }

    public static void main(String[] args) // unit testing
    {
        Deque<String> s = new Deque<String>();
         String x = "hello";
         s.addFirst(x);
         StdOut.println(s.size());
         s.addLast(" world");
         s.addLast(" !");   
         
         String y = s.removeLast();
         StdOut.println(y);         
         StdOut.println(s.size());

         y = s.removeLast();
         StdOut.println(y);         
         StdOut.println(s.size());

         y = s.removeLast();
         StdOut.println(y);         
         StdOut.println(s.size());

         s.addFirst(" world");
         s.addFirst(" hello");
         s.addLast(" !"); 

         s.addLast(null); 

         Iterator it = s.iterator();
         //         it.remove();
         while (it.hasNext()) { StdOut.println(it.next()); }
    }

    
}
