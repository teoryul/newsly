package com.teoryul.newsly.dagger.component;

import com.teoryul.newsly.dagger.module.AppModule;
import com.teoryul.newsly.dagger.module.NetworkModule;
import com.teoryul.newsly.dagger.module.RoomModule;
import com.teoryul.newsly.presenter.business.BusinessFeedPresenter;
import com.teoryul.newsly.presenter.entertainment.EntertainmentFeedPresenter;
import com.teoryul.newsly.presenter.favoritearticles.FavoriteArticlesPresenter;
import com.teoryul.newsly.presenter.favoritesourcefeed.FavoriteSourceFeedPresenter;
import com.teoryul.newsly.presenter.favoritesources.FavoriteSourcesPresenter;
import com.teoryul.newsly.presenter.favoritetopicfeed.FavoriteTopicFeedPresenter;
import com.teoryul.newsly.presenter.favoritetopics.FavoriteTopicsPresenter;
import com.teoryul.newsly.presenter.health.HealthFeedPresenter;
import com.teoryul.newsly.presenter.newsarticle.NewsArticlePresenter;
import com.teoryul.newsly.presenter.personalized.PersonalizedFeedPresenter;
import com.teoryul.newsly.presenter.science.ScienceFeedPresenter;
import com.teoryul.newsly.presenter.sports.SportsFeedPresenter;
import com.teoryul.newsly.presenter.technology.TechnologyFeedPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        RoomModule.class,
        NetworkModule.class
})
public interface AppComponent {

    void inject(BusinessFeedPresenter presenter);

    void inject(EntertainmentFeedPresenter presenter);

    void inject(FavoriteArticlesPresenter presenter);

    void inject(FavoriteSourceFeedPresenter presenter);

    void inject(FavoriteSourcesPresenter presenter);

    void inject(FavoriteTopicFeedPresenter presenter);

    void inject(FavoriteTopicsPresenter presenter);

    void inject(HealthFeedPresenter presenter);

    void inject(NewsArticlePresenter presenter);

    void inject(PersonalizedFeedPresenter presenter);

    void inject(ScienceFeedPresenter presenter);

    void inject(SportsFeedPresenter presenter);

    void inject(TechnologyFeedPresenter presenter);
}
