package com.teoryul.newsly.ui.fragment.sports;

import com.teoryul.newsly.App;
import com.teoryul.newsly.R;
import com.teoryul.newsly.presenter.sports.SportsFeedContract;
import com.teoryul.newsly.ui.fragment.BaseNewsFeedFragment;

public class SportsFeedFragment extends BaseNewsFeedFragment
        implements SportsFeedContract.View {

    public static SportsFeedFragment newInstance() {
        return new SportsFeedFragment();
    }

    @Override
    public String getFragmentTag() {
        return App.getInstance().getString(R.string.tab_title_sports);
    }
}
