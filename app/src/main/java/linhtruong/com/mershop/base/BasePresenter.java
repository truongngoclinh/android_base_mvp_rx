package linhtruong.com.mershop.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import linhtruong.com.mershop.app.AppSchedulers;
import linhtruong.com.mershop.base.pager.IPageContainer;
import linhtruong.com.mershop.base.pager.MSPage;
import linhtruong.com.mershop.base.task.DataObserver;
import linhtruong.com.mershop.base.task.TaskExecutable;
import linhtruong.com.mershop.base.task.TaskManager;
import linhtruong.com.mershop.base.task.WeakDelegateObserver;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Provider presenter to handle business logic with full life circle mapping with its activity
 *
 * @author linhtruong
 * @date 10/1/18 - 14:44.
 * @organization VED
 */
public abstract class BasePresenter<V extends View> implements TaskExecutable, IPageContainer {
    @Inject
    TaskManager mTaskManager;

    /**
     * Used to keep non-tracked data observer alive until destroyed
     */
    private List<DataObserver> mWeakObserverHolder;
    private final CompositeDisposable mDisposables = new CompositeDisposable();

    protected V view = null;
    private boolean isLoaded;

    public final V getView() {
        return view;
    }

    private final List<MSPage> mPages = new ArrayList<>();
    private boolean mIsStarted, mIsResumed;

    /**
     * Recommend to call in when activity reaches onStart()
     *
     * @param view
     */
    public void takeView(V view) {
        if (view == null) {
            throw new NullPointerException("New view must not be null");
        }

        if (this.view != view) {
            if (this.view != null) {
                dropView(this.view);
            }

            this.view = view;
            if (getView() != null && !isLoaded) {
                isLoaded = true;
                onLoad();
            }
        }
    }

    /**
     * Recommend to call in when activity reaches onDestroy()
     *
     * @param view
     */
    public void dropView(V view) {
        if (view == null) {
            throw new NullPointerException("Dropped view must not be null");
        }

        if (view == this.view) {
            isLoaded = false;
            this.view = null;
            onDestroy();
        }
    }


    /**
     * Convenient method for executing tasks with bounded lifecycle i.e. terminates upon destroy of activity
     *
     * @param task
     * @param callback
     * @param <T>
     * @return subscription for early or customized termination
     * @see #executeTask(BaseTask, DataObserver, boolean)
     */
    public <T> Disposable executeTask(BaseTask<T> task, final DataObserver<? super T> callback) {
        return executeTask(task, callback, true);
    }

    @Override
    public <T> Disposable executeTask(BaseTask<T> task, DataObserver<? super T> callback, boolean trackSubscription) {
        Disposable disposable;
        if (callback != null) {
            DisposableObserver<? super T> observer = callback;
            if (!trackSubscription) {
                if (mWeakObserverHolder == null) {
                    mWeakObserverHolder = new ArrayList<>();
                }
                mWeakObserverHolder.add(callback);

                // use a weak reference to avoid leaking activity if not tracking subscription
                observer = new WeakDelegateObserver<>(callback);
            }
            disposable = mTaskManager.execute(task).observeOn(AppSchedulers.UI).subscribeWith(observer);
        } else {
            disposable = mTaskManager.executeAndTrigger(task);
        }

        if (trackSubscription) {
            mDisposables.add(disposable);
        }

        return disposable;
    }

    protected void onLoad() {
        mIsStarted = true;
        for (MSPage page : mPages) {
            page.onStart();
        }
    }

    protected void onDestroy() {
        mDisposables.clear();
        if (mWeakObserverHolder != null) {
            mWeakObserverHolder.clear();
        }


        if (!mPages.isEmpty()) {
            List<MSPage> temp = new ArrayList<>(mPages);
            mPages.clear();

            for (MSPage p : temp) {
                p.onDestroy();
            }
        }
    }

    protected void onRestore(@NonNull Bundle savedInstanceState) {
    }

    protected void onSave(@NonNull Bundle outState) {
    }

    public void onResume() {
        mIsResumed = true;
        for (MSPage page : mPages) {
            page.onResume();
        }
    }

    public void onPause() {
        mIsResumed = false;
        for (MSPage page : mPages) {
            page.onPause();
        }
    }

    public void onStop() {
        mIsStarted = false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (MSPage page : mPages) {
            page.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for (MSPage page: mPages) {
            page.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void addDisposable(Disposable disposable) {
        mDisposables.add(disposable);
    }

    @Override
    public void registerPage(MSPage page) {
        mPages.add(page);
        if (mIsStarted) {
            page.onStart();
        }
        if (mIsResumed) {
            page.onResume();
        }
    }

    @Override
    public void deregisterPage(MSPage page) {
        mPages.remove(page);
    }
}
