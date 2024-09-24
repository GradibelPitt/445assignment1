import java.util.Arrays;

public class ArrayDS<T extends Comparable<? super T>> implements SequenceInterface<T>, ReorderInterface, Comparable<ArrayDS<T>> {

    private T[] sequence;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public ArrayDS() {
        sequence = (T[]) new Comparable[DEFAULT_CAPACITY];
        numberOfEntries = 0;
    }

    @SuppressWarnings("unchecked")
    public ArrayDS(ArrayDS<T> other) {
        sequence = (T[]) new Comparable[other.sequence.length];
        System.arraycopy(other.sequence, 0, this.sequence, 0, other.numberOfEntries);
        this.numberOfEntries = other.numberOfEntries;
    }

    @Override
    public void append(T item) {
        if (numberOfEntries == sequence.length) {
            resize();
        }
        sequence[numberOfEntries] = item;
        numberOfEntries++;
    }

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

    @Override
    public T first() {
        if (isEmpty()) {
            return null;
        }
        return sequence[0];
    }

    @Override
    public T last() {
        if (isEmpty()) {
            return null;
        }
        return sequence[numberOfEntries - 1];
    }

    @Override
    public T predecessor(T item) {
        for (int i = 1; i < numberOfEntries; i++) {
            if (sequence[i].compareTo(item) == 0) {
                return sequence[i - 1];
            }
        }
        return null;
    }

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

    @Override
    public void clear() {
        numberOfEntries = 0;
        Arrays.fill(sequence, null);
    }

    @Override
    public int lastOccurrenceOf(T item) {
        for (int i = numberOfEntries - 1; i >= 0; i--) {
            if (sequence[i].compareTo(item) == 0) {
                return i;
            }
        }
        return -1;
    }

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
