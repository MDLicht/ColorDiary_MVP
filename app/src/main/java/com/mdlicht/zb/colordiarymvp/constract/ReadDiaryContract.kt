package com.mdlicht.zb.colordiarymvp.constract

import android.arch.lifecycle.LiveData
import com.mdlicht.zb.colordiarymvp.database.model.Diary

interface ReadDiaryContract {
    interface View: BaseContract.View {
        fun updateDate(date: String?)
    }
    interface Presenter: BaseContract.Presenter {
        fun getDiarySet(): LiveData<List<Diary>>
        fun getDate(): String?
        fun getDiary(): Diary?
        fun updateDate(date: String?)
        fun updateDiary(diary: Diary?)
        fun deleteDiary(diary: Diary)
    }
    interface Model: BaseContract.Model {

    }
}