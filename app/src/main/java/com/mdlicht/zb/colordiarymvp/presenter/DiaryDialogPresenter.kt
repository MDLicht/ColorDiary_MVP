package com.mdlicht.zb.colordiarymvp.presenter

import android.text.TextUtils
import com.mdlicht.zb.colordiarymvp.R
import com.mdlicht.zb.colordiarymvp.common.AESHelper
import com.mdlicht.zb.colordiarymvp.common.App
import com.mdlicht.zb.colordiarymvp.constract.DiaryDialogConstract
import com.mdlicht.zb.colordiarymvp.database.model.Diary
import com.mdlicht.zb.colordiarymvp.database.repository.DiaryRepository

class DiaryDialogPresenter(_view: DiaryDialogConstract.View, private val repository: DiaryRepository): DiaryDialogConstract.Presenter {
    private val view: DiaryDialogConstract.View = _view

    init {
        view.initView()
    }

    override fun saveDiary(id: Int?, contents: String?, feel: String?, date: String?) {
        if (TextUtils.isEmpty(feel)) {
            view.showMsg(App.getInstance().getString(R.string.msg_empty_diary_feel))
            return
        } else if (TextUtils.isEmpty(contents)) {
            view.showMsg(App.getInstance().getString(R.string.msg_empty_diary_contents))
            return
        }
        if (id == -1) {
            repository.insertDIary(
                Diary(
                    0,
                    AESHelper.encrypt(contents!!, App.getInstance().getNativeKey2()),
                    feel!!,
                    date!!
                )
            )
            view.diaryUpdated()
        } else {
            repository.updateDIary(
                Diary(
                    id!!,
                    AESHelper.encrypt(contents!!, App.getInstance().getNativeKey2()),
                    feel!!,
                    date!!
                )
            )
            view.diaryUpdated()
        }
    }
}