package com.mdlicht.zb.colordiarymvp.common

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


class GridDividerDecoration(private val offset: Int, private val numCol: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = offset
        outRect.right = offset
        outRect.bottom = offset
        if (parent.getChildAdapterPosition(view) / numCol == 0) {
            outRect.top = offset
        }
    }
}