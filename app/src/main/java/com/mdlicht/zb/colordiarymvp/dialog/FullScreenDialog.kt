package com.mdlicht.zb.colordiarymvp.dialog

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.DialogFragment
import com.mdlicht.zb.colordiarymvp.R


abstract class FullScreenDialog: DialogFragment() {
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreenDialogTheme) // Full Screen Dialog
    }

    abstract fun initPresenter()
}