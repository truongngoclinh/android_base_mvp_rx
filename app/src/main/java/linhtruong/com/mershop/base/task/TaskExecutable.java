package linhtruong.com.mershop.base.task;

import android.content.Context;
import android.content.ContextWrapper;
import io.reactivex.disposables.Disposable;
import linhtruong.com.mershop.base.BaseTask;

/**
 * Provide ability to execute a task
 *
 * @author linhtruong
 * @date 10/4/18 - 08:03.
 * @organization VED
 */
public interface TaskExecutable {
    class Extractor {

        public static TaskExecutable extract(Context context) {
            if (context instanceof TaskExecutable) {
                return (TaskExecutable) context;
            }

            if (context instanceof ContextWrapper) {
                Context wrapped = ((ContextWrapper) context).getBaseContext();
                if (wrapped instanceof TaskExecutable) {
                    return (TaskExecutable) wrapped;
                }
            }

            return null;
        }
    }

    /**
     * Execute a task and subscribe immediately to the returned observable
     *
     * @param task              task to be executed
     * @param callback          callback for the observable
     * @param trackSubscription true if subscription will be disposed upon destroy of current activity/fragment
     * @param <T>               return type
     * @return subscription
     */
    <T> Disposable executeTask(BaseTask<T> task, final DataObserver<? super T> callback, boolean trackSubscription);
}
