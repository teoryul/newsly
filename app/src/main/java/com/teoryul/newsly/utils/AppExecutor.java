package com.teoryul.newsly.utils;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

@Singleton
public class AppExecutor {

    private Executor executor;

    public AppExecutor(Executor executor) {
        this.executor = executor;
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
