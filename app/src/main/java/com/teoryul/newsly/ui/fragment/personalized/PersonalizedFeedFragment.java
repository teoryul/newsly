package com.teoryul.newsly.ui.fragment.personalized;

import com.teoryul.newsly.presenter.personalized.PersonalizedFeedContract;
import com.teoryul.newsly.ui.fragment.BaseNewsFeedFragment;

public class PersonalizedFeedFragment extends BaseNewsFeedFragment
        implements PersonalizedFeedContract.View {

    public static PersonalizedFeedFragment newInstance() {
        return new PersonalizedFeedFragment();
    }
}
