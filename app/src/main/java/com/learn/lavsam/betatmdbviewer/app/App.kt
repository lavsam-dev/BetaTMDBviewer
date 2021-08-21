package com.learn.lavsam.betatmdbviewer.app

import android.app.Application
import androidx.room.Room
import com.learn.lavsam.betatmdbviewer.room.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: App? = null
        private var db: HistoryDataBase? = null

        private const val DB_NAME = "HistoryDataBase.db"

        fun getHistoryDao(): HistoryDao {
            if (db == null) {
                synchronized(HistoryDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) {
                            throw IllegalStateException("Application is null while creating HistoryDataBase")
                        }
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            HistoryDataBase::class.java,
                            DB_NAME)
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!.historyDao()
        }

//        fun getNoteDao(): NoteDao {
//            if (dataBase == null) {
//                synchronized(DataBase::class.java) {
//                    if (dataBase == null) {
//                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
//                        dataBase = Room.databaseBuilder(
//                            appInstance!!.applicationContext,
//                            DataBase::class.java,
//                            DB_NAME)
//                            .allowMainThreadQueries()
//                            .build()
//                    }
//                }
//            }
//            return dataBase!!.noteDao()
//        }
    }
}
