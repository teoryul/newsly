package com.teoryul.newsly.ui.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import com.teoryul.newsly.R;
import com.teoryul.newsly.presenter.personalized.PersonalizedFeedPresenter;
import com.teoryul.newsly.ui.activity.BaseActivity;
import com.teoryul.newsly.ui.fragment.favorites.FavoritesFragment;
import com.teoryul.newsly.ui.fragment.personalized.PersonalizedFeedFragment;
import com.teoryul.newsly.ui.fragment.settings.SettingsFragment;
import com.teoryul.newsly.ui.fragment.topheadlines.TopHeadlinesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadPersonalizedFeedFragment();
    }

    /**
     * Loads a personalized news feed fragment as default when this activity is created
     * and each time its called by the bottom navigation.
     */
    private void loadPersonalizedFeedFragment() {
        setTitle(R.string.title_main_nav_tab_for_you);
        PersonalizedFeedFragment fragment = PersonalizedFeedFragment.newInstance();
        new PersonalizedFeedPresenter(fragment);
        replaceFragment(fragment, false, null);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = menuItem -> {
        Fragment fragment;
        switch (menuItem.getItemId()) {

            case R.id.navigation_your_feed:
                loadPersonalizedFeedFragment();
                return true;

            case R.id.navigation_top_headlines:
                setTitle(R.string.title_main_nav_tab_top_headlines);
                fragment = TopHeadlinesFragment.newInstance();
                replaceFragment(fragment, false, null);
                return true;

            case R.id.navigation_favorites:
                setTitle(R.string.title_main_nav_tab_favorites);
                replaceFragment(FavoritesFragment.newInstance(), false, null);
                return true;

            case R.id.navigation_settings:
                setTitle(R.string.title_main_nav_tab_settings);
                replaceFragment(SettingsFragment.newInstance(), false, null);
                return true;

            default:
                return false;
        }
    };
}
