import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        Node(Item item) {
            this.item = item;
            next = null;
            prev = null;
        }
    }

    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldFirst = first;
        first = new Node(item);
        first.next = oldFirst;

        if (oldFirst != null) oldFirst.prev = first;
        else                  last = first;

        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldLast = last;
        last = new Node(item);
        last.prev = oldLast;

        if (oldLast != null) oldLast.next = last;
        else                 first = last;

        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = first.item;
        first = first.next;

        if (first == null) last = null;
        else               first.prev = null;

        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = last.item;
        last = last.prev;

        if (last == null) first = null;
        else              last.next = null;

        size--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;

            return item;
        }
                
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        for (String arg : args) {
            if (arg.equals("RF"))         deque.removeFirst();
            else if (arg.equals("RL"))    deque.removeLast();
            else if (arg.startsWith("%")) deque.addFirst(arg);
            else                          deque.addLast(arg);
        }

        for (String item : deque)
            StdOut.println(item);
    }
}
