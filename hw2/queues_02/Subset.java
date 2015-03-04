public class Subset {
    public static void main(String[] args) {
        
        //        Stopwatch stopwatch = new Stopwatch();

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) { rq.enqueue(StdIn.readString()); }

        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }

        //        System.out.println("time used = " + stopwatch.elapsedTime());
    }
}
