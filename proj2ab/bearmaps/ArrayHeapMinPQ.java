package bearmaps;

import java.util.*;


public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private int size;
    private TNode[] keys;
    private HashMap<T, Integer> keySet;

    private static class TNode<T> {
        private double value;
        private T obj;

        TNode(T obj, double value) {
            this.obj = obj;
            this.value = value;
        }

        double getValue() {
            return this.value;
        }

        T getObj() {
            return this.obj;
        }

        void setValue(double newVal) {
            this.value = newVal;
        }
    }

    ArrayHeapMinPQ() {
        this(4);

    }

    ArrayHeapMinPQ(int size) {
        this.keys = new TNode[size];
        this.size = 0;
        this.keySet = new HashMap<>();
        keys[0] = null;
    }


    // method for testing
    private TNode[] getHeap() {
        return keys;
    }

    // returns left child node index
    private int leftChild(int k) {
        int index = k * 2;

        if (index >= keys.length) return 0;

        return index;
    }

    // returns right child node index
    private int rightChild(int k) {
        int index = (k * 2) + 1;

        if (index >= keys.length) return 0;

        return index;
    }

    //returns parent node index
    private int parent(int k) {
        return k / 2;
    }

    // swaps two node spaces
    private TNode swap(int k, int parentK) {
        TNode swapOne = keys[k];
        TNode swapTwo = keys[parentK];
        T itemOne = (T) swapOne.obj;
        T itemTwo = (T) swapTwo.obj;

        keys[k] = swapTwo;
        keySet.put(itemTwo, k);

        keys[parentK] = swapOne;
        keySet.put(itemOne, parentK);

        return swapOne;
    }

    // "swims" node from bottom of tree to correct position
    private void swim(int k) {
        if (keys[parent(k)] != null && (keys[parent(k)].getValue() > keys[k].getValue())) {
            swap(k, parent(k));
            swim(parent(k));
        }
    }


    // "sinks" node from top of tree to appropriote position
    private void sink(int k) {
        TNode left = keys[leftChild(k)];
        TNode right = keys[rightChild(k)];
        TNode check = null;
        int belowK = 0;

        if (left == null || (right != null && left.getValue() > right.getValue())) {
            check = right;
            belowK = rightChild(k);
        } else if (right == null || (left.getValue() <= right.getValue())) {
            check = left;
            belowK = leftChild(k);
        }

        if (check != null) {
            swap(k, belowK);
            sink(belowK);
        }

    }

    //resize array based on size change.  Never more than 3/4 empty.  More than 3/4 empty make smaller.
    //Reaches 90% make larger.
    private void reSize(boolean enlarge) {
        int length = keys.length;
        TNode[] newKeys = null;

        if (enlarge) {
            newKeys = new TNode[length * 2];
        } else {
            newKeys = new TNode[length / 2 + 2];
        }

        for (int i = 0; i <= size; i++) {
            newKeys[i] = keys[i];
        }

        this.keys = newKeys;

    }

    // adds item to appropriate position in tree
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Queue already contains this item.");
        } else {
            keySet.put(item, size + 1);
            TNode<Object> next = new TNode<>(item, priority);
            keys[size + 1] = next;
            swim(size + 1);
            size++;

            if (size >= keys.length - 2) {
                reSize(true);
            }
        }
    }

    @Override
    public boolean contains(Object item) {
        return keySet.containsKey(item);
    }


    //return smallest object in array
    @Override
    public T getSmallest() {
        if (size == 0) throw new NoSuchElementException();

        return (T) keys[1].getObj();
    }

    //remove smallest object in array and returns that item
    @Override
    public T removeSmallest() {
        if (size == 0) throw new NoSuchElementException();

        TNode min = swap(1, size);
        keys[size] = null;
        keySet.remove(min.obj);
        sink(1);
        size--;

        double fill = (double) size / (double) keys.length;

        if (fill < .25 && keys.length > 4) {
            reSize(false);
        }

        return (T) min.getObj();
    }

    @Override
    public int size() {
        return this.size;
    }

    // changes priority of item in queue and updates queue
    @Override
    public void changePriority(Object item, double priority) {
        if (!contains(item)) throw new NoSuchElementException();

        int index = keySet.get(item);
        keys[index].setValue(priority);

        /*
        if (keys[parent(index)].getValue() < priority) {
            sink(index);
        } else {
            swim(index);
        }*/

        sink(index);
        swim(index);
    }


    // main method for testing only
    public static void main(String[] args) {

        //test my implementation
        ArrayHeapMinPQ<String> test = new ArrayHeapMinPQ<>();

        Random r = new Random();

        long start = System.currentTimeMillis();

        for (int i = 0; i < 5000; i += 1) {
            test.add("test" + i, r.nextDouble() * 10000);
        }


        for (int i = 1000; i < 2000; i++) {
            test.changePriority("test" + i, r.nextDouble() * 10000);
        }

        for (int i = 0; i < 3000; i++) {
           test.removeSmallest();
        }


        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start)/1000.0 +  " seconds.");




        //test naive implementation
        NaiveMinPQ<String> testNext = new NaiveMinPQ<>();

        long startNew = System.currentTimeMillis();

        for (int i = 0; i < 5000; i += 1) {
            testNext.add("test" + i, r.nextDouble() * 10000);
        }


        for (int i = 1000; i < 2000; i++) {
            testNext.changePriority("test" + i, r.nextDouble() * 10000);
        }

        for (int i = 0; i < 3000; i++) {
            testNext.removeSmallest();
        }


        long endNew = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (endNew - startNew)/1000.0 +  " seconds.");


    }
}