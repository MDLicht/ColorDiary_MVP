package com.mdlicht.zb.colordiarymvp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.util.TimeUtil
import kotlinx.android.synthetic.main.item_year.view.*

class YearRvAdapter(private val context: Context) : RecyclerView.Adapter<YearRvAdapter.YearViewHolder>() {
    private val dataSet: MutableList<String> = mutableListOf()
    private var selYear: String? = null

    init {
        val minYear = context.getString(R.string.min_date).split(".")[0]
        val maxYear = TimeUtil.getToday(TimeUtil.YYYY)
        for (i in minYear.toInt()..maxYear.toInt()) {
            dataSet.add(i.toString())
        }
    }

    fun getSelectedYear(): String? = selYear

    fun setSelectedYear(year: String?) {
        val preSelYear = this.selYear
        year?.let {
            this.selYear = year
            notifyItemChanged(getItemPosition(this.selYear!!))
            preSelYear?.let {
                notifyItemChanged(getItemPosition(it))
            }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): YearViewHolder {
        return YearViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_year, p0, false))
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun getItemPosition(item: String): Int {
        return dataSet.indexOf(item)
    }

    override fun onBindViewHolder(p0: YearViewHolder, p1: Int) {
        p0.itemView.apply {
            val year = dataSet[p1]
            tvYear.text = year
            tvYear.setTextColor(if(selYear.equals(year)) this.context.resources.getColor(R.color.colorPrimary) else this.resources.getColor(R.color.colorTextPrimary))
            tvYear.setOnClickListener {
                if(selYear.equals(year))
                    return@setOnClickListener
                val preSelYear = selYear
                year.let {
                    selYear = year
                    notifyItemChanged(getItemPosition(selYear!!))
                    preSelYear?.let {
                        notifyItemChanged(getItemPosition(it))
                    }
                }
            }
        }
    }

    class YearViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}