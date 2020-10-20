package com.twomin.timerclient.ui.main

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TimerDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapter = parent.adapter ?: return
        if (parent.getChildAdapterPosition(view) == adapter.itemCount - 1)
            return
        outRect.bottom = (15 * view.resources.displayMetrics.density).toInt()
    }
}