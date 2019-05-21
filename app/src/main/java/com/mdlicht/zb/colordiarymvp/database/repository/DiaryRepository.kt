package com.mdlicht.zb.colordiarymvp.database.repository

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.content.Context
import android.os.AsyncTask
import com.mdlicht.zb.colordiarymvp.database.dao.DiaryDao
import com.mdlicht.zb.colordiarymvp.database.model.Diary
import com.mdlicht.zb.colordiarymvp.database.DB

class DiaryRepository(val context: Context) {
    private val diaryDao: DiaryDao = DB.getDatabase(context)!!.diaryDao()

    fun getDiaryByDate(date: String): LiveData<Diary> {
        return diaryDao.getDiaryByDate(date)
    }

    fun getAllDiary(): LiveData<List<Diary>> {
        return diaryDao.getAllDiary()
    }

    fun getAllDiaryInYear(year: String?): LiveData<List<Diary>> {
        return diaryDao.getAllDiaryInYear(year)
    }

    fun getPagedDiaryByDescDate(): DataSource.Factory<Int, Diary> {
        return diaryDao.getPagedDiaryByDescDate()
    }

    fun insertDIary(diary: Diary) {
        RoomInsertAsyncTask(diaryDao, diary).execute()
    }

    fun updateDIary(diary: Diary) {
        RoomUpdateAsyncTask(diaryDao, diary).execute()
    }

    fun deleteDiary(diary: Diary) {
        RoomDeleteAsyncTask(diaryDao, diary).execute()
    }

    class RoomInsertAsyncTask(private val dao: DiaryDao, val diary: Diary): AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            dao.insertDiary(diary)
            return null
        }
    }

    class RoomUpdateAsyncTask(private val dao: DiaryDao, val diary: Diary): AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            dao.updateDiary(diary)
            return null
        }
    }

    class RoomDeleteAsyncTask(private val dao: DiaryDao, val diary: Diary): AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            dao.deleteDiary(diary)
            return null
        }
    }
}