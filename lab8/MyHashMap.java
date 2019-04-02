import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int initialSize;
    private double loadFactor;
    private int size;
    private HashSet<K> keys;
    private ArrayList<Entry<K, V>> bins;

    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        this.keys = new HashSet<>();
        this.size = 0;
        this.bins = new ArrayList<>(initialSize);
        setClear();

    }

    @Override
    public void clear() {
        this.size = 0;
        this.bins.clear();
        this.keys.clear();
        setClear();
    }

    @Override
    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    @Override
    public V get(K key) {
        Entry<K, V> x = find(key, bins.get(hash(key)));
        if (x == null) {
            return null;
        } else {
            return x.getValue();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        int h = hash(key);
        Entry<K, V> x = find(key, bins.get(h));

        if (x == null) {
            bins.set(h, new Entry<>(key, value, bins.get(h)));
            keys.add(key);
            size++;
            if (size > bins.size() * loadFactor) {
                grow();
            }
        } else {
            x.setValue(value);
        }
    }

    private void setClear() {
        bins.addAll(Collections.nCopies(initialSize, null));
    }


    private void grow() {
        MyHashMap<K, V> newMap = new MyHashMap<>(initialSize * 2, loadFactor);
        for (Entry<K, V> bin : bins) {
            for (Entry<K, V> x = bin; x != null; x = x.next) {
                newMap.put(x.getKey(), x.getValue());
            }
        }

        this.bins = newMap.bins;
        this.initialSize = newMap.initialSize;
    }


    private Entry<K, V> find (K key, Entry<K, V> bin) {
        for (Entry<K, V> x = bin; x != null; x = x.next) {
            if (key == null && x.key == null || key.equals(x.key)) {
                return x;
            }
        }
        return null;
    }

    private int hash(K key) {
        if (key == null) {
            return 0;
        } else {
            return (0x7fffffff & key.hashCode()) % bins.size();
        }
    }

    @Override
    public Set<K> keySet() {
        return keys;
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
        return keys.iterator();
    }



    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry (K key, V value, Entry<K,V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V x) {
            V old = value;
            this.value = x;
            return old;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Entry)) return false;
            Entry<?, ?> node = (Entry<?, ?>) o;
            return Objects.equals(key, node.key) &&
                    Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }
}
