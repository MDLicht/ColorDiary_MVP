package com.mdlicht.zb.colordiarymvp.presenter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.mdlicht.zb.colordiarymvp.constract.ColorHistoryContract
import com.mdlicht.zb.colordiarymvp.database.model.Diary
import com.mdlicht.zb.colordiarymvp.database.repository.DiaryRepository
import com.mdlicht.zb.colordiarymvp.util.TimeUtil

class ColorHistoryPresenter(_view: ColorHistoryContract.View, private val repository: DiaryRepository): ColorHistoryContract.Presenter {
    private val view: ColorHistoryContract.View = _view

    private val date: MutableLiveData<String> = MutableLiveData()
    private var diarySet: LiveData<List<Diary>>
    private val colorCountMap: MutableLiveData<MutableMap<String, Int>> = MutableLiveData()

    init {
        view.initView()
        date.value = TimeUtil.getToday(TimeUtil.YYYY)
        diarySet = Transformations.switchMap(date) {
            repository.getAllDiaryInYear(it)
        }
    }

    override fun getDate(): MutableLiveData<String> = date

    override fun getDiarySet(): LiveData<List<Diary>> = diarySet

    override fun getColorCountMap(): MutableLiveData<MutableMap<String, Int>> = colorCountMap

    override fun updateColorCountMap(map: MutableMap<String, Int>) {
        colorCountMap.value = map
    }
}