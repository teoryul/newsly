package com.teoryul.newsly.ui.fragment.topheadlines;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.teoryul.newsly.R;
import com.teoryul.newsly.adapter.TabPageAdapter;
import com.teoryul.newsly.presenter.business.BusinessFeedPresenter;
import com.teoryul.newsly.presenter.entertainment.EntertainmentFeedPresenter;
import com.teoryul.newsly.presenter.health.HealthFeedPresenter;
import com.teoryul.newsly.presenter.science.ScienceFeedPresenter;
import com.teoryul.newsly.presenter.sports.SportsFeedPresenter;
import com.teoryul.newsly.presenter.technology.TechnologyFeedPresenter;
import com.teoryul.newsly.ui.fragment.BaseFragment;
import com.teoryul.newsly.ui.fragment.business.BusinessFeedFragment;
import com.teoryul.newsly.ui.fragment.entertainment.EntertainmentFeedFragment;
import com.teoryul.newsly.ui.fragment.health.HealthFeedFragment;
import com.teoryul.newsly.ui.fragment.science.ScienceFeedFragment;
import com.teoryul.newsly.ui.fragment.sports.SportsFeedFragment;
import com.teoryul.newsly.ui.fragment.technology.TechnologyFeedFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TopHeadlinesFragment extends BaseFragment {

    @BindView(R.id.tab_layout_top_headlines)
    TabLayout tabLayout;
    @BindView(R.id.viewpager_fragment_top_headlines)
    ViewPager viewPager;

    public static TopHeadlinesFragment newInstance() {
        return new TopHeadlinesFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setAdapter(new TabPageAdapter(getChildFragmentManager(), createTabFragments()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_top_headlines;
    }

    /**
     * @return A list of news feed fragments which will serve as tabbed navigation.
     */
    private List<BaseFragment> createTabFragments() {
        List<BaseFragment> list = new ArrayList<>();

        BusinessFeedFragment businessFeedFragment = BusinessFeedFragment.newInstance();
        list.add(businessFeedFragment);
        new BusinessFeedPresenter(businessFeedFragment);

        HealthFeedFragment healthFeedFragment = HealthFeedFragment.newInstance();
        list.add(healthFeedFragment);
        new HealthFeedPresenter(healthFeedFragment);

        ScienceFeedFragment scienceFeedFragment = ScienceFeedFragment.newInstance();
        list.add(scienceFeedFragment);
        new ScienceFeedPresenter(scienceFeedFragment);

        TechnologyFeedFragment technologyFeedFragment = TechnologyFeedFragment.newInstance();
        list.add(technologyFeedFragment);
        new TechnologyFeedPresenter(technologyFeedFragment);

        SportsFeedFragment sportsFeedFragment = SportsFeedFragment.newInstance();
        list.add(sportsFeedFragment);
        new SportsFeedPresenter(sportsFeedFragment);

        EntertainmentFeedFragment entertainmentFeedFragment = EntertainmentFeedFragment.newInstance();
        list.add(entertainmentFeedFragment);
        new EntertainmentFeedPresenter(entertainmentFeedFragment);

        return list;
    }
}
