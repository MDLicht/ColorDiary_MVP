package com.mdlicht.zb.colordiarymvp.constract

import android.arch.lifecycle.LiveData
import com.mdlicht.zb.colordiarymvp.database.model.Diary

interface MainFragmentContract {
    interface View: BaseContract.View {
        fun updateDiary(diary: Diary?)
    }
    interface Presenter: BaseContract.Presenter {
        fun getDate(): String
        fun updateDate(date: String)
        fun getDiary(): LiveData<Diary>
    }
    interface Model: BaseContract.Model {

    }
}