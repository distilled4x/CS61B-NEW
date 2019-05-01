import edu.princeton.cs.algs4.Queue;

import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;
import java.util.Random;
import static org.junit.Assert.assertTrue;

public class TestSortAlgs {

    public static Queue<Integer> genIntQueue(int max) {

        Queue<Integer> items = new Queue<>();
        Random r = new Random();
        for (int i = 0; i < max; i++) {
            int x = r.nextInt(max);
            items.enqueue(x);
        }

        return items;
    }

    @Test
    public void testQuickSort() {
        Queue<Integer> quickTest = genIntQueue(30);

        for (int x : quickTest) {
            System.out.print(x + " ");
        }

        System.out.println();

        Queue<Integer> quickSorted = QuickSort.quickSort(quickTest);

        for (int x : quickSorted) {
            System.out.print(x + " ");
        }

        assertTrue(isSorted(quickSorted));

    }

    @Test
    public void testMergeSort() {
        Queue<Integer> mergeTest = genIntQueue(30);
        Queue<Integer> mergeSorted = MergeSort.mergeSort(mergeTest);
        assertTrue(isSorted(mergeSorted));
    }

    @Test
    public void sortedTest() {
        Queue<Integer> sortedTest = new Queue<>();
        sortedTest.enqueue(1);
        sortedTest.enqueue(2);
        sortedTest.enqueue(3);
        assertTrue(isSorted(sortedTest));
    }


    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
