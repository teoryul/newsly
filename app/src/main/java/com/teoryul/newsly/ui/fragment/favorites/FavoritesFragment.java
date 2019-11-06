package com.teoryul.newsly.ui.fragment.favorites;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.teoryul.newsly.R;
import com.teoryul.newsly.adapter.TabPageAdapter;
import com.teoryul.newsly.presenter.favoritearticles.FavoriteArticlesPresenter;
import com.teoryul.newsly.presenter.favoritesources.FavoriteSourcesPresenter;
import com.teoryul.newsly.presenter.favoritetopics.FavoriteTopicsPresenter;
import com.teoryul.newsly.ui.fragment.BaseFragment;
import com.teoryul.newsly.ui.fragment.favoritearticles.FavoriteArticlesFragment;
import com.teoryul.newsly.ui.fragment.favoritesources.FavoriteSourcesFragment;
import com.teoryul.newsly.ui.fragment.favoritetopics.FavoriteTopicsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FavoritesFragment extends BaseFragment {

    @BindView(R.id.tab_layout_favorites)
    TabLayout tabLayout;
    @BindView(R.id.viewpager_fragment_favorites)
    ViewPager viewPager;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setAdapter(new TabPageAdapter(getChildFragmentManager(), createTabFragments()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onResume() {
        super.onResume();
        setActivityTitle();
        setActionBarBackButtonState(false);
    }

    /**
     * Resets this fragments title when the user returns to it after closing
     * an opened favorite topic or source feed fragment,
     * both if which change the title according to the searched topic or source.
     */
    private void setActivityTitle() {
        getBaseActivity().setTitle(getString(R.string.title_main_nav_tab_favorites));
    }

    /**
     * Removes the back button arrow from the action bar when the user returns to this fragment
     * after closing a favorite topic or source feed fragment, both of which enable it.
     *
     * @param isEnabled
     */
    private void setActionBarBackButtonState(boolean isEnabled) {
        if (getBaseActivity().getSupportActionBar() != null) {
            getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(isEnabled);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_favorites;
    }

    private List<BaseFragment> createTabFragments() {
        List<BaseFragment> list = new ArrayList<>();

        FavoriteTopicsFragment favoriteTopicsFragment = FavoriteTopicsFragment.newInstance();
        list.add(favoriteTopicsFragment);
        new FavoriteTopicsPresenter(favoriteTopicsFragment);

        FavoriteSourcesFragment favoriteSourcesFragment = FavoriteSourcesFragment.newInstance();
        list.add(favoriteSourcesFragment);
        new FavoriteSourcesPresenter(favoriteSourcesFragment);

        FavoriteArticlesFragment favoriteArticlesFragment = FavoriteArticlesFragment.newInstance();
        list.add(favoriteArticlesFragment);
        new FavoriteArticlesPresenter(favoriteArticlesFragment);

        return list;
    }
}
