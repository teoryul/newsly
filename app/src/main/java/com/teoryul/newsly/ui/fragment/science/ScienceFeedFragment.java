package com.teoryul.newsly.ui.fragment.science;

import com.teoryul.newsly.App;
import com.teoryul.newsly.R;
import com.teoryul.newsly.presenter.science.ScienceFeedContract;
import com.teoryul.newsly.ui.fragment.BaseNewsFeedFragment;

public class ScienceFeedFragment extends BaseNewsFeedFragment
        implements ScienceFeedContract.View {

    public static ScienceFeedFragment newInstance() {
        return new ScienceFeedFragment();
    }

    @Override
    public String getFragmentTag() {
        return App.getInstance().getString(R.string.tab_title_science);
    }
}
