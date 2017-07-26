import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] array;

    public RandomizedQueue() {
        size = 0;
        array = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (size > 0 && size == array.length) resize(array.length * 2);
        array[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        if (size > 0 && size == array.length/4) resize(array.length/2);

        int pos = StdRandom.uniform(size);
        Item item = array[pos];

        array[pos] = array[size - 1];
        array[--size] = null;
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        int pos = StdRandom.uniform(size);
        return array[pos];
    }

    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];

        for (int i = 0; i < size; i++)
            newArray[i] = array[i];

        array = newArray;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current;
        private int[] order;

        public RandomizedQueueIterator() {
            current = 0;
            order = new int[size];

            for (int i = 0; i < size; i++)
                order[i] = i;
            StdRandom.shuffle(order);
        }

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return array[order[current++]];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        for (String arg : args) {
            if (arg.equals("-")) rq.dequeue();
            else                 rq.enqueue(arg);
        }

        for (String item : rq)
            StdOut.println(item);
    }
}
