package linhtruong.com.mershop.base.task;

import io.reactivex.observers.DisposableObserver;

import java.lang.ref.WeakReference;

/**
 * Observer with weakref
 *
 * @author linhtruong
 * @date 10/4/18 - 08:16.
 * @organization VED
 */
public class WeakDelegateObserver<T> extends DisposableObserver<T> {
    private final WeakReference<DataObserver<T>> mClient;

    public WeakDelegateObserver(DataObserver<T> dataObserver) {
        mClient = new WeakReference<>(dataObserver);
    }

    @Override
    public void onError(Throwable e) {
        DataObserver<T> observer = mClient.get();
        if (observer != null) {
            observer.onError(e);
        }
    }

    @Override
    public void onComplete() {
        DataObserver<T> observer = mClient.get();
        if (observer != null) {
            observer.onComplete();
        }
    }

    @Override
    public void onNext(T t) {
        DataObserver<T> observer = mClient.get();
        if (observer != null) {
            observer.onNext(t);
        }
    }
}
