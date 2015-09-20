package com.github.skart1.SimpleRecyclerView.example.Adapters;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.skart1.SimpleRecyclerView.example.R;
import com.github.skart1.SimpleRecyclerViewDivider.DividerProvider;
import com.github.skart1.SimpleRecyclerViewDivider.FlexibleDividerDecorator;
import com.github.skart1.SimpleRecyclerViewDivider.SimpleDividerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter implements SimpleDividerAdapter, DividerProvider {
    private List<RecyclerViewItem> recyclerViewItems;

    public ExampleAdapter() {
        recyclerViewItems = new ArrayList<>();

        recyclerViewItems.add(new RecyclerViewItem(RecyclerViewItem.RecyclerViewItemType.DATE_DIVIDER_ITEM, "5 SEPTEMBER"));
        recyclerViewItems.add(new RecyclerViewItem(RecyclerViewItem.RecyclerViewItemType.CHAT_ITEM, "Tom"));
        recyclerViewItems.add(new RecyclerViewItem(RecyclerViewItem.RecyclerViewItemType.CHAT_ITEM, "Jerry"));

        recyclerViewItems.add(new RecyclerViewItem(RecyclerViewItem.RecyclerViewItemType.DATE_DIVIDER_ITEM, "2 SEPTEMBER"));
        recyclerViewItems.add(new RecyclerViewItem(RecyclerViewItem.RecyclerViewItemType.CHAT_ITEM, "Winnie"));
        recyclerViewItems.add(new RecyclerViewItem(RecyclerViewItem.RecyclerViewItemType.CHAT_ITEM, "Christopher"));
        recyclerViewItems.add(new RecyclerViewItem(RecyclerViewItem.RecyclerViewItemType.CHAT_ITEM, "Piglet"));

        recyclerViewItems.add(new RecyclerViewItem(RecyclerViewItem.RecyclerViewItemType.DATE_DIVIDER_ITEM, "1 SEPTEMBER"));
        recyclerViewItems.add(new RecyclerViewItem(RecyclerViewItem.RecyclerViewItemType.CHAT_ITEM, "Kenny"));
        recyclerViewItems.add(new RecyclerViewItem(RecyclerViewItem.RecyclerViewItemType.CHAT_ITEM, "Stan"));
    }


    @Override
    public int getItemViewType(int position) {
        RecyclerViewItem recyclerViewItem = recyclerViewItems.get(position);
        if (recyclerViewItem.isDateDivider()) {
            return R.layout.recyclerview_date_divider_item;
        } else {
            return R.layout.recyclerview_chat_item;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == R.layout.recyclerview_chat_item) {
            RecyclerView.ViewHolder viewHolder = new ChatItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_chat_item, viewGroup, false));
            return viewHolder;
        } else {
            RecyclerView.ViewHolder viewHolder = new DateDividerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_date_divider_item, viewGroup, false));
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        RecyclerViewItem recyclerViewItem = recyclerViewItems.get(position);

        if (recyclerViewItem.getType() == RecyclerViewItem.RecyclerViewItemType.DATE_DIVIDER_ITEM) {
            DateDividerViewHolder dateDividerViewHolder = (DateDividerViewHolder) viewHolder;
            dateDividerViewHolder.date.setText(recyclerViewItem.getText());
        } else {
            ChatItemViewHolder chatItemViewHolder = (ChatItemViewHolder) viewHolder;
            chatItemViewHolder.nickname.setText(recyclerViewItem.getText());
        }
    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }

//<editor-fold desc="Dividers">
    @Override
    public FlexibleDividerDecorator.WhatToDrawStructure whatToDrawForPosition(int itemPosition) {
        FlexibleDividerDecorator.WhatToDrawStructure whatToDrawStructure = new FlexibleDividerDecorator.WhatToDrawStructure();
        RecyclerViewItem dialogsListItem = recyclerViewItems.get(itemPosition);

        boolean isFirst = itemPosition == 0;
        boolean isLast = itemPosition == (recyclerViewItems.size() - 1);
        boolean isSection = dialogsListItem.isDateDivider();
        boolean isSectionAbove = false;
        boolean isSectionBelow = false;
        boolean isDialog = !dialogsListItem.isDateDivider();

        if (itemPosition > 0 && itemPosition < (recyclerViewItems.size())) {
            isSectionAbove = recyclerViewItems.get(itemPosition - 1).isDateDivider();
        }
        if (itemPosition + 1 < (recyclerViewItems.size())) {
            isSectionBelow = recyclerViewItems.get(itemPosition + 1).isDateDivider();
        }


        whatToDrawStructure.drawAbove = (!isFirst && !isLast &&  isSection && !isDialog)  && !isSectionAbove;
        whatToDrawStructure.drawBelow = ((!isFirst && !isLast && !isSection &&  isDialog) || (!isFirst && isLast && !isSection && isDialog)) && !isSectionBelow;


        whatToDrawStructure.aboveItemResourceId = R.drawable.layout_divider;
        whatToDrawStructure.belowItemResourceId = R.drawable.layout_divider;

        return whatToDrawStructure;
    }

    @Override
    public Rect evaluateMarginsRect(int position, RecyclerView.ViewHolder childViewHolder, boolean isAbove, boolean isBelow, int itemResource) {
        RecyclerViewItem dialogsListItem = recyclerViewItems.get(position);
        Rect rect = new Rect(0, 0, 0, 0);

        if (dialogsListItem.isDateDivider()) {
            return rect;
        } else {
            boolean isLast = (position == (recyclerViewItems.size() - 1));
            if (!isLast || isAbove) {
                ChatItemViewHolder chatItemViewHolder = (ChatItemViewHolder) childViewHolder;
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) chatItemViewHolder.avatar.getLayoutParams();
                rect.left = lp.leftMargin + chatItemViewHolder.avatar.getWidth() + lp.rightMargin;
            }
            return rect;
        }
    }

    @Override
    public Drawable getDividerDrawableByResource(View parent, int resourceId) {
        return parent.getContext().getResources().getDrawable(resourceId);
    }
//</editor-fold>


    /**
     * View holder for DateDivider
     */
    public static class DateDividerViewHolder extends RecyclerView.ViewHolder {
        public TextView date;

        public DateDividerViewHolder(View itemView) {
            super(itemView);
            this.date = (TextView) itemView.findViewById(R.id.date);
        }
    }


    /**
     * View holder for ChatItem
     */
    public static class ChatItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView  nickname;

        public ChatItemViewHolder(View itemView) {
            super(itemView);
            this.nickname = (TextView) itemView.findViewById(R.id.nickname);
            this.avatar = (ImageView) itemView.findViewById(R.id.avatar);
        }
    }


    private static class RecyclerViewItem {
        RecyclerViewItemType type;
        String text;

        public RecyclerViewItem(RecyclerViewItemType type, String text) {
            this.type = type;
            this.text = text;
        }

        public RecyclerViewItemType getType() {
            return type;
        }

        public String getText() {
            return text;
        }

        public boolean isDateDivider() {
            return type == RecyclerViewItemType.DATE_DIVIDER_ITEM;
        }

        enum RecyclerViewItemType {
            DATE_DIVIDER_ITEM,
            CHAT_ITEM
        }
    }
}

