package linhtruong.com.mershop.base.task;

import io.reactivex.observers.DisposableObserver;
import linhtruong.com.commons.utils.MSLogUtil;

/**
 * Observer
 *
 * @author linhtruong
 * @date 10/3/18 - 21:35.
 * @organization VED
 */
public abstract class DataObserver<T> extends DisposableObserver<T> {

    @Override
    public abstract void onNext(T t);

    @Override
    public void onError(Throwable e) {
        MSLogUtil.e(e);
    }

    @Override
    public void onComplete() {

    }
}
