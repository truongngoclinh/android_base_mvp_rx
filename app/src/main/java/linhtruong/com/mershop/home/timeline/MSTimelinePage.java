package linhtruong.com.mershop.home.timeline;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import linhtruong.com.commons.MSConstValue;
import linhtruong.com.commons.utils.MSCameraUtil;
import linhtruong.com.commons.utils.MSPermissionUtil;
import linhtruong.com.commons.widgets.MSViewPager;
import linhtruong.com.mershop.R;
import linhtruong.com.mershop.app.navigator.ActivityScreenSwitcher;
import linhtruong.com.mershop.base.BasePresenter;
import linhtruong.com.mershop.base.pager.MSPagerPage;
import linhtruong.com.mershop.base.task.DataObserver;
import linhtruong.com.mershop.di.MSHomeComponent;
import linhtruong.com.mershop.home.MSHomeAdapter;
import linhtruong.com.mershop.home.MSHomeBasePage;
import linhtruong.com.mershop.home.timeline.detail.MSTimelineCategoryDetailActivity;
import linhtruong.com.mershop.task.TaskGetTimeLineConfig;
import linhtruong.com.protocol.gson.response.TimelineCategoryResponse;

import javax.inject.Inject;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

/**
 * Timeline page with dynamic tabs based on remote config
 *
 * @author linhtruong
 * @date 10/6/18 - 15:18.
 * @organization VED
 */
public class MSTimelinePage extends MSHomeBasePage {
    private final static int TAB_SCROLLABLE_THRESHOLD = 4;
    private MSHomeComponent mComponent;
    private MSNestedPagerAdapter mAdapter;
    private TaskGetTimeLineConfig mTaskGetTimeLineConfig;
    private String mImageCaptureStoragePath;

    @BindView(R.id.ms_timeline_tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.ms_timeline_viewpager)
    MSViewPager mViewPager;

    @BindView(R.id.ms_fab)
    FloatingActionButton mFab;

    @Inject
    ActivityScreenSwitcher mSwitcher;

    public MSTimelinePage(MSHomeComponent component, BasePresenter presenter, Bundle params) {
        super(presenter, params);
        mComponent = component;
        mComponent.inject(this);
    }

    @Override
    protected View onCreateView(Bundle savedInstanceState) {
        View contentView =
                LayoutInflater.from(getContext()).inflate(R.layout.com_linhtruong_mershop_home_timeline_page, null);
        ButterKnife.bind(this, contentView);

        getTimelineData();
        initFab();

        return contentView;
    }

    private void getTimelineData() {
        mTaskGetTimeLineConfig = new TaskGetTimeLineConfig();
        mComponent.inject(mTaskGetTimeLineConfig);

        showLoading(false);
        getPresenter().executeTask(mTaskGetTimeLineConfig, new DataObserver<HashMap<String, List<TimelineCategoryResponse.CategoryItem>>>() {
            @Override
            public void onNext(HashMap<String, List<TimelineCategoryResponse.CategoryItem>> response) {
                hideLoading();
                if (response != null) {
                    initTabs(response);
                }
            }

            @Override
            public void onError(Throwable e) {
                hideLoading();
                super.onError(e);
                showNetworkError();
            }
        });
    }

    private void initFab() {
        mFab.setOnClickListener(v -> {
            runWithPermission(MSPermissionUtil.Permissions.CAMERA, () -> {
                runWithPermission(MSPermissionUtil.Permissions.WRITE_STORAGE, () -> captureImage());
            });
        });
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = MSCameraUtil.getOutputMediaFile(MSConstValue.GALLERY.MEDIA_TYPE_IMAGE);
        if (file != null) {
            mImageCaptureStoragePath = file.getAbsolutePath();
        }
        Uri fileUri = MSCameraUtil.getOutputMediaFileUri(getActivity().getApplicationContext(), file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture
        getActivity().startActivityForResult(intent, MSConstValue.REQUEST_CODE.CAMERA_CAPTURE_IMAGE);
    }

    private void initTabs(HashMap<String, List<TimelineCategoryResponse.CategoryItem>> timelineData) {
        mAdapter = new MSNestedPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setHorizontalSwipeEnabled(true);
        mViewPager.setOffscreenPageLimit(5);
        mTabLayout.setupWithViewPager(mViewPager);

        List<MSHomeBasePage> pages = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> tabTitles = new ArrayList<>();
        Set<String> tabs = timelineData.keySet();
        Iterator<String> iterator = tabs.iterator();
        while (iterator.hasNext()) {
            String tab = iterator.next();
            List<TimelineCategoryResponse.CategoryItem> data = timelineData.get(tab);
            Type type = new TypeToken<List<TimelineCategoryResponse.CategoryItem>>() {
            }.getType();

            Bundle bundle = new Bundle();
            bundle.putString(MSConstValue.EXTRA.EXTRA_CATEGORY_CONFIG, new Gson().toJsonTree(data, type).toString());
            MSTimelineCategoryPage page = new MSTimelineCategoryPage(mComponent, getPresenter(), bundle);
            pages.add(page);
            titles.add(tab);
            tabTitles.add(tab);
        }

        for (int i = 0; i < pages.size(); i++) {
            mAdapter.addPage(pages.get(i), titles.get(i));
        }
        mAdapter.notifyDataSetChanged();

        for (int i = 0; i < pages.size(); i++) {
            mTabLayout.getTabAt(i).setText(tabTitles.get(i));
        }

        if (mTabLayout.getTabCount() > TAB_SCROLLABLE_THRESHOLD) {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    @Override
    protected void onFirstLoad() {
    }

    private class MSNestedPagerAdapter extends MSHomeAdapter {
        private boolean mShown;

        void setShown(boolean shown) {
            mShown = shown;

            for (int i = 0, count = getCount(); i < count; i++) {
                MSHomeBasePage page = (MSHomeBasePage) getPage(i);
                if (shown && page == mCurPrimaryItem) {
                    if (page.isHidden()) {
                        page.onShown();
                    }
                } else if (!page.isHidden()) {
                    page.onHidden();
                }
            }
        }

        @Override
        protected void onCurrentPageChanged(MSPagerPage old, MSPagerPage current) {
            if (old != null) {
                old.onHidden();
            }
            if (current != null && mShown) {
                current.onShown();
            }
        }
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MSConstValue.REQUEST_CODE.CAMERA_CAPTURE_IMAGE) {
             if (resultCode == RESULT_OK)  {
                mSwitcher.open(new MSTimelineCategoryDetailActivity.ImageScreen(mImageCaptureStoragePath));
             }
        }

        return false;
    }
}

