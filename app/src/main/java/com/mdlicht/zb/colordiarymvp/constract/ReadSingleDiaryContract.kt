package com.mdlicht.zb.colordiarymvp.constract

import android.arch.lifecycle.LiveData
import com.mdlicht.zb.colordiarymvp.database.model.Diary

interface ReadSingleDiaryContract {
    interface View: BaseContract.View {

    }
    interface Presenter: BaseContract.Presenter {
        fun getDate(): LiveData<String>
        fun getDiary(): LiveData<Diary>
        fun deleteDiary(diary: Diary)
    }
    interface Model: BaseContract.Model {

    }
}