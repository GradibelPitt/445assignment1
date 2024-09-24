import java.util.Arrays;


public class ArrayDS<T extends Comparable<? super T>> implements SequenceInterface<T>, ReorderInterface, Comparable<ArrayDS<T>> {


    /**
     * main for debugging
     */
    public static void main(String[] args) {
        ArrayDS<Integer> arrayDS = new ArrayDS<>();
        
        arrayDS.append(1);
        arrayDS.append(2);
        arrayDS.append(3);
        System.out.println("After appending 1, 2, 3: " + arrayDS.toString());

        arrayDS.prefix(0);
        System.out.println("After prefixing 0: " + arrayDS.toString());

        arrayDS.deleteHead();
        System.out.println("After deleting head: " + arrayDS.toString());

        arrayDS.deleteTail();
        System.out.println("After deleting tail: " + arrayDS.toString());

        arrayDS.reverse();
        System.out.println("After reversing: " + arrayDS.toString());

        arrayDS.rotateRight();
        System.out.println("After rotating right: " + arrayDS.toString());

        arrayDS.rotateLeft();
        System.out.println("After rotating left: " + arrayDS.toString());
    }
 
    private T[] sequence;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public ArrayDS() {
        sequence = (T[]) new Comparable[DEFAULT_CAPACITY];
        numberOfEntries = 0;
    }

    /**
     * copy constructor 
     */
    @SuppressWarnings("unchecked")
    public ArrayDS(ArrayDS<T> other) {
        sequence = (T[]) new Comparable[other.sequence.length];
        System.arraycopy(other.sequence, 0, this.sequence, 0, other.numberOfEntries);
        this.numberOfEntries = other.numberOfEntries;
    }

    /**
     * adds an item to the end of the sequence
     * resizes array if full
     */
    @Override
    public void append(T item) {
        if (numberOfEntries == sequence.length) {
            resize();
        }
        sequence[numberOfEntries] = item;
        numberOfEntries++;
    }

    /** 
     * adds an item to the beginning of the sequence
     * resizes array if full
     */
    @Override
    public void prefix(T item) {
        if (numberOfEntries == sequence.length) {
            resize();
        }
        for (int i = numberOfEntries - 1; i >= 0; i--) {
            sequence[i + 1] = sequence[i];
        }
        sequence[0] = item;
        numberOfEntries++;
    }

    /**
     * inserts an item at specified position
     * moves subsequent items to the right
     */
    @Override
    public void insert(T item, int position) {
        if (position < 0 || position > numberOfEntries) {
            throw new IndexOutOfBoundsException("Invalid position");
        }
        if (numberOfEntries == sequence.length) {
            resize();
        }
        for (int i = numberOfEntries - 1; i >= position; i--) {
            sequence[i + 1] = sequence[i];
        }
        sequence[position] = item;
        numberOfEntries++;
    }

    /**
     * retrives the item at the specified position
     * if invalid, throws exception
     */

