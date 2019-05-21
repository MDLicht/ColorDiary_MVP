package com.mdlicht.zb.colordiarymvp.constract

import android.arch.lifecycle.LiveData
import com.mdlicht.zb.colordiarymvp.database.model.Diary

interface MainConstract {
    interface View: BaseContract.View {
        fun updateDate(date: String?)
        fun updateDiary(diary: Diary?)
    }
    interface Presenter: BaseContract.Presenter {
        fun getDate(): LiveData<String>
        fun getDiary(): LiveData<Diary>
        fun dateChanged(date: String)
    }
    interface Model: BaseContract.Model {

    }
}