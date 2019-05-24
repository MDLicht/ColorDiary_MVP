package com.mdlicht.zb.colordiarymvp.presenter

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.mdlicht.zb.colordiarymvp.constract.TimelineContract
import com.mdlicht.zb.colordiarymvp.database.model.Diary
import com.mdlicht.zb.colordiarymvp.database.repository.DiaryRepository

class TimelinePresenter(_view: TimelineContract.View, private val repository: DiaryRepository): TimelineContract.Presenter {
    private val view: TimelineContract.View = _view
    private var timeline: LiveData<PagedList<Diary>> = LivePagedListBuilder<Int, Diary>(repository.getPagedDiaryByDescDate(), PagedList.Config.Builder().setInitialLoadSizeHint(10).setEnablePlaceholders(false).setPageSize(10).build()).build()

    init {
        view.initView()
    }

    override fun getPagedTimeline(): LiveData<PagedList<Diary>> = timeline
}