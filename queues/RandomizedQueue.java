import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    public RandomizedQueue() {

    }

    public boolean isEmpty() {

    }

    public int size() {

    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

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
