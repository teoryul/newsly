package com.teoryul.newsly.network.interceptor;

import com.teoryul.newsly.BuildConfig;
import com.teoryul.newsly.network.utils.ApiConstants;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class RequestInterceptor implements Interceptor {

    @Inject
    public RequestInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request newRequest = originalRequest.newBuilder()
                .addHeader(ApiConstants.HEADER_KEY_ACCEPT, ApiConstants.HEADER_VALUE_ACCEPT_JSON)
                .addHeader(ApiConstants.HEADER_KEY_ACCEPT_CHARSET, ApiConstants.HEADER_VALUE_ACCEPT_CHARSET_UTF8)
                .addHeader(ApiConstants.HEADER_KEY_AUTHORIZATION, BuildConfig.API_KEY)
                .method(originalRequest.method(), null)
                .build();

        return chain.proceed(newRequest);
    }
}
