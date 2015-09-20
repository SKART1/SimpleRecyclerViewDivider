package com.github.skart1.SimpleRecyclerViewDivider.utils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

public class DimensionCalculator {
    public Rect getMargins(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
            return this.getMarginRect(marginLayoutParams);
        } else {
            return new Rect();
        }
    }

    private Rect getMarginRect(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return new Rect(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
    }
}
