// Copyright 2022-2025 Woohyun Shin (sinusinu)
// SPDX-License-Identifier: GPL-3.0-only

package com.sinu.molla;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppItemListSelectAdapter extends RecyclerView.Adapter<AppItemListSelectAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<AppItem> list;
    private final ArrayList<AppItem> selectedList;

    private Drawable drawableGeneric;

    private final View.OnClickListener itemClickListener;

    public AppItemListSelectAdapter(Context context, ArrayList<AppItem> list, ArrayList<AppItem> selectedList, View.OnClickListener itemClickListener, boolean simple) {
        this.list = list;
        this.selectedList = selectedList;
        this.context = context;

        drawableGeneric = ContextCompat.getDrawable(context, simple ? R.drawable.generic_simple : R.drawable.generic);

        this.itemClickListener = itemClickListener;
    }

    public void SetSimpleBackground(boolean simple) {
        drawableGeneric = ContextCompat.getDrawable(context, simple ? R.drawable.generic_simple : R.drawable.generic);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnFocusChangeListener {
        public final ImageView ivBanner;
        public final ImageView ivIcon;
        public final TextView tvAppName;
        public final CheckBox cbCheck;
        private final Context context;

        public boolean focused = false;

        public ViewHolder(@NonNull View v, Context context) {
            super(v);
            this.context = context;

            ivBanner = v.findViewById(R.id.iv_appitem_list_select_banner);
            ivIcon = v.findViewById(R.id.iv_appitem_list_select_icon);
            tvAppName = v.findViewById(R.id.tv_appitem_list_select_app_name);
            cbCheck = v.findViewById(R.id.cb_appitem_list_select_check);

            v.setOnClickListener(itemClickListener);
            v.setOnFocusChangeListener(this);
        }

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                var pref = context.getSharedPreferences("com.sinu.molla.settings", Context.MODE_PRIVATE);
                if (pref.getInt("draw_white_outline", 1) == 1) {
                    view.setForeground(ContextCompat.getDrawable(context, R.drawable.outline));
                } else {
                    view.setForeground(null);
                }
            } else {
                view.setForeground(null);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_appitem_list_select, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Drawable appBanner = null;
        Drawable appIcon = null;

        var ci = AppItemIcon.getAppItemIcon((MollaApplication)context, list.get(position));
        if (ci.type == AppItemIcon.IconType.LEANBACK) {
            appBanner = ci.drawable;
        } else {
            appBanner = drawableGeneric;
            appIcon = ci.drawable;
        }
        holder.ivBanner.setImageDrawable(appBanner);
        holder.ivIcon.setImageDrawable(appIcon);
        holder.tvAppName.setText(list.get(position).customItemDisplayName == null ? list.get(position).displayName : list.get(position).customItemDisplayName);
        holder.cbCheck.setChecked(selectedList.contains(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
