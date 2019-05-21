package com.mdlicht.zb.colordiarymvp.util

import android.content.Context
import android.view.inputmethod.InputMethodManager


object ViewUtils {
    fun dpFromPx(context: Context, px: Float): Float = px / context.resources.displayMetrics.density

    fun pxFromDp(context: Context, dp: Float): Float = dp * context.resources.displayMetrics.density

    fun showKeyboard(context: Context) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun closeKeyboard(context: Context) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}