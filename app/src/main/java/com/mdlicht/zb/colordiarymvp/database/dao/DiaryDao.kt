package com.mdlicht.zb.colordiarymvp.database.dao

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.*
import com.mdlicht.zb.colordiarymvp.database.model.Diary

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary WHERE date LIKE '%' || :date || '%'")
    fun getDiaryByDate(date: String): LiveData<Diary>

    @Query("SELECT * FROM diary ORDER BY date ASC")
    fun getAllDiary(): LiveData<List<Diary>>

    @Query("SELECT * FROM diary WHERE date LIKE '%' || :year || '%' ORDER BY date ASC")
    fun getAllDiaryInYear(year: String?): LiveData<List<Diary>>

    @Query("SELECT * FROM diary ORDER BY date DESC")
    fun getPagedDiaryByDescDate(): DataSource.Factory<Int, Diary>

    @Insert
    fun insertDiary(diary: Diary)

    @Update
    fun updateDiary(diary: Diary)

    @Delete
    fun deleteDiary(diary: Diary)
}