package com.github.skart1.SimpleRecyclerViewDivider;

import android.graphics.drawable.Drawable;
import android.view.View;

public interface DividerProvider {
    Drawable getDividerDrawableByResource(View parent, int resourceId);
}
