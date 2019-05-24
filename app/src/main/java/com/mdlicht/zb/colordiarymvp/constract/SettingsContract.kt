package com.mdlicht.zb.colordiarymvp.constract

interface SettingsContract {
    interface View: BaseContract.View {

    }
    interface Presenter: BaseContract.Presenter {
        fun export()
        fun import(path: String): Boolean
    }
    interface Model: BaseContract.Model {

    }
}