// Copyright 2022-2025 Woohyun Shin (sinusinu)
// SPDX-License-Identifier: GPL-3.0-only

package com.sinu.molla;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppItemSingleSelectAdapter extends RecyclerView.Adapter<AppItemSingleSelectAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<AppItem> list;
    private AppItem selectedItem = null;

    private Drawable drawableGeneric;

    private final View.OnClickListener itemClickListener;

    public AppItemSingleSelectAdapter(Context context, ArrayList<AppItem> list, View.OnClickListener itemClickListener, boolean simple) {
        this.list = list;
        this.context = context;

        drawableGeneric = ContextCompat.getDrawable(context, simple ? R.drawable.generic_simple : R.drawable.generic);

        this.itemClickListener = itemClickListener;
    }

    public void setSelectedItem(AppItem item) {
        selectedItem = item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnFocusChangeListener {
        public final ImageView ivIcon;
        public final TextView tvAppName;
        public final RadioButton rbCheck;
        private final Context context;

        public boolean focused = false;

        public ViewHolder(@NonNull View v, Context context) {
            super(v);
            this.context = context;

            ivIcon = v.findViewById(R.id.iv_appitem_single_select_icon);
            tvAppName = v.findViewById(R.id.tv_appitem_single_select_app_name);
            rbCheck = v.findViewById(R.id.rb_appitem_single_select_check);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_appitem_single_select, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.get(position) == null) {
            holder.ivIcon.setImageResource(R.drawable.ic_disable);
            holder.tvAppName.setText(context.getString(R.string.dialog_autolaunch_select_disable));
            holder.rbCheck.setChecked(selectedItem == null);
        } else {
            Drawable appIcon = null;
            var ci = ((MollaApplication)context).getCachedAppIcon(list.get(position).packageName);
            if (ci != null && ci.type == AppItemIcon.IconType.NORMAL) {
                appIcon = ci.drawable;
            } else {
                try {
                    appIcon = context.getPackageManager().getApplicationIcon(list.get(position).packageName);
                } catch (PackageManager.NameNotFoundException e) {
                    // appIcon = null;
                }
            }
            holder.ivIcon.setImageDrawable(appIcon);
            holder.tvAppName.setText(list.get(position).customItemDisplayName == null ? list.get(position).displayName : list.get(position).customItemDisplayName);
            holder.rbCheck.setChecked(list.get(position).equals(selectedItem));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
