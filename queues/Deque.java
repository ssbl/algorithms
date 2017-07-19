import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    public Deque() {

    }

    public boolean isEmpty() {

    }

    public int size() {

    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        public boolean hasNext() {

        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (isEmpty()) throw new NoSuchElementException();
        }
                
    }

    public static void main(String[] args) {

    }
}
