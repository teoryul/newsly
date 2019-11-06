package com.teoryul.newsly.ui.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teoryul.newsly.ui.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * @return The layout id of the child fragment.
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * @return This fragment's source code class name.
     */
    public String getFragmentTag() {
        return getClass().getSimpleName();
    }

    /**
     * @return A reference to the base activity.
     */
    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    /**
     * @param fragment
     * @param shouldAddToBackStack
     * @param name                 Name for the back stack state
     */
    protected void replaceFragment(Fragment fragment, boolean shouldAddToBackStack, String name) {
        getBaseActivity().replaceFragment(fragment, shouldAddToBackStack, name);
    }

    /**
     * @param fragment
     * @param shouldAddToBackStack
     * @param name                 Name for the back stack state
     */
    protected void addFragment(Fragment fragment, boolean shouldAddToBackStack, String name) {
        getBaseActivity().addFragment(fragment, shouldAddToBackStack, name);
    }
}
