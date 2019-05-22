package com.mdlicht.zb.colordiarymvp.activity

import android.support.v7.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {
    abstract fun initPresenter()
}