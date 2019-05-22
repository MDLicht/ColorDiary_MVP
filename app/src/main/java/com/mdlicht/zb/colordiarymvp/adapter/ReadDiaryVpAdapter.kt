package com.mdlicht.zb.colordiarymvp.adapter

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.database.model.Diary

class ReadDiaryVpAdapter: PagerAdapter() {
    private var dataSet: List<Diary>? = null

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    fun setDiarySet(diarySet: List<Diary>?) {
        this.dataSet = diarySet
        notifyDataSetChanged()
    }

    fun getItemByIndex(position: Int): Diary? {
        return dataSet?.get(position)
    }

    fun getPositionByDate(date: String): Int {
        val index = dataSet?.indexOf(dataSet?.find {
            it.date == date
        }) ?: 0
        return if(index >= 0) index else 0
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getCount(): Int {
        return dataSet?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(container.context).inflate(R.layout.item_read_diary, container, false)
        view.findViewById<TextView>(R.id.tvContents).apply {
            text = dataSet?.get(position)?.getDecryptContents() ?: ""
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        (container as ViewPager).removeView(obj as View)
    }
}