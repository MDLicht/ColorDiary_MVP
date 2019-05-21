package com.mdlicht.zb.colordiarymvp.presenter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.mdlicht.zb.colordiarymvp.constract.MainConstract
import com.mdlicht.zb.colordiarymvp.database.model.Diary
import com.mdlicht.zb.colordiarymvp.database.repository.DiaryRepository
import com.mdlicht.zb.colordiarymvp.util.TimeUtil

class MainPresenter(_view: MainConstract.View, private val repository: DiaryRepository) : BasePresenter(),
    MainConstract.Presenter {
    private val view: MainConstract.View = _view
    private var date: MutableLiveData<String> = MutableLiveData()
    private var diary: LiveData<Diary> = Transformations.switchMap(date) {
        repository.getDiaryByDate(it)
    }

    init {
        view.initView()
        date.value = TimeUtil.getToday(TimeUtil.YYYY_MM_DD)
    }

    override fun getDate(): LiveData<String> = date

    override fun getDiary(): LiveData<Diary> = diary

    override fun dateChanged(date: String) {
        this.date.value = date
    }

    override fun onClear() {

    }
}