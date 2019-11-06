package com.teoryul.newsly.dagger.module;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.teoryul.newsly.utils.AppExecutor;

import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    AppExecutor provideExecutor() {
        return new AppExecutor(Executors.newSingleThreadExecutor());
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return application.getSharedPreferences(application.getPackageName(), Activity.MODE_PRIVATE);
    }
}
