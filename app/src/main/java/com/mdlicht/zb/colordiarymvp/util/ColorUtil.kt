package com.mdlicht.zb.colordiarymvp.util

import android.graphics.Color
import com.mdlicht.zb.colordiarymvp.common.App

object ColorUtil {
    @JvmStatic
    fun getColorByFeel(feel: String): Int {
        return App.feelColorMap[feel] ?: Color.WHITE
    }
}