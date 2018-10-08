package linhtruong.com.mershop.app;

import android.support.annotation.NonNull;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Application thread pool
 *
 * @author linhtruong
 * @date 10/1/18 - 16:05.
 * @organization VED
 */
public class AppSchedulers {
    private static final int POOL_SIZE_IO = 8;
    private static final int POOL_SIZE_DB_READ = 2;
    private static final int KEEP_ALIVE_TIME = 30; // seconds

    // common IO i.e. network, db write
    private static final ThreadPoolExecutor sIoExecutor = new ThreadPoolExecutor(POOL_SIZE_IO, POOL_SIZE_IO,
            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new IoThreadFactory(),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    static {
        sIoExecutor.allowCoreThreadTimeOut(true);
    }

    // db read
    private static final ThreadPoolExecutor sDbReadExecutor = new ThreadPoolExecutor(POOL_SIZE_DB_READ,
            POOL_SIZE_DB_READ, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
            new DbReadThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());

    static {
        sDbReadExecutor.allowCoreThreadTimeOut(true);
    }

    public static final Scheduler IO = Schedulers.from(sIoExecutor);
    public static final Scheduler DB_READ = Schedulers.from(sDbReadExecutor);
    public static final Scheduler UI = AndroidSchedulers.mainThread();
    public static final Scheduler COMPUTE = Schedulers.computation();

    private static final class IoThreadFactory implements ThreadFactory {

        private final AtomicInteger index = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "io-" + index.getAndIncrement());
        }
    }

    private static final class DbReadThreadFactory implements ThreadFactory {

        private final AtomicInteger index = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "db-r-" + index.getAndIncrement());
        }
    }
}
