package com.teoryul.newsly;

import android.app.Application;
import android.preference.PreferenceManager;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.teoryul.newsly.dagger.component.AppComponent;
import com.teoryul.newsly.dagger.component.DaggerAppComponent;
import com.teoryul.newsly.dagger.module.AppModule;
import com.teoryul.newsly.dagger.module.RoomModule;

public class App extends Application {

    private static App instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setUp();
    }

    private void setUp() {
        setUpSettingsDefaultValues();
        setUpPicassoInstance();
    }

    private void setUpSettingsDefaultValues() {
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    private void setUpPicassoInstance() {
        Picasso.setSingletonInstance(
                new Picasso.Builder(this)
                        .downloader(new OkHttp3Downloader(this, Integer.MAX_VALUE))
                        .memoryCache(new LruCache(this))
                        .build());
    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .roomModule(new RoomModule(this))
                    .build();
        }
        return appComponent;
    }
}
