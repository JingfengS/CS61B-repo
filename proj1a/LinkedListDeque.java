/**
 * Invariants:
 * prev: the node before
 * item: the information stored in the node
 * next: the node after
 * size: the size of the deque
 * lastNode: the last node of the deque
 * sentinel: the first "fake" node of the deque
 *
 * @param <Item>
 */
public class LinkedListDeque<Item> {
    private class ItemNode {
        ItemNode prev;
        Item item;
        ItemNode next;

        private ItemNode(ItemNode pr, Item it, ItemNode ne) {
            prev = pr;
            item = it;
            next = ne;
        }

    }

    private ItemNode sentinel;
    private int size;

    public LinkedListDeque() {
        size = 0;
        sentinel = new ItemNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public LinkedListDeque(Item x) {
        this();
        size = 1;
        sentinel.next = new ItemNode(sentinel, x, sentinel);
        sentinel.prev = sentinel.next;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item t) {
        ItemNode secondNode = sentinel.next;
        ItemNode firstNode = new ItemNode(sentinel, t, secondNode);
        sentinel.next = firstNode;
        secondNode.prev = firstNode;
        size += 1;
    }

    public void addLast(Item t) {
        ItemNode addLastNode = new ItemNode(sentinel.prev, t, sentinel);
        sentinel.prev.next = addLastNode;
        sentinel.prev = addLastNode;
        size += 1;
    }

    public Item removeFirst() {
        if (this.size() == 0) {
            return null;
        }
        Item returnValue = sentinel.next.item;
        ItemNode secondNode = sentinel.next.next;
        sentinel.next = secondNode;
        secondNode.prev = sentinel;
        size -= 1;
        return returnValue;
    }

    public Item removeLast() {
        if (this.size() == 0) {
            return null;
        }
        ItemNode lastNode = sentinel.prev;
        Item returnValue = lastNode.item;
        lastNode.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return returnValue;
    }

    public Item get(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index cannot be negative!");
        }
        ItemNode p = sentinel.next;
        while (index != 0) {
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    public void printDeque() {
        ItemNode p = sentinel.next;
        while (p.item != null) {
            System.out.print(p.item + " ");
            p = p.next;
        }
    }


    private Item getRecursive(int index, ItemNode D) {
        if (index == 0) {
            return D.next.item;
        }
        return getRecursive(index - 1, D.next);
    }

    public Item getRecursive(int index) {
        return getRecursive(index, this.sentinel);
    }
}
