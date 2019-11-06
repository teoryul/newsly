package com.teoryul.newsly.ui.fragment.business;

import com.teoryul.newsly.App;
import com.teoryul.newsly.R;
import com.teoryul.newsly.presenter.business.BusinessFeedContract;
import com.teoryul.newsly.ui.fragment.BaseNewsFeedFragment;

public class BusinessFeedFragment extends BaseNewsFeedFragment
        implements BusinessFeedContract.View {

    public static BusinessFeedFragment newInstance() {
        return new BusinessFeedFragment();
    }

    @Override
    public String getFragmentTag() {
        return App.getInstance().getString(R.string.tab_title_business);
    }
}
