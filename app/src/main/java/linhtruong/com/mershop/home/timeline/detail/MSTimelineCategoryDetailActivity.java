package linhtruong.com.mershop.home.timeline.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import com.google.gson.Gson;
import linhtruong.com.commons.widgets.MSToolbar;
import linhtruong.com.commons.widgets.base.BaseActionView;
import linhtruong.com.mershop.R;
import linhtruong.com.mershop.app.AppComponent;
import linhtruong.com.mershop.app.navigator.ActivityScreen;
import linhtruong.com.mershop.base.BaseActionActivity;
import linhtruong.com.protocol.gson.response.TimelineCategoryResponse;

/**
 * Product detail
 *
 * @author linhtruong
 * @date 10/8/18 - 01:12.
 * @organization VED
 */
public class MSTimelineCategoryDetailActivity extends BaseActionActivity {
    MSTimelineCategoryDetailPresenter mPresenter;

    @Override
    protected BaseActionView onCreateContentView(Bundle savedInstanceState) {
        MSTimelineCategoryDetailView view = new MSTimelineCategoryDetailView(this);
        mPresenter.takeView(view);
        return view;
    }

    @Override
    protected void onConfigToolbar(MSToolbar toolbar) {
        setTitle(R.string.ms_label_detail);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onCreateComponent(AppComponent appComponent) {
        String jsonCategoryItem = getIntent().getStringExtra(Screen.KEY_CATEGORY_ITEM);
        String imgPath = getIntent().getStringExtra(ImageScreen.KEY_IMAGE_PATH);
        TimelineCategoryResponse.CategoryItem data = null;
        if (!TextUtils.isEmpty(jsonCategoryItem)) {
            data = new Gson().fromJson(jsonCategoryItem, TimelineCategoryResponse.CategoryItem.class);
        } else if (!TextUtils.isEmpty(imgPath)) {
            data = new TimelineCategoryResponse.CategoryItem();
            data.photoUrl = imgPath;
        }

        mPresenter = new MSTimelineCategoryDetailPresenter(this, data);

        appComponent.inject(this);
        appComponent.inject(mPresenter);
    }

    @Override
    protected boolean navigationOnClick(View v) {
        return true;
    }

    public static class Screen extends ActivityScreen {
        private static final String KEY_CATEGORY_ITEM = "key_category_item";
        private String mCategoryItem;

        public Screen(String categoryItem) {
            mCategoryItem = categoryItem;
        }

        @Override
        protected void configureIntent(Intent intent) {
            intent.putExtra(KEY_CATEGORY_ITEM, mCategoryItem);
        }

        @Override
        protected Class<? extends Activity> activityClass() {
            return MSTimelineCategoryDetailActivity.class;
        }
    }

    public static class ImageScreen extends ActivityScreen {
        private static final String KEY_IMAGE_PATH = "key_image_path";
        private String mImgPath;

        public ImageScreen(String imgPath) {
            mImgPath = imgPath;
        }

        @Override
        protected void configureIntent(Intent intent) {
            intent.putExtra(KEY_IMAGE_PATH, mImgPath);
        }

        @Override
        protected Class<? extends Activity> activityClass() {
            return MSTimelineCategoryDetailActivity.class;
        }
    }
}
