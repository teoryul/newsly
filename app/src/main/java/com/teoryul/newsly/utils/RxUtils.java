package com.teoryul.newsly.utils;

import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class RxUtils {

    public static <T> SingleTransformer<T, T> applySingleSchedulers() {
        return upstream -> upstream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
