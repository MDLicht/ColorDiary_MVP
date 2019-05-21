package com.mdlicht.zb.colordiarymvp.common

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import android.widget.Toast
import com.mdlicht.zb.colordiarymvp.R

fun Context.showToast(text: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).apply {
        view.apply {
            setBackgroundResource(R.color.colorPrimary)
            findViewById<TextView>(android.R.id.message).apply {
                setBackgroundResource(R.color.colorPrimary)
                setTextColor(Color.WHITE)
                setPadding(20, 0, 20, 0)
            }
        }
    }.show()
}