package com.teoryul.newsly.ui.fragment.entertainment;

import com.teoryul.newsly.App;
import com.teoryul.newsly.R;
import com.teoryul.newsly.presenter.entertainment.EntertainmentFeedContract;
import com.teoryul.newsly.ui.fragment.BaseNewsFeedFragment;

public class EntertainmentFeedFragment extends BaseNewsFeedFragment
        implements EntertainmentFeedContract.View {

    public static EntertainmentFeedFragment newInstance() {
        return new EntertainmentFeedFragment();
    }

    @Override
    public String getFragmentTag() {
        return App.getInstance().getString(R.string.tab_title_entertainment);
    }
}
