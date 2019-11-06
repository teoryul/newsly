package com.teoryul.newsly.ui.fragment.health;

import com.teoryul.newsly.App;
import com.teoryul.newsly.R;
import com.teoryul.newsly.presenter.health.HealthFeedContract;
import com.teoryul.newsly.ui.fragment.BaseNewsFeedFragment;

public class HealthFeedFragment extends BaseNewsFeedFragment
        implements HealthFeedContract.View {

    public static HealthFeedFragment newInstance() {
        return new HealthFeedFragment();
    }

    @Override
    public String getFragmentTag() {
        return App.getInstance().getString(R.string.tab_title_health);
    }
}
