import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = 0;

        if (args.length == 1) k = Integer.parseInt(args[0]);
        else                  return;

        if (k == 0) return;

        RandomizedQueue<String> rq = new RandomizedQueue<>();
        try {
            while (true) {
                String s = StdIn.readString();
                rq.enqueue(s);
            }
        }
        catch (NoSuchElementException e) {
            if (rq.isEmpty()) return;
        }

        for (String item : rq) {
            StdOut.println(item);
            if (--k == 0) break;
        }
    }
}
