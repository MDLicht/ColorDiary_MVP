package com.mdlicht.zb.colordiarymvp.adapter

import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.common.App
import kotlinx.android.synthetic.main.item_color_history_count.view.*

class ColorHistoryCountRvAdapter: RecyclerView.Adapter<ColorHistoryCountRvAdapter.ColorHistoryCountViewHolder>() {
    private var dataMap: MutableMap<String, Int>? = null

    fun setDataMap(dataMap: MutableMap<String, Int>?) {
        this.dataMap = dataMap
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ColorHistoryCountViewHolder {
        return ColorHistoryCountViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_color_history_count, p0, false))
    }

    override fun getItemCount(): Int {
        return dataMap?.size ?: 0
    }

    override fun onBindViewHolder(p0: ColorHistoryCountViewHolder, p1: Int) {
        p0.itemView.apply {
            val key = dataMap?.keys?.elementAt(p1)
            val value = dataMap?.get(key)
            this.ivColorChip.setImageDrawable(ColorDrawable(App.feelColorMap[key]!!))
            this.tvCount.text = value?.toString() ?: "0"
        }
    }

    class ColorHistoryCountViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}