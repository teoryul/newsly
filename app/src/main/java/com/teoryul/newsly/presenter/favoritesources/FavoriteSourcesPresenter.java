package com.teoryul.newsly.presenter.favoritesources;

import android.text.TextUtils;
import android.util.Log;

import com.teoryul.newsly.R;
import com.teoryul.newsly.network.response.NewsPublishersResponse;
import com.teoryul.newsly.network.service.NewsApiService;
import com.teoryul.newsly.network.utils.NetworkUtils;
import com.teoryul.newsly.persistence.model.SourcePersist;
import com.teoryul.newsly.persistence.service.NewsDBService;
import com.teoryul.newsly.presenter.BasePresenter;
import com.teoryul.newsly.utils.InternetConnectivityHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FavoriteSourcesPresenter extends BasePresenter
        implements FavoriteSourcesContract.Presenter {

    private final FavoriteSourcesContract.View view;
    private final List<SourcePersist> sources;
    private String[] dialogSources = null;
    private boolean[] dialogCheckedItems = null;

    @Inject
    NewsApiService newsApiService;
    @Inject
    NewsDBService newsDBService;

    public FavoriteSourcesPresenter(FavoriteSourcesContract.View view) {
        super();
        this.view = view;
        this.view.setPresenter(this);
        this.sources = new ArrayList<>();
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onCreate() {
        // Not used
    }

    @Override
    public void onResume() {
        view.showProgressBar();
        view.setFabAddSourceState(false);
        view.setCanClickOnRecyclerViewItems(false);
        subscribeSingle(
                newsDBService.getSourceDBService().getAllSources(),
                this::onQuerySuccess,
                this::onQueryError);
    }

    @Override
    public void processFabAddSourceClick() {
        if (sources.isEmpty()) {
            view.showProgressBar();
            requestDataFromApi();
            return;
        }

        view.setFabAddSourceState(false);
        prepareSourcesForAlertDialog();
        view.showMultiChoiceDialog(dialogSources, dialogCheckedItems);
    }

    /**
     * Extracts each news source's name and favorited state into a separate arrays
     * which will be passed in to the multiple choice alert dialog.
     * <p>
     * Also makes a backup copy of the current state in case the user dismisses the dialog.
     */
    private void prepareSourcesForAlertDialog() {
        dialogSources = new String[sources.size()];
        dialogCheckedItems = new boolean[sources.size()];

        for (int i = 0; i < sources.size(); i++) {
            dialogSources[i] = concatSourceNameAndLanguage(sources.get(i));
            dialogCheckedItems[i] = sources.get(i).isFavorite();
        }
    }

    private String concatSourceNameAndLanguage(SourcePersist source) {
        return TextUtils.concat(source.getName(), " (", source.getLanguage().toUpperCase(), ")").toString();
    }

    /**
     * Update the isFavorite boolean flag for the {@link #sources} list item at this position.
     *
     * @param position
     */
    @Override
    public void onDialogItemClick(int position) {
        sources.get(position).setFavorite(!sources.get(position).isFavorite());
    }

    /**
     * Apply any changes made to the {@link #sources} list to the db.
     */
    @Override
    public void onDialogPositiveButtonClick() {
        view.showProgressBar();
        newsDBService.getSourceDBService().insertSourcesWithReplaceStrategy(sources);
        setViewWithFavoriteSourcesState();
    }

    @Override
    public void onDialogNegativeButtonClick() {
        view.setFabAddSourceState(true);
    }

    /**
     * Clear all favorite sources.
     */
    @Override
    public void onDialogNeutralButtonClick() {
        for (SourcePersist source : sources) {
            source.setFavorite(false);
        }
        onDialogPositiveButtonClick();
    }

    @Override
    public void processSourceClick(SourcePersist source) {
        if (!InternetConnectivityHelper.isInternetConnectionAvailable()) {
            view.showMessageDialog(R.string.title_dialog_error, R.string.check_network_conn_msg);
            view.setCanClickOnRecyclerViewItems(true);
            return;
        }
        view.loadSourceFeedFragment(source);
    }

    private void onQuerySuccess(List<SourcePersist> result) {
        if (result == null || result.isEmpty()) {
            requestDataFromApi();
            return;
        }

        if (!sources.isEmpty()) {
            sources.clear();
        }
        sources.addAll(result);

        setViewWithFavoriteSourcesState();
    }

    private void onQueryError(Throwable throwable) {
        Log.e("Failed room query", throwable.getMessage());
        requestDataFromApi();
    }

    private void requestDataFromApi() {
        subscribeSingle(
                newsApiService.getNewsPublishersResponse(),
                this::onRequestSuccess,
                this::onRequestError);
    }

    private void onRequestSuccess(NewsPublishersResponse result) {
        if (result == null || result.getSources().isEmpty()) {
            onRequestEmpty();
            return;
        }

        if (!sources.isEmpty()) {
            sources.clear();
        }
        sources.addAll(NetworkUtils.extractNewsSources(result));
        newsDBService.getSourceDBService().insertSourcesWithReplaceStrategy(sources);

        setViewDefaultState();
    }

    private void onRequestEmpty() {
        setViewDefaultState();
        view.showMessageDialog(R.string.title_dialog_error, R.string.no_available_news_sources);
    }

    private void onRequestError(Throwable throwable) {
        Log.e("Failed api request", throwable.getMessage());
        setViewDefaultState();
        view.showMessageDialog(R.string.title_dialog_error, R.string.check_network_conn_msg);
    }

    private void setViewDefaultState() {
        view.clearRecyclerView();
        view.setCanClickOnRecyclerViewItems(true);
        view.setFabAddSourceState(true);
        view.showLoadingMessage(R.string.add_favorite_sources_msg);
    }

    private void setViewWithFavoriteSourcesState() {
        List<SourcePersist> favoriteSources = extractFavoriteSources(sources);

        if (favoriteSources.isEmpty()) {
            setViewDefaultState();
            return;
        }

        view.hideAllStates();
        view.updateRecyclerView(favoriteSources);
        view.setCanClickOnRecyclerViewItems(true);
        view.setFabAddSourceState(true);
    }

    private List<SourcePersist> extractFavoriteSources(List<SourcePersist> sources) {
        List<SourcePersist> favoriteSources = new ArrayList<>();
        for (SourcePersist source : sources) {
            if (source.isFavorite()) {
                favoriteSources.add(source);
            }
        }
        return favoriteSources;
    }
}
