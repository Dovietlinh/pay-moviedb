package com.example.movietv.model.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movietv.common.Constants.Companion.DB_NAME
import com.example.movietv.model.dao.MovieDao
import com.example.movietv.model.local.MovieDetailLocal
import com.example.movietv.model.local.MovieResponseLocal
import com.example.movietv.model.local.TrailerResponseLocal


@Database(entities = [MovieDetailLocal::class, TrailerResponseLocal::class, MovieResponseLocal::class], version = 1)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        private var INSTANCE: MovieRoomDatabase? = null
        fun getDB(context: Context): MovieRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieRoomDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}