    @Override
    public T itemAt(int position) {
        if (position < 0 || position >= numberOfEntries) {
            throw new IndexOutOfBoundsException("Invalid position");
        }
        return sequence[position];
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public int size() {
        return numberOfEntries;
    }

    /**
     * returns the first item in the sequence
     */
    @Override
    public T first() {
        if (isEmpty()) {
            return null;
        }
        return sequence[0];
    }

    /**
     * returns the last item in the sequence
     */
    @Override
    public T last() {
        if (isEmpty()) {
            return null;
        }
        return sequence[numberOfEntries - 1];
    }

    /** 
     * finds the predecesspr of the given item and returns it
     * returns null if the item not found or the item is at the start
     */
    @Override
    public T predecessor(T item) {
        for (int i = 1; i < numberOfEntries; i++) {
            if (sequence[i].compareTo(item) == 0) {
                return sequence[i - 1];
            }
        }
        return null;
    }


    /**
     * returns the frequency of given item in the sequence
     */
    @Override
    public int getFrequencyOf(T item) {
        int count = 0;
        for (int i = 0; i < numberOfEntries; i++) {
            if (sequence[i].compareTo(item) == 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * clear the sequence by resetting numberOfEntries
     */
    @Override
    public void clear() {
        numberOfEntries = 0;
        Arrays.fill(sequence, null);
    }

    /** 
     * returns the index of the last occurrence of the given item
     * returns -1 if item not found
     */
    @Override
    public int lastOccurrenceOf(T item) {
        for (int i = numberOfEntries - 1; i >= 0; i--) {
            if (sequence[i].compareTo(item) == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * deletes and returns the first item in the sequence
     */
    @Override
    public T deleteHead() {
        if (isEmpty()) {
            throw new EmptySequenceException("Sequence is empty");
        }
        T head = sequence[0];
        for (int i = 1; i < numberOfEntries; i++) {
            sequence[i - 1] = sequence[i];
        }
        numberOfEntries--;
        return head;
    }

    /** 
     * deletes and returns the last item in the sequence
     */
    @Override
    public T deleteTail() {
        if (isEmpty()) {
            throw new EmptySequenceException("Sequence is empty");
        }
        T tail = sequence[numberOfEntries - 1];
        numberOfEntries--;
        return tail;
    }

    @Override
    public boolean trim(int numItems) {
        if (numItems > numberOfEntries) {
            return false;
        }
        numberOfEntries -= numItems;
        return true;
    }

    @Override
    public boolean cut(int start, int numItems) {
        if (start < 0 || start + numItems > numberOfEntries) {
            return false;
        }
        for (int i = start + numItems; i < numberOfEntries; i++) {
            sequence[i - numItems] = sequence[i];
        }
        numberOfEntries -= numItems;
        return true;
    }

    @Override
    public void reverse() {
        for (int i = 0; i < numberOfEntries / 2; i++) {
            T temp = sequence[i];
            sequence[i] = sequence[numberOfEntries - 1 - i];
            sequence[numberOfEntries - 1 - i] = temp;
        }
    }

    @Override
    public void rotateRight() {
        if (numberOfEntries > 0) {
            T lastItem = sequence[numberOfEntries - 1];
            for (int i = numberOfEntries - 1; i > 0; i--) {
                sequence[i] = sequence[i - 1];
            }
            sequence[0] = lastItem;
        }
    }

    /**
     * rotates the sequence to the left and moving the first element to the end
     */
    @Override
    public void rotateLeft() {
        if (numberOfEntries > 0) {
            T firstItem = sequence[0];
            for (int i = 0; i < numberOfEntries - 1; i++) {
                sequence[i] = sequence[i + 1];
            }
            sequence[numberOfEntries - 1] = firstItem;
        }
    }

    /**
     * based on old and new positions to shuffle items
     */
    @Override
    public void shuffle(int[] oldPositions, int[] newPositions) {
        if (oldPositions.length != newPositions.length) {
            throw new IllegalArgumentException("The length of oldPositions and newPositions must be the same.");
        }
        T[] temp = (T[]) new Comparable[numberOfEntries];
        for (int i = 0; i < oldPositions.length; i++) {
            if (oldPositions[i] < 0 || oldPositions[i] >= numberOfEntries ||
                newPositions[i] < 0 || newPositions[i] >= numberOfEntries) {
                throw new IndexOutOfBoundsException("Invalid position in shuffle.");
            }
            temp[newPositions[i]] = sequence[oldPositions[i]];
        }
        for (int i = 0; i < numberOfEntries; i++) {
            if (temp[i] == null) {
                throw new IllegalArgumentException("Some positions are not properly shuffled.");
            }
            sequence[i] = temp[i];
        }
    }

    @Override
    public int compareTo(ArrayDS<T> other) {
        int minLength = Math.min(this.numberOfEntries, other.numberOfEntries);
        for (int i = 0; i < minLength; i++) {
            int comparison = this.sequence[i].compareTo(other.sequence[i]);
            if (comparison != 0) {
                return comparison;
            }
        }
        return Integer.compare(this.numberOfEntries, other.numberOfEntries);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numberOfEntries; i++) {
            result.append(sequence[i].toString());
        }
        return result.toString();
    }

    /**
     * doubles the size of the underlying array when it's full
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        sequence = Arrays.copyOf(sequence, sequence.length * 2);
    }

    @Override
    public SequenceInterface<T> slice(int start, int numItems) {
        if (start < 0 || start + numItems > numberOfEntries) {
            return null;
        }
        ArrayDS<T> result = new ArrayDS<>();
        for (int i = start; i < start + numItems; i++) {
            result.append(sequence[i]);
        }
        return result;
    }

    @Override
    public SequenceInterface<T> slice(T item) {
        ArrayDS<T> result = new ArrayDS<>();
        for (int i = 0; i < numberOfEntries; i++) {
            if (sequence[i].compareTo(item) <= 0) {
                result.append(sequence[i]);
            }
        }
        return result;
    }
}

public static class EmptySequenceException extends RuntimeException {
        public EmptySequenceException(String message) {
            super(message);
        }
    }