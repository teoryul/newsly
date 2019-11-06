package com.teoryul.newsly.dagger.module;

import android.app.Application;

import com.readystatesoftware.chuck.ChuckInterceptor;
import com.teoryul.newsly.BuildConfig;
import com.teoryul.newsly.network.interceptor.RequestInterceptor;
import com.teoryul.newsly.network.retrofit.NewsApi;
import com.teoryul.newsly.network.utils.ApiConstants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class NetworkModule {

    @Singleton
    @Provides
    static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Singleton
    @Provides
    static ChuckInterceptor provideChuckInterceptor(Application application) {
        return new ChuckInterceptor(application);
    }

    @Singleton
    @Provides
    static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor,
                                            ChuckInterceptor chuckInterceptor,
                                            RequestInterceptor requestInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(chuckInterceptor)
                .addInterceptor(requestInterceptor)
                .connectTimeout(ApiConstants.OK_HTTP_CLIENT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(ApiConstants.OK_HTTP_CLIENT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(ApiConstants.OK_HTTP_CLIENT_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    static NewsApi provideNewsApi(Retrofit retrofit) {
        return retrofit.create(NewsApi.class);
    }
}
