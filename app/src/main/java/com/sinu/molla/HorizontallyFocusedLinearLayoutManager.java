// Copyright 2022-2023 Woohyun Shin (sinusinu)
// SPDX-License-Identifier: GPL-3.0-only

package com.sinu.molla;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontallyFocusedLinearLayoutManager extends LinearLayoutManager {
    private RecyclerView rv;

    public HorizontallyFocusedLinearLayoutManager(Context context, int orientation, boolean reverseLayout, RecyclerView rv) {
        super(context, orientation, reverseLayout);
        this.rv = rv;
    }

    @Override
    public View onFocusSearchFailed(View focused, int focusDirection, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // this will prevent RecyclerView from losing focus when e.g. scrolling too fast with d-pad.
        // only applies to horizontal navigation.
        if (focusDirection == View.FOCUS_LEFT || focusDirection == View.FOCUS_RIGHT) return focused;
        return super.onFocusSearchFailed(focused, focusDirection, recycler, state);
    }
}
