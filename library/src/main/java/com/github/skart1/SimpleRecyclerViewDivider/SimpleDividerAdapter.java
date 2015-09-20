package com.github.skart1.SimpleRecyclerViewDivider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;


public interface SimpleDividerAdapter {
    FlexibleDividerDecorator.WhatToDrawStructure whatToDrawForPosition(int itemPosition);
    Rect evaluateMarginsRect(int position, RecyclerView.ViewHolder childViewHolder, boolean isAbove, boolean isBelow, int itemResource);

    int getItemCount();
}
