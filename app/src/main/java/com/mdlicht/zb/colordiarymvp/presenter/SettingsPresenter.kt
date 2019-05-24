package com.mdlicht.zb.colordiarymvp.presenter

import com.mdlicht.zb.colordiarymvp.constract.SettingsContract
import com.mdlicht.zb.colordiarymvp.database.DB

class SettingsPresenter(_view: SettingsContract.View): SettingsContract.Presenter {
    private val view: SettingsContract.View = _view

    init {
        view.initView()
    }

    override fun export() {
        DB.export()
    }

    override fun import(path: String): Boolean {
        return DB.import(path)
    }
}