public class ArrayDeque<T> {
    private T[] items = (T[]) new Object[8];
    private int size;
    private int nextLast;
    private int nextFirst;

    public ArrayDeque() {
        int size = 0;
        int nextLast = 0;
        int nextFirst = items.length - 1;
    }

    private int getNextFirst(int first, T[] items) {
        if (first == 0) {
            return items.length - 1;
        } else {
            return first - 1;
        }
    }

    private int getNextLast(int last, T[] items) {
        if (last == items.length - 1) {
            return 0;
        } else {
            return last + 1;
        }
    }

    private void copyToArray(T[] target) {
        int p = nextFirst;
        for (int i = 0; i < size; i++) {
            p = getNextLast(p, items);
            target[i] = items[p];
        }
    }

    private void resize() {
        if (items.length == size) {
            T[] newItems = (T[]) new Object[size * 2];
            copyToArray(newItems);
            items = newItems;
            nextFirst = getNextFirst(0, items);
            nextLast = getNextLast(size - 1, items);
        } else if ((double) size / items.length < 0.25 && items.length > 8) {
            T[] newItems = (T[]) new Object[items.length / 2];
            copyToArray(newItems);
            items = newItems;
            nextFirst = getNextFirst(0, items);
            nextLast = getNextLast(size - 1, items);
        }
    }

    public int size() {
        return size;
    }

    public void addLast(T x) {
        size += 1;
        items[nextLast] = x;
        nextLast = getNextLast(nextLast, items);
        this.resize();
    }

    public void addFirst(T x) {
        size += 1;
        items[nextFirst] = x;
        nextFirst = getNextFirst(nextFirst, items);
        this.resize();
    }

    public void removeLast() {
        size -= 1;
        if (size < 0) {
            throw new IllegalArgumentException("The Array is already empty!");
        }
        nextLast = getNextFirst(nextLast, items);
        items[nextLast] = null;
        this.resize();
    }

    public void removeFirst() {
        size -= 1;
        if (size < 0) {
            throw new IllegalArgumentException("The Array is already empty!");
        }
        nextFirst = getNextLast(nextFirst, items);
        items[nextFirst] = null;
        this.resize();
    }

    private int getIndex(int i) {
        int index = i + nextFirst + 1;
        if (index >= items.length) {
            index -= items.length;
        }
        return index;
    }

    public T get(int index) {
        return items[getIndex(index)];
    }

}
