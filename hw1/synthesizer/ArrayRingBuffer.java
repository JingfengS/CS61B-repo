package synthesizer;
// TODO: Make sure to make this class a part of the synthesizer package
// package <package name>;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        this.capacity = capacity;
        this.fillCount = 0;
    }

    private int getNext(int index) {
        if (index == capacity - 1) {
            return 0;
        }
        return index + 1;
    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (this.isFull()) {
            throw new RuntimeException("The buffer is full!");
        }
        fillCount += 1;
        rb[last] = x;
        last = getNext(last);
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (this.isEmpty()) {
            throw new RuntimeException("The buffer is empty!");
        }
        fillCount -= 1;
        T valueReturn = rb[first];
        rb[first] = null;
        first = getNext(first);
        return valueReturn;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
}
