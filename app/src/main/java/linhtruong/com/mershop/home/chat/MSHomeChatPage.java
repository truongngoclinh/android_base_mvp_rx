package linhtruong.com.mershop.home.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import butterknife.BindView;
import butterknife.ButterKnife;
import linhtruong.com.mershop.R;
import linhtruong.com.mershop.base.BasePresenter;
import linhtruong.com.mershop.home.MSHomeBasePage;

/**
 * Chat page
 *
 * @author linhtruong
 * @date 10/6/18 - 22:40.
 * @organization VED
 */
public class MSHomeChatPage extends MSHomeBasePage {
    public MSHomeChatPage(BasePresenter presenter, Bundle params) {
        super(presenter, params);
    }

    @Override
    protected View onCreateView(Bundle savedInstanceState) {
        View contentView =
                LayoutInflater.from(getContext()).inflate(R.layout.com_linhtruong_mershop_home_chat_page, null);
        ButterKnife.bind(this, contentView);

        return contentView;
    }

    @Override
    protected void onFirstLoad() {

    }
}
