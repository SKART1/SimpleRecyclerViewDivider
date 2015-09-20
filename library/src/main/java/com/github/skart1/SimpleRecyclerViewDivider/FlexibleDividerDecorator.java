package com.github.skart1.SimpleRecyclerViewDivider;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.skart1.SimpleRecyclerViewDivider.utils.DimensionCalculator;

public class FlexibleDividerDecorator extends RecyclerView.ItemDecoration {
    SimpleDividerAdapter _simpleDividerAdapter;
    DimensionCalculator _dimensionCalculator;
    DividerProvider _dividerProvider;


    public FlexibleDividerDecorator(SimpleDividerAdapter simpleDividerAdapter, DividerProvider dividerProvider) {
        _simpleDividerAdapter = simpleDividerAdapter;

        _dimensionCalculator = new DimensionCalculator();
        _dividerProvider = dividerProvider;
    }

    /**
     * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
     * the number of pixels that the item view should be inset by, similar to padding or margin.
     * The default implementation sets the bounds of outRect to 0 and returns.
     * <p/>
     * <p/>
     * If this ItemDecoration does not affect the positioning of item views, it should set
     * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
     * before returning.
     * <p/>
     * <p/>
     * If you need to access Adapter for additional data, you can call
     * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
     * View.
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == RecyclerView.NO_POSITION) {
            return;
        }


        WhatToDrawStructure whatToDrawStructure = _simpleDividerAdapter.whatToDrawForPosition(itemPosition);
        if(whatToDrawStructure.drawAbove){
            Drawable headerDivider = _dividerProvider.getDividerDrawableByResource(parent, whatToDrawStructure.aboveItemResourceId);

            if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                outRect.top = headerDivider.getIntrinsicHeight();
            } else {
                outRect.left = headerDivider.getIntrinsicWidth();
            }

        }

        if(whatToDrawStructure.drawBelow) {
            Drawable bottomDivider = _dividerProvider.getDividerDrawableByResource(parent, whatToDrawStructure.belowItemResourceId);

            if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                outRect.bottom = bottomDivider.getIntrinsicHeight();
            } else {
                outRect.right = bottomDivider.getIntrinsicWidth();
            }
        }
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        View childView;
        RecyclerView.ViewHolder childViewHolder;

        int position;
        WhatToDrawStructure whatToDrawStructure;
        int left, right, top, bottom;
        int orientation = getOrientation(parent);
        int fullWidth = parent.getWidth();

        if (parent.getChildCount() <= 0 || _simpleDividerAdapter.getItemCount() <= 0) {
            return;
        }

        for (int i = 0; i < parent.getChildCount(); i++) {
            childView = parent.getChildAt(i);
            position = parent.getChildAdapterPosition(childView);
            childViewHolder = parent.findViewHolderForAdapterPosition(position);

            if (position == RecyclerView.NO_POSITION) {
                continue;
            }

            whatToDrawStructure = _simpleDividerAdapter.whatToDrawForPosition(position);
            //Above
            if (whatToDrawStructure.drawAbove) {
                Drawable headerDivider = _dividerProvider.getDividerDrawableByResource(parent, whatToDrawStructure.aboveItemResourceId);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();

                if (orientation == LinearLayoutManager.VERTICAL) {
                    Rect rect = _simpleDividerAdapter.evaluateMarginsRect(position, childViewHolder, true, false, whatToDrawStructure.aboveItemResourceId);
                    bottom = childView.getTop() - params.topMargin;
                    top = bottom - headerDivider.getIntrinsicHeight();
                    right = fullWidth + rect.right;
                    left  = rect.left;
                } else { //horizontal
                    Rect rect = _simpleDividerAdapter.evaluateMarginsRect(position, childViewHolder, true, false, whatToDrawStructure.aboveItemResourceId);
                    bottom = childView.getWidth() - params.leftMargin;
                    top = bottom - headerDivider.getIntrinsicHeight();
                    right = fullWidth + rect.right;
                    left  = rect.left;
                }

                headerDivider.setBounds(left, top, right, bottom);
                headerDivider.draw(canvas);
            }

            //Below
            if (whatToDrawStructure.drawBelow) {
                Drawable bottomDivider = _dividerProvider.getDividerDrawableByResource(parent, whatToDrawStructure.belowItemResourceId);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();

                if (orientation == LinearLayoutManager.VERTICAL) {
                    Rect rect = _simpleDividerAdapter.evaluateMarginsRect(position, childViewHolder, false, true, whatToDrawStructure.aboveItemResourceId);
                    bottom = childView.getBottom() - params.bottomMargin;
                    top = bottom - bottomDivider.getIntrinsicHeight();
                    right = fullWidth + rect.right;
                    left  = rect.left;
                } else { //horizontal
                    Rect rect = _simpleDividerAdapter.evaluateMarginsRect(position, childViewHolder, false, true, whatToDrawStructure.aboveItemResourceId);
                    bottom = childView.getWidth() - params.leftMargin;
                    top = bottom - bottomDivider.getIntrinsicHeight();
                    right = fullWidth + rect.right;
                    left  = rect.left;
                }

                bottomDivider.setBounds(left, top, right, bottom);
                bottomDivider.draw(canvas);
            }
        }
    }


    private int getOrientation(RecyclerView parent) {
        if (!(parent.getLayoutManager() instanceof LinearLayoutManager))
            throw new IllegalStateException("Layout manager must be an instance of LinearLayoutManager");
        return ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
    }


    /**
     * Structure to pass what to draw
     *
     */
    public static class WhatToDrawStructure {
        public boolean drawBelow;
        public boolean drawAbove;

        public int belowItemResourceId;
        public int aboveItemResourceId;
    }
}
