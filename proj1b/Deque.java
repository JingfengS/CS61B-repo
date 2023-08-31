public interface Deque<T> {
    /**
     * Add item to the front of the deque
     * @param item  item to add to front
     */
    public void addFirst(T item);

    /**
     * Add item to the end of the deque
     * @param item  item to add to last
     */
    public void addLast(T item);

    /**
     * Check if the deque is empty
     * @return true if empty
     * false otherwise
     */
    public boolean isEmpty();

    /**
     * return the size of the deque
     * @return size
     */
    public int size();

    /**
     * Print all the items in the deque
     * separated by whitespace
     */
    public void printDeque();

    /**
     * Remove the first item in the deque
     * @return the first item
     */
    public T removeFirst();

    /**
     * Remove the last item in the deque
     * @return the last item
     */
    public T removeLast();

    /**
     * get the ith item in the deque
     * @param index the ith item
     * @return the ith item
     */
    public T get(int index);
}
