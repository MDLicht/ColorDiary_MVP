package com.mdlicht.zb.colordiarymvp.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    const val YYYY: String = "yyyy"
    const val YYYY_MM_DD: String = "yyyy.MM.dd"
    const val YYYY_MM_DD_HH_mm: String = "yyyy.MM.dd HH:mm"
    const val YYYY_MM_DD_EEE_HH_mm: String = "yyyy.MM.dd EEE HH:mm"
    const val YYYY_MM_DD_EEE: String = "yyyy.MM.dd EEE"

    const val YYYY_MM_DD_BACK_UP: String = "yyyy_MM_dd"

    fun convertDateFormat(date: String, currentFormat: String, targetFormat: String): String {
        val fromFormat = SimpleDateFormat(currentFormat)
        val toFormat = SimpleDateFormat(targetFormat)

        val cal = Calendar.getInstance().apply {
            clear()
            time = fromFormat.parse(date)
        }

        return toFormat.format(cal.time)
    }

    fun convertDateFormat(year: Int, month: Int, day: Int, format: String): String {
        val toFormat = SimpleDateFormat(format)

        val cal = Calendar.getInstance().apply {
            clear()
            set(year, month, day)
        }

        return toFormat.format(cal.time)
    }

    fun getToday(format: String): String {
        val cal = Calendar.getInstance().apply {
            clear()
            timeInMillis = System.currentTimeMillis()
        }
        return SimpleDateFormat(format).format(cal.time)
    }
}