package com.mdlicht.zb.colordiarymvp.constract

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.mdlicht.zb.colordiarymvp.database.model.Diary

interface TimelineContract {
    interface View: BaseContract.View {

    }
    interface Presenter: BaseContract.Presenter {
        fun getPagedTimeline(): LiveData<PagedList<Diary>>
    }
    interface Model: BaseContract.Model {

    }
}