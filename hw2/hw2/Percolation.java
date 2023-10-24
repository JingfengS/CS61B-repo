package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private class Site {
        private class Status {
            boolean open;
            boolean full;

            public Status() {
                open = false;
                full = false;
            }
        }

        private int row, col;

        int label;
        private Status status;

        public Site(int n) {
            row = nToRow(n);
            col = nToCol(n);
            label = n;
            status = new Status();
        }
    }

    private int length;
    private WeightedQuickUnionUF labelsOfEachSite;
    private Site[] sites;
    private int numOfSitesOpening;

    private Site virtualTop;
    private Site virtualBottom;

    /**
     * create N-by-N grid, with all sites initially blocked
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be greater than 0!");
        }
        length = N;
        labelsOfEachSite = new WeightedQuickUnionUF(N * N + 2);
        sites = new Site[N * N + 2];
        numOfSitesOpening = 0;
        for (int i = 0; i < N * N; i += 1) {
            sites[i] = new Site(i);
        }
        virtualTop = new Site(N * N);
        virtualBottom = new Site(N * N + 1);
        virtualTop.status.open = true;
        virtualTop.status.full = true;
        virtualBottom.status.open = true;
        sites[N * N] = virtualTop;
        sites[N * N + 1] = virtualBottom;
    }

    private int nToRow(int n) {
        return n / length;
    }

    private int nToCol(int n) {
        return n % length;
    }

    private int rowAndColToN(int row, int col) {
        return row * length + col;
    }

    private Site siteInPlace(int row, int col) {
        return sites[rowAndColToN(row, col)];
    }


    /**
     * @param row
     * @param col
     * @return the root of the Site in (row, col)
     */
    private Site findRoot(int row, int col) {
        int n = labelsOfEachSite.find(rowAndColToN(row, col));
        return sites[n];
    }

    private Site.Status findStatus(int row, int col) {
        return findRoot(row, col).status;
    }

    /**
     * if one of s1 and s2 is Full, then both s1 and s2 will be Full
     *
     * @param s1
     * @param s2
     */
    private void union(Site s1, Site s2) {
        if (labelsOfEachSite.connected(rowAndColToN(s1.row, s1.col), rowAndColToN(s2.row, s2.col))) {
            return;
        }
        Site.Status s1Status = findStatus(s1.row, s1.col);
        Site.Status s2Status = findStatus(s2.row, s2.col);
        labelsOfEachSite.union(rowAndColToN(s1.row, s1.col), rowAndColToN(s2.row, s2.col));
        if (s1Status.full || s2Status.full) {
            setFull(s1);
        }
    }

    private boolean hasLeft(Site s) {
        return s.col > 0 && findStatus(s.row, s.col - 1).open;
    }

    private boolean hasRight(Site s) {
        return s.col < length - 1 && findStatus(s.row, s.col + 1).open;
    }

    private boolean hasDown(Site s) {
        return s.row < length - 1 && findStatus(s.row + 1, s.col).open;
    }

    private boolean hasUp(Site s) {
        return s.row > 0 && findStatus(s.row - 1, s.col).open;
    }

    private void setOpen(Site s) {
        findStatus(s.row, s.col).open = true;
        numOfSitesOpening += 1;
    }

    private void setFull(Site s) {
        findStatus(s.row, s.col).full = true;
    }


    /**
     * open the site (row, col) if it is not open already
     * @param row row number of the sites to be opened
     * @param col column number of the sites to be opened
     */
    public void open(int row, int col) {
        if (row >= length || row < 0 ||
                col >= length || col < 0) {
            throw new IndexOutOfBoundsException("Index out of bound!");
        }
        Site siteToOpen = siteInPlace(row, col);
        if (findStatus(siteToOpen.row, siteToOpen.col).open) {
            return;
        }
        setOpen(siteToOpen);
        if (siteToOpen.row == 0) {
            union(siteToOpen, virtualTop);
        } else if (siteToOpen.row == length - 1) {
            union(siteToOpen, virtualBottom);
        }
        if (hasDown(siteToOpen)) {
            Site siteDown = siteInPlace(row + 1, col);
            union(siteToOpen, siteDown);
        }
        if (hasLeft(siteToOpen)) {
            Site siteLeft = siteInPlace(row, col - 1);
            union(siteToOpen, siteLeft);
        }
        if (hasUp(siteToOpen)) {
            Site siteUp = siteInPlace(row - 1, col);
            union(siteToOpen, siteUp);
        }
        if (hasRight(siteToOpen)) {
            Site siteRight = siteInPlace(row, col + 1);
            union(siteToOpen, siteRight);
        }
    }

    /**
     * is the site (row, col) open?
     * @param row
     * @param col
     */
    public boolean isOpen(int row, int col) {
        return findStatus(row, col).open;
    }

    /**
     * is the site (row, col) full?
     * @param row
     * @param col
     */
    public boolean isFull(int row, int col) {
        return findStatus(row, col).full;
    }

    /**
     * @return number of the open sites
     */
    public int numberOfOpenSites() {
        return numOfSitesOpening;
    }

    /**
     * dose the system percolate?
     */
    public boolean percolates() {
        return labelsOfEachSite.connected(length * length, length * length + 1);
    }


}
