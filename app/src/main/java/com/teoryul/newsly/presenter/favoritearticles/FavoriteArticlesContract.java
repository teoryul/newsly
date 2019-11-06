package com.teoryul.newsly.presenter.favoritearticles;

import com.teoryul.newsly.persistence.model.ArticlePersist;
import com.teoryul.newsly.presenter.BaseFavoriteContract;

import java.util.List;

public interface FavoriteArticlesContract extends BaseFavoriteContract {

    interface View extends BaseFavoriteContract.View {

        void updateRecyclerView(List<ArticlePersist> result);

        void startActivityNewsArticle(ArticlePersist article);
    }

    interface Presenter extends BaseFavoriteContract.Presenter {

        void processArticleClick(ArticlePersist article);
    }
}
