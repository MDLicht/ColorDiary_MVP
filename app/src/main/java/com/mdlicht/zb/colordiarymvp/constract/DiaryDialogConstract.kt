package com.mdlicht.zb.colordiarymvp.constract

interface DiaryDialogConstract {
    interface View: BaseContract.View {
        fun showMsg(msg: String)
        fun diaryUpdated()
    }
    interface Presenter: BaseContract.Presenter {
        fun saveDiary(id: Int?, contents: String?, feel: String?, date: String?)
    }
    interface Model: BaseContract.Model {

    }
}