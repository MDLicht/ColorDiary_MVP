package com.mdlicht.zb.colordiarymvp.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.activity.ReadDiaryActivity
import com.mdlicht.zb.colordiarymvp.database.model.Diary
import com.mdlicht.zb.colordiarymvp.util.ColorUtil
import kotlinx.android.synthetic.main.item_color_history_cell.view.*
import kotlinx.android.synthetic.main.item_color_history_title.view.*

class ColorHistoryRvAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val NUM_MONTH = 12
        const val NUM_DAY = 31
    }

    var dataSet: List<Diary>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var selYear: String? = null

    fun getSelectedYear(): String? = selYear

    fun setSelectedYear(year: String?) {
        this.selYear = year
    }

    enum class ViewType(val type: Int) {
        TYPE_EMPTY(-1),
        TYPE_MONTH(0),
        TYPE_DAY(1),
        TYPE_CELL(2)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            ViewType.TYPE_EMPTY.type
        } else if (position >= (NUM_MONTH + 1) && position % (NUM_MONTH + 1) == 0) {
            ViewType.TYPE_DAY.type
        } else if (position / (NUM_MONTH + 1) == 0) {
            ViewType.TYPE_MONTH.type
        } else {
            ViewType.TYPE_CELL.type
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return when (p1) {
            ViewType.TYPE_EMPTY.type -> EmptyViewHolder(
                LayoutInflater.from(p0.context).inflate(
                    R.layout.item_color_history_empty,
                    p0,
                    false
                )
            )
            ViewType.TYPE_CELL.type -> ColorHistoryViewHolder(
                LayoutInflater.from(p0.context).inflate(
                    R.layout.item_color_history_cell,
                    p0,
                    false
                )
            )
            ViewType.TYPE_MONTH.type -> MonthTitleViewHolder(
                LayoutInflater.from(p0.context).inflate(
                    R.layout.item_color_history_title,
                    p0,
                    false
                )
            )
            ViewType.TYPE_DAY.type -> DayTitleViewHolder(
                LayoutInflater.from(p0.context).inflate(
                    R.layout.item_color_history_title,
                    p0,
                    false
                )
            )
            else -> throw Exception()
        }
    }

    override fun getItemCount(): Int {
        return (NUM_DAY + 1) * (NUM_MONTH + 1)
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        when (p0) {
            is ColorHistoryViewHolder -> p0.itemView.apply {
                this.let {
                    val month = p1 % (NUM_MONTH + 1)
                    val day = p1 / (NUM_MONTH + 1)
                    val monthStr: String = if (month < 10) "0$month" else month.toString()
                    val dayStr: String = if (day < 10) "0$day" else day.toString()
                    val DATE_STRING = "$selYear.$monthStr.$dayStr"

                    val diary = dataSet?.find { d ->
                        d.date.startsWith(DATE_STRING)
                    }
                    it.ivCell.apply {
                        setBackgroundColor(if (diary != null) ColorUtil.getColorByFeel(diary.feel) else Color.WHITE)
                        setOnClickListener {
                            diary?.let {d ->
                                context.startActivity(Intent(context, ReadDiaryActivity::class.java).apply {
                                    putExtra(ReadDiaryActivity.KEY_DATE, d.date)
                                    putExtra(ReadDiaryActivity.KEY_DIARY, d)
                                })
                            }
                        }
                    }
                }
            }
            is MonthTitleViewHolder -> p0.itemView.apply {
                if (p1 > 0)
                    this.tvTitle.text = p1.toString()
            }
            is DayTitleViewHolder -> p0.itemView.apply {
                this.tvTitle.text = (p1 / (NUM_MONTH + 1)).toString()
            }
        }
    }

    class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ColorHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class MonthTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class DayTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}