public class ArrayDeque<T> {
    private T[] items = (T[]) new Object[8];
    private int size;
    private int nextLast;
    private int nextFirst;

    public ArrayDeque() {
        size = 0;
        nextLast = 0;
        nextFirst = items.length - 1;
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
        } else if ((double) size / items.length < 0.25 && items.length > 16) {
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

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        nextLast = getNextFirst(nextLast, items);
        T returnValue = items[nextLast];
        items[nextLast] = null;
        this.resize();
        return returnValue;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        nextFirst = getNextLast(nextFirst, items);
        T returnValue = items[nextFirst];
        items[nextFirst] = null;
        this.resize();
        return returnValue;
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

    public void printDeque() {
        int itemIndex = getNextLast(nextFirst, items);
        for (int i = 0; i < size; i += 1) {
            System.out.print(items[itemIndex] + " ");
            itemIndex = getNextLast(itemIndex, items);
        }
        System.out.println();
    }

   public boolean isEmpty() {
        return size == 0;
   }
}
