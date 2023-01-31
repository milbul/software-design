import org.junit.Test;
import org.junit.Assert;

public class LRUCacheTest {
    @Test
    public void putGetTest() {
        LRUCache<Integer, String> lru = new LRUCache<>(5);
        lru.put(1, "a");
        lru.put(2, "b");
        lru.put(3, "c");
        Assert.assertEquals(lru.get(1), "a");
        Assert.assertEquals(lru.get(2), "b");
        Assert.assertEquals(lru.get(3), "c");
        Assert.assertNull(lru.get(10));
    }

    @Test
    public void updateTest() {
        LRUCache<Integer, String> lru = new LRUCache<>(5);
        lru.put(1, "a");
        lru.put(1, "b");
        lru.put(1, "c");
        Assert.assertEquals(lru.get(1), "c");
    }

    @Test
    public void capacityTest() {
        LRUCache<Integer, String> lru = new LRUCache<>(3);
        lru.put(1, "a");
        lru.put(2, "b");
        lru.put(3, "c");
        lru.put(4, "d");
        Assert.assertNull(lru.get(1));
        Assert.assertEquals(lru.get(3), "c");
        Assert.assertEquals(lru.get(4), "d");
    }

    @Test
    public void removeTest() {
        LRUCache<Integer, String> lru = new LRUCache<>(3);
        lru.put(1, "a");
        lru.put(2, "b");
        lru.put(3, "c");
        lru.remove(1);
        Assert.assertNull(lru.get(1));
    }
}