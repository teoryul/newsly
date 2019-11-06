package com.teoryul.newsly.presenter;

import com.teoryul.newsly.App;
import com.teoryul.newsly.dagger.component.AppComponent;
import com.teoryul.newsly.utils.RxUtils;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public abstract class BasePresenter implements IBasePresenter {

    private CompositeDisposable compositeDisposable;
    private final AppComponent appComponent;

    public BasePresenter() {
        this.appComponent = App.getInstance().getAppComponent();
        inject();
    }

    @Override
    public void onDestroy() {
        getCompositeDisposable().dispose();
    }

    protected final <T> void subscribeSingle(Single<T> observable, Consumer<T> onSuccess, Consumer<Throwable> onError) {
        getCompositeDisposable().add(observable.compose(RxUtils.applySingleSchedulers()).subscribe(onSuccess, onError));
    }

    private CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    protected abstract void inject();

    protected final AppComponent getAppComponent() {
        return appComponent;
    }
}
