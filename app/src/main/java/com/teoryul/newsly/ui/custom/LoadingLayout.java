package com.teoryul.newsly.ui.custom;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.teoryul.newsly.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Custom loading view with status text, progress bar and retry button.
 */
public class LoadingLayout extends FrameLayout {

    /**
     * State enum with four possible states.
     */
    public enum State {
        LOADING, MESSAGE, DISABLED
    }

    @BindView(R.id.txt_loading_view)
    TextView text;
    @BindView(R.id.progress_bar_loading_view)
    ProgressBar progressBar;

    private Unbinder unbinder;

    public LoadingLayout(Context context) {
        super(context);
        init(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }

    private void init(Context context) {
        View view = inflate(context, R.layout.loading_view, this);
        unbinder = ButterKnife.bind(this, view);
    }

    /**
     * Sets the text of the TextView.
     *
     * @param text
     */
    private void setText(String text) {
        this.text.setText(text);
    }

    /**
     * Changes the visibility of each view according to the provided state.
     *
     * @param state
     */
    private void setState(State state) {
        switch (state) {

            case LOADING:
                text.setVisibility(GONE);
                progressBar.setVisibility(VISIBLE);
                break;

            case MESSAGE:
                text.setVisibility(VISIBLE);
                progressBar.setVisibility(GONE);
                break;

            case DISABLED:
                text.setVisibility(GONE);
                progressBar.setVisibility(GONE);
                break;

            default:
                text.setVisibility(GONE);
                progressBar.setVisibility(GONE);
        }
    }

    /**
     * Changes the visibility of the progress bar to VISIBLE. Changes the visibility of other views to GONE.
     */
    public void showLoading() {
        setState(State.LOADING);
    }

    /**
     * Sets the text of the TextView and changes its visibility to VISIBLE. Changes the visibility of other views to GONE.
     *
     * @param textMessage
     */
    public void showLoadingMessage(String textMessage) {
        setText(textMessage);
        setState(State.MESSAGE);
    }

    /**
     * Changes the visibility of all views to GONE.
     */
    public void hideAllViews() {
        setState(State.DISABLED);
    }
}
