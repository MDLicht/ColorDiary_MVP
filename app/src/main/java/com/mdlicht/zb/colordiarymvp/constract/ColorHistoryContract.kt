package com.mdlicht.zb.colordiarymvp.constract

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mdlicht.zb.colordiarymvp.database.model.Diary

interface ColorHistoryContract {
    interface View: BaseContract.View {

    }
    interface Presenter: BaseContract.Presenter {
        fun getDate(): MutableLiveData<String>
        fun getDiarySet(): LiveData<List<Diary>>?
        fun getColorCountMap(): MutableLiveData<MutableMap<String, Int>>
        fun updateColorCountMap(map: MutableMap<String, Int>)
    }
    interface Model: BaseContract.Model {

    }
}