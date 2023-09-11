package synthesizer;

public interface BoundedQueue<T> {

    /**
     * @return the capacity of the queue
     */
    public int capacity();

    /**
     * @return the number of items currently in the buffer
     */
    public int fillCount();

    /**
     * @param x add x to the end
     */
    public void enqueue(T x);

    /**
     * delete the front item and return the item from front
     * @return item from the front
     */
    T dequeue();

    /**
     * return (but not delete item) from the front
     * @return
     */
    T peek();

    /**
     * @return true if the buffer is empty
     *         false otherwise
     */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /**
     * @return true if the buffer is full
     *         false otherwise
     */
    default boolean isFull() {
        return fillCount() == capacity();
    }
}
