package com.mdlicht.zb.colordiarymvp.presenter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.mdlicht.zb.colordiarymvp.constract.ReadSingleDiaryContract
import com.mdlicht.zb.colordiarymvp.database.model.Diary
import com.mdlicht.zb.colordiarymvp.database.repository.DiaryRepository

class ReadSingleDiaryPresenter(_view: ReadSingleDiaryContract.View, private val repository: DiaryRepository, currentDate: String): ReadSingleDiaryContract.Presenter {
    private val view: ReadSingleDiaryContract.View = _view

    private val date: MutableLiveData<String> = MutableLiveData()
    private val diary: LiveData<Diary> = Transformations.switchMap(date) {
        repository.getDiaryByDate(it)
    }

    init {
        view.initView()
        date.value = currentDate
    }

    override fun getDate(): LiveData<String> = date

    override fun getDiary(): LiveData<Diary> = diary

    override fun deleteDiary(diary: Diary) {
        repository.deleteDiary(diary)
    }
}