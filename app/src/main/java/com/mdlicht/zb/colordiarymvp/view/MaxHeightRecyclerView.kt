package com.mdlicht.zb.colordiarymvp.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.mdlicht.zb.colordiarymvp.common.App
import com.mdlicht.zb.colordiarymvp.util.ViewUtils

class MaxHeightRecyclerView: RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val height = MeasureSpec.makeMeasureSpec(ViewUtils.pxFromDp(App.getInstance(), 100f).toInt(), MeasureSpec.AT_MOST)
        super.onMeasure(widthSpec, height)
    }
}