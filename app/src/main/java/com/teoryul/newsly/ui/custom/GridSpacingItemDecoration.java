package com.teoryul.newsly.ui.custom;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int spanCount;
    private final float itemSize;

    public GridSpacingItemDecoration(int spanCount, int itemSize) {
        this.spanCount = spanCount;
        this.itemSize = itemSize;
    }

    @Override
    public void getItemOffsets(final Rect outRect, final View view, RecyclerView parent, RecyclerView.State state) {
        final int position = parent.getChildLayoutPosition(view);
        final int column = position % spanCount;
        final int parentWidth = parent.getWidth();
        int spacing = (int) (parentWidth - (itemSize * spanCount)) / (spanCount + 1);

        outRect.left = spacing - column * spacing / spanCount;
        outRect.right = (column + 1) * spacing / spanCount;
        if (position < spanCount) {
            outRect.top = spacing;
        }
        outRect.bottom = spacing;
    }
}
