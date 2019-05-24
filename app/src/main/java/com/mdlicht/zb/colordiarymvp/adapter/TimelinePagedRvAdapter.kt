package com.mdlicht.zb.colordiarymvp.adapter

import android.arch.paging.PagedListAdapter
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.activity.ReadSingleDiaryActivity
import com.mdlicht.zb.colordiarymvp.common.App
import com.mdlicht.zb.colordiarymvp.database.model.Diary
import kotlinx.android.synthetic.main.item_timeline.view.*

class TimelinePagedRvAdapter : PagedListAdapter<Diary, TimelinePagedRvAdapter.TimelineViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Diary>() {
            override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        return TimelineViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_timeline, parent, false))
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        holder.itemView.apply {
            val item = getItem(position)
            item?.let {
                ivFeel.setImageDrawable(ColorDrawable(App.feelColorMap[it.feel]!!))
                tvContents.text = it.getDecryptContents()
                tvDate.text = it.date

                setOnClickListener { v ->
                    v.context.apply {
                        startActivity(Intent(this, ReadSingleDiaryActivity::class.java).apply {
                            putExtra(ReadSingleDiaryActivity.KEY_DATE, it.date)
                        })
                    }
                }
            }
        }
    }

    class TimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}