package com.teoryul.newsly.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.teoryul.newsly.ui.fragment.BaseFragment;

import java.util.List;

public class TabPageAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> tabFragments;

    public TabPageAdapter(FragmentManager fManager, List<BaseFragment> tabFragments) {
        super(fManager);
        this.tabFragments = tabFragments;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabFragments.get(position).getFragmentTag();
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position);
    }

    @Override
    public int getCount() {
        return tabFragments.size();
    }
}
