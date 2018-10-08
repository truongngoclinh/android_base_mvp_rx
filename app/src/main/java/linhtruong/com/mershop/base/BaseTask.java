package linhtruong.com.mershop.base;

import android.text.TextUtils;
import io.reactivex.Observable;
import linhtruong.com.commons.utils.MSLogUtil;
import linhtruong.com.mershop.app.AppSchedulers;
import linhtruong.com.mershop.base.task.TaskResource;

import java.util.IllegalFormatException;
import java.util.concurrent.Callable;

/**
 * Base task
 *
 * @author linhtruong
 * @date 10/3/18 - 21:34.
 * @organization VED
 */
public abstract class BaseTask<T> {
    private final String TAG = "[" + getClass().getSimpleName() + "] ";

    public abstract Observable<T> execute(TaskResource res);

    /**
     * Convenient method to run a CPU-intensive task on COMPUTE scheduler.
     *
     * @param procedure task to run on COMPUTE scheduler
     * @param <V>
     * @return
     */
    protected <V> Observable<V> runCompute(final Callable<V> procedure) {
        return Observable.fromCallable(procedure).subscribeOn(AppSchedulers.COMPUTE);
    }

    protected final void log(String content, Object... args) {
        if (TextUtils.isEmpty(content)) {
            return;
        }

        String log;
        try {
            log = args == null || args.length == 0 ? content : String.format(content, args);
        } catch (IllegalFormatException e) {
            MSLogUtil.e(e);
            return;
        }

        MSLogUtil.d(TAG + log);
    }
}
