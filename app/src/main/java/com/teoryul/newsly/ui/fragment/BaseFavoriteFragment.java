package com.teoryul.newsly.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.teoryul.newsly.R;
import com.teoryul.newsly.presenter.BaseFavoriteContract;
import com.teoryul.newsly.ui.custom.LoadingLayout;

import butterknife.BindView;

public abstract class BaseFavoriteFragment extends BaseFragment
        implements BaseFavoriteContract.View {

    @BindView(R.id.loading_view)
    LoadingLayout loadingLayout;
    @BindView(R.id.recycler_view_favorites)
    protected RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initRecyclerViewAdapter();
    }

    protected abstract void initRecyclerView();

    protected abstract void initRecyclerViewAdapter();

    /**
     * Displays a progress bar. See {@link LoadingLayout}.
     */
    @Override
    public void showProgressBar() {
        if (isAdded() && loadingLayout != null) {
            loadingLayout.showLoading();
        }
    }

    /**
     * Displays text when there is no loaded content. See {@link LoadingLayout}.
     *
     * @param messageId The text to display
     */
    @Override
    public void showLoadingMessage(int messageId) {
        if (isAdded() && loadingLayout != null) {
            loadingLayout.showLoadingMessage(getString(messageId));
        }
    }

    /**
     * Hides any displayed view from {@link LoadingLayout}.
     */
    @Override
    public void hideAllStates() {
        if (isAdded() && loadingLayout != null) {
            loadingLayout.hideAllViews();
        }
    }

    @Override
    public void showMessageDialog(int titleId, int messageId) {
        if (isAdded()) {
            new AlertDialog.Builder(getBaseActivity())
                    .setTitle(getString(titleId))
                    .setMessage(getString(messageId))
                    .setPositiveButton(R.string.btn_ok,
                            (dialog, which) -> dialog.cancel())
                    .show();
        }
    }
}
