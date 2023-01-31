import java.util.HashMap;
import java.util.LinkedList;

public class LRUCache<K, V> {
    private final LinkedList<K> queue = new LinkedList<>();
    private final HashMap<K, V> map = new HashMap<>();
    private final int capacity;

    public LRUCache(int capacity) {
        assert capacity > 0;
        this.capacity = capacity;
    }

    public V get(K key) {
        if (map.containsKey(key)) {
            V value = map.get(key);
            queue.remove(key);
            queue.addFirst(key);
            assert queue.getFirst() == key;
            assert map.size() <= capacity;
            assert map.size() == queue.size();
            return value;
        }
        return null;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            queue.remove(key);
        } else {
            if (queue.size() == capacity) {
                K needToRemove = queue.removeLast();
                map.remove(needToRemove);
                assert !map.containsKey(needToRemove);
            }
        }
        queue.addFirst(key);
        map.put(key, value);
        assert queue.getFirst() == key;
        assert map.size() <= capacity;
        assert map.size() == queue.size();
    }

    public void remove(K key) {
        if (queue.contains(key)) {
            queue.remove(key);
            map.remove(key);
        }
        assert !map.containsKey(key);
        assert map.size() == queue.size();
    }
}
