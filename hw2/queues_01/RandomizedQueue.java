import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private int N;
    private Item[] ir;
    
    public RandomizedQueue() // construct an empty randomized queue
        { N = 0; }

    public boolean isEmpty() // is the queue empty?
    { return N == 0; }

    public int size() // return the number of items on the queue
    { return N; }

    public void enqueue(Item item) // add the item
    {
        if (item == null) throw new 
        java.lang.NullPointerException("A null item is not allowed!");

        if (N == 0) {
            ir = (Item[]) new Object[1];
            ir[N] = item;
        } else {
            resize(N+1);
            ir[N] = item;
        }
        N++;
    }


    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) 
            { copy[i] = ir[i]; }
        ir = copy;
    }


        
    public Item dequeue() // delete and return a random item 
    {
        if (N == 0) throw new 
        java.util.NoSuchElementException("Queue is empty!");
        
        int pick = StdRandom.uniform(N);
        Item det = ir[pick];
        
        if (N > 1) {
            Item[] copy = (Item[]) new Object[N-1];
            for (int i = 0; i < pick; i++) 
                { copy[i] = ir[i]; }
            for (int i = pick + 1; i < N; i++)
                { copy[i-1] = ir[i]; }
            ir = copy;
        }

        N--;
        return det;

    }

    public Item sample() // return (but do not delete) a random item
    {
        if (N == 0) throw new 
        java.util.NoSuchElementException("Queue is empty!");
        int pick = StdRandom.uniform(N);
        return ir[pick];
    }
        

    public Iterator<Item> iterator() 
    // return an independent iterator over items in random order
    { return new RandomIterator(); }


    private class RandomIterator implements Iterator<Item>
    {
        private int is = N;
        private Item[] itir = (Item[]) new Object[is];

        public boolean hasNext() { return is >= 1; }
        public Item next() {
            if (is <= 0) throw new
            java.util.NoSuchElementException("No more item to iterate!");
            
            if (is == N) { itir = ir; }
            int pick = StdRandom.uniform(is);
            Item tmp = itir[pick];
            Item[] copy = (Item[]) new Object[is-1];
            for (int i = 0; i < pick; i++) 
                { copy[i] = itir[i]; }
            for (int i = pick+1; i < is; i++) 
                { copy[i-1] = itir[i]; } 
            is--;
            itir = copy;
            return tmp;
        }
    
        public void remove() 
        { throw new 
        java.lang.UnsupportedOperationException("Iterator remove is not allowed!"); }
        
    }


    public static void main(String[] args) // unit testing
    {
        RandomizedQueue<Integer> p = new RandomizedQueue<Integer>();
        StdOut.println(p.isEmpty());
        p.enqueue(3);
        p.enqueue(5);
        //     StdOut.println(p.sample());
        p.enqueue(10);
        // StdOut.println(p.dequeue());
        p.enqueue(1);
        p.enqueue(8);
        p.enqueue(9);
        p.enqueue(15);
        StdOut.println(p.size());
         Iterator it = p.iterator();
         //         it.remove();
         while (it.hasNext()) { StdOut.println(it.next()); }

    }


}
