package linhtruong.com.mershop.home.timeline;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import linhtruong.com.commons.MSConstValue;
import linhtruong.com.commons.widgets.MSItemOffSetDecoration;
import linhtruong.com.mershop.R;
import linhtruong.com.mershop.app.navigator.ActivityScreenSwitcher;
import linhtruong.com.mershop.app.navigator.Navigator;
import linhtruong.com.mershop.base.BasePresenter;
import linhtruong.com.mershop.di.MSHomeComponent;
import linhtruong.com.mershop.home.MSHomeBasePage;
import linhtruong.com.protocol.gson.response.TimelineCategoryResponse;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Timeline category page
 *
 * @author linhtruong
 * @date 10/7/18 - 11:40.
 * @organization VED
 */
public class MSTimelineCategoryPage extends MSHomeBasePage {
    private MSHomeComponent mComponent;
    private MSTimelineCategoryPageAdapter mAdapter;
    private List<TimelineCategoryResponse.CategoryItem> mData;

    @BindView(R.id.ms_list_item)
    RecyclerView mRecyclerView;

    @Inject
    ActivityScreenSwitcher mSwitcher;

    public MSTimelineCategoryPage(MSHomeComponent component, BasePresenter presenter, Bundle params) {
        super(presenter, params);
        mComponent = component;
        mComponent.inject(this);

        Type type = new TypeToken<List<TimelineCategoryResponse.CategoryItem>>() {
        }.getType();
        mData = new Gson().fromJson(params.getString(MSConstValue.EXTRA.EXTRA_CATEGORY_CONFIG), type);
    }

    @Override
    protected View onCreateView(Bundle savedInstanceState) {
        View contentView =
                LayoutInflater.from(getContext()).inflate(R.layout.com_linhtruong_mershop_home_timeline_category_page, null);
        ButterKnife.bind(this, contentView);
        initListItem();

        return contentView;
    }

    private void initListItem() {
        int orientation = getActivity().getResources().getConfiguration().orientation;
        mAdapter = new MSTimelineCategoryPageAdapter(item -> {
            String categoryItemJson = new Gson().toJson(item, TimelineCategoryResponse.CategoryItem.class);
            Navigator.onNavigationTimelineDetailActivity(mSwitcher, categoryItemJson);
        }, orientation);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), MSConstValue.SPAN.TIMELINE_CATEGORY_SPAN_SIZE));
        MSItemOffSetDecoration itemOffSetDecoration = new MSItemOffSetDecoration(getActivity(), R.dimen.ms_timeline_list_item_offset);
        mRecyclerView.addItemDecoration(itemOffSetDecoration);
        mAdapter.setData(mData);
    }


    @Override
    protected void onFirstLoad() {

    }

    public interface ICategoryItemInteractor {
        void onItemClicked(TimelineCategoryResponse.CategoryItem item);
    }
}
