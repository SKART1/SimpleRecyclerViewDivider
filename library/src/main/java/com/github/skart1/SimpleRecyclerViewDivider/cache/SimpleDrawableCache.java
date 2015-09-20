package com.github.skart1.SimpleRecyclerViewDivider.cache;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.github.skart1.SimpleRecyclerViewDivider.DividerProvider;

import java.util.HashMap;

public class SimpleDrawableCache implements DividerProvider {
    private DividerProvider _dividerProvider;
    private HashMap<Integer, Drawable> drawableHashMap;

    public SimpleDrawableCache(DividerProvider dividerProvider) {
        _dividerProvider = dividerProvider;
        this.drawableHashMap = new HashMap<>();
    }

    @Override
    public Drawable getDividerDrawableByResource(View parent, int resourceId) {
        if(!drawableHashMap.containsKey(resourceId)) {
            drawableHashMap.put(resourceId, _dividerProvider.getDividerDrawableByResource(parent, resourceId));
        }
        return drawableHashMap.get(resourceId);
    }
}
