package com.mdlicht.zb.colordiarymvp.util

import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.common.App

object ColorDiaryUtil {
    private val guides = App.getInstance().resources.getStringArray(R.array.main_today_guide_array)

    fun getTodayGuilde(): String = guides[(Math.random() * guides.size).toInt()]
}