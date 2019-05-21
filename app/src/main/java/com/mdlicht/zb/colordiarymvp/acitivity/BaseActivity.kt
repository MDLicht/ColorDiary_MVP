package com.mdlicht.zb.colordiarymvp.acitivity

import android.support.v7.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {
    abstract fun initPresenter()
}