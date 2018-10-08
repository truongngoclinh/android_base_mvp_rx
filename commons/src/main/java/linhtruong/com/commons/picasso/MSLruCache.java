package linhtruong.com.commons.picasso;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.squareup.picasso.Cache;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/7/18 - 22:15.
 * @organization VED
 */
public class MSLruCache implements Cache {

    private static final char KEY_SEPARATOR = '\n';

    private final LinkedHashMap<String, CacheItem<?>> map;
    private final int maxSize;

    private int size;
    private int putCount;
    private int evictionCount;
    private int hitCount;
    private int missCount;

    /**
     * Create a cache using an appropriate portion of the available RAM as the maximum size.
     */
    public MSLruCache(Context context) {
        this(calculateMemoryCacheSize(context));
    }

    /**
     * Create a cache with a given maximum size in bytes.
     */
    public MSLruCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Max size must be positive.");
        }
        this.maxSize = maxSize;
        this.map = new LinkedHashMap<>(0, 0.75f, true);
    }

    private static int calculateMemoryCacheSize(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        boolean largeHeap = (context.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
        int memoryClass = am.getMemoryClass();
        if (largeHeap) {
            memoryClass = am.getLargeMemoryClass();
        }
        // Target ~15% of the available heap.
        return 1024 * 1024 * memoryClass / 7;
    }

    @Override
    public Bitmap get(String key) {
        return getItem(key, Bitmap.class);
    }

    public byte[] getBytes(String key) {
        return getItem(key, byte[].class);
    }

    private <T> T getItem(String key, Class<T> clazz) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }

        synchronized (this) {
            CacheItem item = map.get(key);
            if (item != null && clazz.isInstance(item.data)) {
                hitCount++;
                return (T) item.data;
            }
            missCount++;
        }
        return null;
    }

    @Override
    public void set(String key, Bitmap bitmap) {
        setItem(key, new BitmapItem(bitmap));
    }

    public void setBytes(String key, byte[] bytes) {
        setItem(key, new BytesItem(bytes));
    }

    private <T> void setItem(String key, @NonNull CacheItem<T> item) {
        if (key == null || item.data == null) {
            throw new NullPointerException("key == null || data == null");
        }

        CacheItem<?> previous;
        synchronized (this) {
            putCount++;
            size += item.size();
            previous = map.put(key, item);
            if (previous != null) {
                size -= previous.size();
            }
        }

        trimToSize(maxSize);
    }

    private void trimToSize(int maxSize) {
        while (true) {
            synchronized (this) {
                if (size < 0 || (map.isEmpty() && size != 0)) {
                    throw new IllegalStateException(
                            getClass().getName() + ".sizeOf() is reporting inconsistent results!");
                }

                if (size <= maxSize || map.isEmpty()) {
                    break;
                }

                Map.Entry<String, CacheItem<?>> toEvict = map.entrySet().iterator().next();
                String key = toEvict.getKey();
                CacheItem<?> value = toEvict.getValue();
                map.remove(key);
                size -= value.size();
                evictionCount++;
            }
        }
    }

    /**
     * Clear the cache.
     */
    public final void evictAll() {
        trimToSize(-1); // -1 will evict 0-sized elements
    }

    @Override
    public final synchronized int size() {
        return size;
    }

    @Override
    public final synchronized int maxSize() {
        return maxSize;
    }

    @Override
    public final synchronized void clear() {
        evictAll();
    }

    @Override
    public final synchronized void clearKeyUri(String uri) {
        boolean sizeChanged = false;
        int uriLength = uri.length();
        for (Iterator<Map.Entry<String, CacheItem<?>>> i = map.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry<String, CacheItem<?>> entry = i.next();
            String key = entry.getKey();
            CacheItem<?> value = entry.getValue();
            int newlineIndex = key.indexOf(KEY_SEPARATOR);
            if (newlineIndex == uriLength && key.substring(0, newlineIndex).equals(uri)) {
                i.remove();
                size -= value.size();
                sizeChanged = true;
            }
        }
        if (sizeChanged) {
            trimToSize(maxSize);
        }
    }

    /**
     * Returns the number of times {@link #get} returned a value.
     */
    public final synchronized int hitCount() {
        return hitCount;
    }

    /**
     * Returns the number of times {@link #get} returned {@code null}.
     */
    public final synchronized int missCount() {
        return missCount;
    }

    /**
     * Returns the number of times {@link #set(String, Bitmap)} was called.
     */
    public final synchronized int putCount() {
        return putCount;
    }

    /**
     * Returns the number of values that have been evicted.
     */
    public final synchronized int evictionCount() {
        return evictionCount;
    }

    private abstract static class CacheItem<T> {
        final T data;

        CacheItem(T data) {
            this.data = data;
        }

        abstract int size();
    }

    private static class BitmapItem extends CacheItem<Bitmap> {

        BitmapItem(@NonNull Bitmap data) {
            super(data);
        }

        @Override
        int size() {
            return data.getByteCount();
        }
    }

    private static class BytesItem extends CacheItem<byte[]> {

        BytesItem(@NonNull byte[] data) {
            super(data);
        }

        @Override
        int size() {
            return data.length;
        }
    }

}
