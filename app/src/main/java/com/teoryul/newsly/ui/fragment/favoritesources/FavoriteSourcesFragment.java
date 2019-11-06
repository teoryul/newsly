package com.teoryul.newsly.ui.fragment.favoritesources;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.teoryul.newsly.App;
import com.teoryul.newsly.R;
import com.teoryul.newsly.adapter.FavoriteSourcesAdapter;
import com.teoryul.newsly.callback.OnRecyclerViewItemClickListener;
import com.teoryul.newsly.persistence.model.SourcePersist;
import com.teoryul.newsly.presenter.BaseFavoriteContract;
import com.teoryul.newsly.presenter.favoritesourcefeed.FavoriteSourceFeedPresenter;
import com.teoryul.newsly.presenter.favoritesources.FavoriteSourcesContract;
import com.teoryul.newsly.ui.custom.GridSpacingItemDecoration;
import com.teoryul.newsly.ui.fragment.BaseFavoriteFragment;
import com.teoryul.newsly.ui.fragment.favoritesourcefeed.FavoriteSourceFeedFragment;
import com.teoryul.newsly.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FavoriteSourcesFragment extends BaseFavoriteFragment
        implements FavoriteSourcesContract.View, View.OnClickListener,
        DialogInterface.OnClickListener, DialogInterface.OnMultiChoiceClickListener, OnRecyclerViewItemClickListener<SourcePersist> {

    @BindView(R.id.fab)
    FloatingActionButton fabAdd;

    private FavoriteSourcesContract.Presenter presenter;
    private FavoriteSourcesAdapter favoriteSourcesAdapter;

    public static FavoriteSourcesFragment newInstance() {
        return new FavoriteSourcesFragment();
    }

    @Override
    public void setPresenter(BaseFavoriteContract.Presenter presenter) {
        this.presenter = (FavoriteSourcesContract.Presenter) presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabAdd.setImageResource(R.drawable.ic_add_white);
        fabAdd.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_favorite_topics_sources;
    }

    @Override
    public String getFragmentTag() {
        return App.getInstance().getString(R.string.title_favorite_sources);
    }

    @Override
    protected void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), AppConstants.RECYCLER_VIEW_GRID_LAYOUT_SPAN_COUNT));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(AppConstants.RECYCLER_VIEW_GRID_LAYOUT_SPAN_COUNT,
                getResources().getDimensionPixelSize(R.dimen.favorite_topic_card_item_size)));
    }

    @Override
    protected void initRecyclerViewAdapter() {
        favoriteSourcesAdapter = new FavoriteSourcesAdapter(this);
        recyclerView.setAdapter(favoriteSourcesAdapter);
    }

    /**
     * Handles fab click events.
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (R.id.fab == view.getId()) {
            presenter.processFabAddSourceClick();
        }
    }

    /**
     * Handles recycler view source click events.
     *
     * @param item
     */
    @Override
    public void onRecyclerViewItemClick(SourcePersist item) {
        presenter.processSourceClick(item);
    }

    @Override
    public void setFabAddSourceState(boolean isEnabled) {
        fabAdd.setEnabled(isEnabled);
    }

    @Override
    public void updateRecyclerView(List<SourcePersist> result) {
        favoriteSourcesAdapter.setData(result);
    }

    @Override
    public void showMultiChoiceDialog(String[] sources, boolean[] checkedItems) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity())
                .setTitle(R.string.title_dialog_add_sources)
                .setMultiChoiceItems(sources, checkedItems, this)
                .setPositiveButton(R.string.btn_ok, this)
                .setNegativeButton(R.string.btn_cancel, this)
                .setNeutralButton(R.string.btn_clear_all, this);
        builder.create().show();
    }

    /**
     * Handles alert dialog button click events.
     *
     * @param dialog
     * @param which
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {

            case DialogInterface.BUTTON_POSITIVE:
                dialog.dismiss();
                presenter.onDialogPositiveButtonClick();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                dialog.cancel();
                presenter.onDialogNegativeButtonClick();
                break;

            case DialogInterface.BUTTON_NEUTRAL:
                dialog.dismiss();
                presenter.onDialogNeutralButtonClick();
                break;
        }
    }

    /**
     * Handles alert dialog item click events.
     *
     * @param dialog
     * @param position
     * @param isChecked
     */
    @Override
    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
        presenter.onDialogItemClick(position);
    }

    @Override
    public void loadSourceFeedFragment(SourcePersist source) {
        FavoriteSourceFeedFragment fragment = FavoriteSourceFeedFragment.newInstance();
        new FavoriteSourceFeedPresenter(fragment, source);
        replaceFragment(fragment, true, null);
    }

    @Override
    public void setCanClickOnRecyclerViewItems(boolean canClick) {
        if (favoriteSourcesAdapter != null) {
            favoriteSourcesAdapter.setCanClick(canClick);
        }
    }

    @Override
    public void clearRecyclerView() {
        favoriteSourcesAdapter.setData(new ArrayList<>());
    }
}
