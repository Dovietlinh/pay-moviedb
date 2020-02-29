package com.example.movietv.di.modules

import android.app.Application
import androidx.room.Room
import com.example.movietv.common.Constants
import com.example.movietv.data.local.dao.MovieDao
import com.example.movietv.data.local.roomDatabase.MovieRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule(val app: Application) {
    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun movieDatabase(application: Application): MovieRoomDatabase {
        val tempInstance: MovieRoomDatabase? = null
        if (tempInstance != null) {
            return tempInstance
        }
        synchronized(this) {
            return Room.databaseBuilder(
                application,
                MovieRoomDatabase::class.java,
                Constants.DB_NAME
            ).allowMainThreadQueries().build()
        }
    }

    @Provides
    @Singleton
    fun binLocalDao(movieRoomDatabase: MovieRoomDatabase): MovieDao {
        return movieRoomDatabase.movieDao()
    }

}