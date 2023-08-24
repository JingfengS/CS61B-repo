import java.util.Formatter;

/**
 * A naked recursive list of integers, similar to what we saw in lecture 3, but
 * with a large number of additional methods.
 *
 * @author P. N. Hilfinger, with some modifications by Josh Hug and melaniecebula
 * [Do not modify this file.]
 */
public class IntList {
    /**
     * First element of list.
     */
    public int first;
    /**
     * Remaining elements of list.
     */
    public IntList rest;

    /**
     * A List with first FIRST0 and rest REST0.
     */
    public IntList(int first0, IntList rest0) {
        first = first0;
        rest = rest0;
    }

    /**
     * A List with null rest, and first = 0.
     */
    public IntList() {
        /* NOTE: public IntList () { }  would also work. */
        this(0, null);
    }

    /**
     * Returns a list equal to L with all elements squared. Destructive.
     */
    public static void dSquareList(IntList L) {

        while (L != null) {
            L.first = L.first * L.first;
            L = L.rest;
        }
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     */
    public static IntList squareListIterative(IntList L) {
        if (L == null) {
            return null;
        }
        IntList res = new IntList(L.first * L.first, null);
        IntList ptr = res;
        L = L.rest;
        while (L != null) {
            ptr.rest = new IntList(L.first * L.first, null);
            L = L.rest;
            ptr = ptr.rest;
        }
        return res;
    }

    /**
     * Returns a list equal to L with all elements squared. Non-destructive.
     */
    public static IntList squareListRecursive(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.first * L.first, squareListRecursive(L.rest));
    }

    /** DO NOT MODIFY ANYTHING ABOVE THIS LINE! */


    /**
     * Returns a list consisting of the elements of A followed by the
     * *  elements of B.  May modify items of A. Don't use 'new'.
     */

    public static IntList dcatenate(IntList A, IntList B) {
        if (A == null) {
            A = B;
            return A;
        } else if (A.rest == null) {
            A.rest = B;
            return A;
        } else {
            return new IntList(A.first, dcatenate(A.rest, B));
        }
    }

    /**
     * Returns a list consisting of the elements of A followed by the
     * * elements of B.  May NOT modify items of A.  Use 'new'.
     */
    public static IntList catenate(IntList A, IntList B) {
        if (A == null) {
            return B;
        } else {
            return new IntList(A.first, catenate(A.rest, B));
        }
    }

    public IntList getLastNode() {
        if (this.rest == null) {
            return this;
        }
        return this.rest.getLastNode();
    }

    public static IntList reverseNondestructive(IntList L) {
        int firstValue = L.first;
        IntList p = new IntList(firstValue, null);
        L = L.rest;
        if (L == null) {
            return p;
        }
        IntList temp = reverseNondestructive(L);
        temp.getLastNode().rest = p;
        return temp;
    }

    public IntList getLastSecondNode() {
        if (this.rest == null) {
            throw new IllegalArgumentException("The List should be at least 2 node long!");
        }
        if (this.rest.rest == null) {
            return this;
        }
        return this.rest.getLastSecondNode();
    }


    public static IntList reverseDestructive(IntList L) {
        if (L.rest == null) {
            return L;
        }
        IntList value = L.getLastNode();
        IntList Node = L.getLastSecondNode();
        Node.rest = null;
        value.rest = Node;
        IntList.reverseDestructive(L);
        return value;
    }

    public static void evenOdd(IntList lst) {
        if (lst.rest == null || lst.rest.rest == null) {
            return;
        }
        IntList secondNode = lst.rest;
        IntList oddNode = lst.rest;
        while (lst.rest != null && oddNode.rest != null) {
            lst.rest = lst.rest.rest;
            oddNode.rest = oddNode.rest.rest;
            lst = lst.rest;
            oddNode = oddNode.rest;
        }
        lst.rest = secondNode;
    }

    public void skippify() {
        IntList p = this;
        int n = 1;
        while (p != null) {
            IntList next = p;
            for (int i = 0; i <= n; i++) {
                if (p == null) {
                    break;
                }
                p = p.rest;
            }
            next.rest = p;
            n++;
        }
    }


    public static IntList shiftListDestructive(IntList L) {
        if (L.rest == null) {
            return L;
        }
        IntList lastNode = L.getLastNode();
        IntList valueReturn = L.rest;
        L.rest = null;
        lastNode.rest = L;
        return valueReturn;
    }


    /**
     * DO NOT MODIFY ANYTHING BELOW THIS LINE! Many of the concepts below here
     * will be introduced later in the course or feature some form of advanced
     * trickery which we implemented to help make your life a little easier for
     * the lab.
     */

    @Override
    public int hashCode() {
        return first;
    }

    /**
     * Returns a new IntList containing the ints in ARGS. You are not
     * expected to read or understand this method.
     */
    public static IntList of(Integer... args) {
        IntList result, p;

        if (args.length > 0) {
            result = new IntList(args[0], null);
        } else {
            return null;
        }

        int k;
        for (k = 1, p = result; k < args.length; k += 1, p = p.rest) {
            p.rest = new IntList(args[k], null);
        }
        return result;
    }

    /**
     * Returns true iff X is an IntList containing the same sequence of ints
     * as THIS. Cannot handle IntLists with cycles. You are not expected to
     * read or understand this method.
     */
    public boolean equals(Object x) {
        if (!(x instanceof IntList)) {
            return false;
        }
        IntList L = (IntList) x;
        IntList p;

        for (p = this; p != null && L != null; p = p.rest, L = L.rest) {
            if (p.first != L.first) {
                return false;
            }
        }
        if (p != null || L != null) {
            return false;
        }
        return true;
    }

    /**
     * If a cycle exists in the IntList, this method
     * returns an integer equal to the item number of the location where the
     * cycle is detected.
     * <p>
     * If there is no cycle, the number 0 is returned instead. This is a
     * utility method for lab2. You are not expected to read, understand, or
     * even use this method. The point of this method is so that if you convert
     * an IntList into a String and that IntList has a loop, your computer
     * doesn't get stuck in an infinite loop.
     */

    private int detectCycles(IntList A) {
        IntList tortoise = A;
        IntList hare = A;

        if (A == null) {
            return 0;
        }

        int cnt = 0;


        while (true) {
            cnt++;
            if (hare.rest != null) {
                hare = hare.rest.rest;
            } else {
                return 0;
            }

            tortoise = tortoise.rest;

            if (tortoise == null || hare == null) {
                return 0;
            }

            if (hare == tortoise) {
                return cnt;
            }
        }
    }

    @Override
    /** Outputs the IntList as a String. You are not expected to read
     * or understand this method. */
    public String toString() {
        Formatter out = new Formatter();
        String sep;
        sep = "(";
        int cycleLocation = detectCycles(this);
        int cnt = 0;

        for (IntList p = this; p != null; p = p.rest) {
            out.format("%s%d", sep, p.first);
            sep = ", ";

            cnt++;
            if ((cnt > cycleLocation) && (cycleLocation > 0)) {
                out.format("... (cycle exists) ...");
                break;
            }
        }
        out.format(")");
        return out.toString();
    }

    /**
     * Returns the reverse of the given IntList.
     * This method is destructive. If given null
     * as an input, returns null.
     */
    public static IntList reverse(IntList lst) {
        if (lst == null || lst.rest == null) {
            return lst;
        }
        IntList p = IntList.reverse(lst.rest);
        lst.rest.rest = lst;
        lst.rest = null;
        return p;
    }

}

