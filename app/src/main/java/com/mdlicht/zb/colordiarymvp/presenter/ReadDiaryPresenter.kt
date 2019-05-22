package com.mdlicht.zb.colordiarymvp.presenter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mdlicht.zb.colordiarymvp.constract.ReadDiaryContract
import com.mdlicht.zb.colordiarymvp.database.model.Diary
import com.mdlicht.zb.colordiarymvp.database.repository.DiaryRepository

class ReadDiaryPresenter(_view: ReadDiaryContract.View, private val repository: DiaryRepository, currentDate: String, currentDiary: Diary): ReadDiaryContract.Presenter {
    private val view: ReadDiaryContract.View = _view
    private val date: MutableLiveData<String> = MutableLiveData()
    private val diary: MutableLiveData<Diary> = MutableLiveData()
    private val diarySet: LiveData<List<Diary>> = repository.getAllDiaryInYear(currentDate.split(".")[0])

    init {
        view.initView()
        date.value = currentDate
        diary.value = currentDiary
    }

    override fun getDiarySet(): LiveData<List<Diary>> = diarySet

    override fun getDate(): String? = date.value

    override fun getDiary(): Diary? = diary.value

    override fun updateDate(date: String?) {
        this.date.value = date
        view.updateDate(date)
    }

    override fun updateDiary(diary: Diary?) {
        this.diary.value = diary
    }

    override fun deleteDiary(diary: Diary) {
        repository.deleteDiary(diary)
    }
}