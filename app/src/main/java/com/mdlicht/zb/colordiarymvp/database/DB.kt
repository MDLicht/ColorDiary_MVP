package com.mdlicht.zb.colordiarymvp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.Environment
import com.mdlicht.zb.colordiarymvp.database.dao.DiaryDao
import com.mdlicht.zb.colordiarymvp.database.model.Diary
import com.mdlicht.zb.colordiarymvp.common.App
import com.mdlicht.zb.colordiarymvp.util.TimeUtil
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


@Database(entities = [Diary::class], version = 1)
abstract class DB : RoomDatabase() {
    companion object {
        private const val DB_NAME = "ColorDiary"
        private const val DB_CHECK = "CD_CHECK_CODE"
        private var INSTANCE: DB? = null

        fun getDatabase(context: Context): DB? {
            if (INSTANCE == null) {
                synchronized(DB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, DB::class.java, DB_NAME)
                        .build()
                }
            }
            return INSTANCE
        }

        fun import(dbPath: String): Boolean {
            closeInstance()
            val importFile: File = File(dbPath)
            val outFileName = ("/data/data/"
                    + App.getInstance().packageName
                    + "/databases/" + DB_NAME);
            val outFile = File(outFileName)
            if (!importFile.exists() || !importFile.name.matches(Regex("ColorDiary_Backup_[0-9_]*.db"))) {
                return false
            }
            val myOutput = FileOutputStream(outFileName)
            val buffer = ByteArray(1024)
            var length: Int
            val importFileInputStream = importFile.inputStream()
            while (true) {
                length = importFileInputStream.read(buffer)
                if(length > 0)
                    myOutput.write(buffer, 0, length)
                else
                    break
            }
            myOutput.flush()
            myOutput.close()

            return true
        }

        fun export() {
            closeInstance()
            val exportFileDir = File(Environment.getExternalStorageDirectory().toString() + "/" + DB_NAME)
            if(!exportFileDir.exists()) {
                exportFileDir.mkdir()
            }
//            val exportPath: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val appDbPath = App.getInstance().getDatabasePath(DB_NAME).absolutePath

            val appDbFile = File(appDbPath)
            val exportDbFile = File(exportFileDir, "ColorDiary_Backup_${TimeUtil.getToday(TimeUtil.YYYY_MM_DD_BACK_UP)}.db")

            if(appDbFile.exists()) {
                val src = FileInputStream(appDbFile).channel
                val dst = FileOutputStream(exportDbFile).channel
                dst.transferFrom(src, 0, src.size())
                src.close()
                dst.close()
            }
        }

        private fun closeInstance() {
            INSTANCE?.let {
                if(it.isOpen)
                    it.close()
            }
            INSTANCE = null
        }
    }

    abstract fun diaryDao(): DiaryDao
}