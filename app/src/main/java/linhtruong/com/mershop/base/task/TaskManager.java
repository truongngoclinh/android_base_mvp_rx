package linhtruong.com.mershop.base.task;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import linhtruong.com.commons.utils.MSLogUtil;
import linhtruong.com.mershop.BuildConfig;
import linhtruong.com.mershop.app.AppSchedulers;
import linhtruong.com.mershop.base.BaseTask;

import javax.inject.Inject;

/**
 * Manage tasks
 *
 * @author linhtruong
 * @date 10/3/18 - 21:21.
 * @organization VED
 */
public class TaskManager {
    public static final DataObserver<Object> EMPTY = new DataObserver<Object>() {
        @Override
        public void onNext(Object o) {
        }
    };

    @Inject
    TaskResource taskResource;

    /**
     * The task will be deferred so that all of its internal logic will be
     * executed in {@link linhtruong.com.mershop.app.AppSchedulers#COMPUTE} scheduler by default.
     *
     * @param task task to be executed
     * @param <T>  return type
     * @return an observable task that can be subscribed to
     */
    public <T> Observable<T> execute(final BaseTask<T> task) {
        final String taskName = task.getClass().getSimpleName();
        return Observable.defer(() -> task.execute(taskResource)
                .doOnSubscribe(disposable -> MSLogUtil.d("[%s] starts execution", taskName))
                .compose(new FrequencyMonitorTransform<>(task))).subscribeOn(AppSchedulers.COMPUTE);
    }

    /**
     * The task will be deferred so that all of its internal logic will be
     * executed in {@link linhtruong.com.mershop.app.AppSchedulers#COMPUTE} scheduler by default.
     *
     * @param task task to be executed
     * @param <T>  return type
     * @return subscription of the task in case a future disposal is necessary
     */
    public <T> Disposable executeAndTrigger(final BaseTask<T> task) {
        final String taskName = task.getClass().getSimpleName();
        return Observable.defer(() -> task.execute(taskResource)
                .doOnSubscribe(disposable -> MSLogUtil.d("[%s] starts execution (auto-trigger)", taskName)))
                .subscribeOn(AppSchedulers.COMPUTE).subscribeWith(EMPTY);
    }

    private class FrequencyMonitorTransform<T> implements ObservableTransformer<T, T> {
        private final static long MIN_WARN_INTERVAL = 100; // 100ms
        private final String mTaskName;

        FrequencyMonitorTransform(BaseTask<T> task) {
            mTaskName = task.getClass().getSimpleName();
        }

        @Override
        public ObservableSource<T> apply(Observable<T> upstream) {
            if (BuildConfig.DEBUG) {
                return upstream.doOnNext(new Consumer<T>() {
                    private volatile long lastTimeStamp = 0;

                    @Override
                    public void accept(T t) {

                        long now = System.currentTimeMillis();
                        long diff = now - lastTimeStamp;
                        if (diff < MIN_WARN_INTERVAL) {
                            MSLogUtil.e("WARNING! %s is emitting too frequently: interval=%dms", mTaskName, diff);
                        }
                        lastTimeStamp = now;
                    }
                });
            }

            return upstream;
        }
    }
}
