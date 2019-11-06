package com.teoryul.newsly.ui.fragment.technology;

import com.teoryul.newsly.App;
import com.teoryul.newsly.R;
import com.teoryul.newsly.presenter.technology.TechnologyFeedContract;
import com.teoryul.newsly.ui.fragment.BaseNewsFeedFragment;

public class TechnologyFeedFragment extends BaseNewsFeedFragment
        implements TechnologyFeedContract.View {

    public static TechnologyFeedFragment newInstance() {
        return new TechnologyFeedFragment();
    }

    @Override
    public String getFragmentTag() {
        return App.getInstance().getString(R.string.tab_title_technology);
    }
}
