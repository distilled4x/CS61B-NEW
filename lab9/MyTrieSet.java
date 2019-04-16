import java.util.ArrayList;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private static final int R = 128;
    private Node root;

    private static class Node {
        private char c;
        private boolean isKey;
        private DataIndexedCharMap map;

        private Node(char c, boolean b, int R) {
            this.c = c;
            this.isKey = b;
            this.map = new DataIndexedCharMap(R);
        }
    }

    private static class DataIndexedCharMap {
        private Node[] items;

        private DataIndexedCharMap(int R) {
            items = new Node[R];
        }

        private void put(char c, Node val) {
            items[c] = val;
        }

        private Node get(char c) {
            return items[c];
        }

        private boolean containsKey(char c) {
            Node x = get(c);
            return x != null;
        }

    }

    @Override
    public void clear() {
        this.root = null;
    }

    @Override
    public boolean contains(String key) {
        if (containsPrefix(key) == null) {
            return false;
        } else {
            return (containsPrefix(key).isKey);
        }

    }

    private Node containsPrefix(String key) {
        if (key == null || key.length() < 1 || root == null) {
            return null;
        }

        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return null;
            }
            curr = curr.map.get(c);
        }
        return curr;
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }

        if (root == null) {
            root = new Node(key.charAt(0), false, R);
        }

        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false, R));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }


    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> words = new ArrayList<String>();
        Node curr = containsPrefix(prefix);
        StringBuilder word = new StringBuilder(prefix);

        return collect(word, words, curr);


    }

    private List<String> collect(StringBuilder prefix, List<String> results, Node x) {
        if (x == null) return results;
        if (x.isKey) results.add(prefix.toString());

        for (char c = 0; c < R; c++) {
            prefix.append(c);
            collect(prefix, results, x.map.get(c));
            prefix.deleteCharAt(prefix.length() - 1);
        }

        return results;
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException("Not Supported");
    }


    public static void main(String[] args) {
        MyTrieSet test = new MyTrieSet();
        test.add("hello");
        test.add("heck");
        test.add("hell");
        test.add("heyday");
        List<String> words = test.keysWithPrefix("he");
        for (String word : words) {
            System.out.println(word);
        }

    }
}
