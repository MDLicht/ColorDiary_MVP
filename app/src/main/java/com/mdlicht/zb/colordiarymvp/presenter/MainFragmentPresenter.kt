package com.mdlicht.zb.colordiarymvp.presenter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mdlicht.zb.colordiarymvp.constract.MainFragmentContract
import com.mdlicht.zb.colordiarymvp.database.model.Diary
import com.mdlicht.zb.colordiarymvp.database.repository.DiaryRepository

class MainFragmentPresenter(_view: MainFragmentContract.View, private val repository: DiaryRepository): MainFragmentContract.Presenter {
    private val view: MainFragmentContract.View = _view
    private var date: String? = null
    private var diary: LiveData<Diary> = MutableLiveData()

    override fun getDate(): String = date!!

    override fun updateDate(date: String) {
        this.date = date
        this.diary = repository.getDiaryByDate(this.date!!)
    }

    override fun getDiary(): LiveData<Diary> = diary
}