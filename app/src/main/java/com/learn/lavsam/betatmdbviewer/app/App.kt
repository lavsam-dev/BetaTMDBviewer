package com.learn.lavsam.betatmdbviewer.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.learn.lavsam.betatmdbviewer.room.HistoryDao
import com.learn.lavsam.betatmdbviewer.room.HistoryDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        context = applicationContext
    }

    companion object {

        lateinit var context: Context
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
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!.historyDao()
        }
    }
}

interface IContextProvider{
    val context: Context
}

object ContextProvider: IContextProvider{
    override val context: Context
        get() = App.context
}
