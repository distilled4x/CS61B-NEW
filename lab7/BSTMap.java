import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;

    private class Node {
        private K key;
        private V val;
        private Node left, right;
        private int size;

        public Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }


    @Override
    public void clear() {
        this.root.val = null;
        this.root.left = null;
        this.root.right = null;
        this.root.size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException();

        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    public V get(Node x, K key) {
        if (key == null) throw new IllegalArgumentException();
        if (x == null) return null;

        int compare = key.compareTo(x.key);

        if (compare < 0) {
            return get(x.left, key);
        } else if (compare > 0) {
            return get(x.right, key);
        } else {
            return x.val;
        }
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException();
        this.root = put(root, key, value);
    }

    public Node put(Node x, K key, V val) {
        if (x == null) return new Node(key, val, 1);
        int compare = key.compareTo(x.key);

        if (compare < 0) {
            x.left = put(x.left, key, val);
        } else if (compare > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
        }

        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
