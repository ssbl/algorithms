import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] array;
    private int[] order;

    public RandomizedQueue() {
        size = 0;
        array = (Item[]) new Object[1];
        order = new int[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (size == array.length) resize(array.length * 2);
        array[order[size++]] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        if (size > 0 && size == array.length/4) resize(array.length/2);
        return array[order[--size]];
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return array[order[size - 1]];
    }

    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];
        int[] newOrder = new int[capacity];

        for (int i = 0; i < capacity; i++)
            newOrder[i] = i;
        StdRandom.shuffle(newOrder);

        for (int i = 0; i < size; i++)
            newArray[newOrder[i]] = array[order[i]];

        array = newArray;
        order = newOrder;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current;
        private int[] newOrder;

        public RandomizedQueueIterator() {
            current = 0;
            newOrder = new int[size];

            for (int i = 0; i < size; i++)
                newOrder[i] = order[i];
            StdRandom.shuffle(newOrder);
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
            if (isEmpty()) throw new NoSuchElementException();
            return array[newOrder[current++]];
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
