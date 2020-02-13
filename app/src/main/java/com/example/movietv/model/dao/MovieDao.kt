package com.example.movietv.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.movietv.model.local.MovieDetailEntity

@Dao
interface MovieDao {

    @Query("Select * from movie")
    fun getAllFavorite(): LiveData<List<MovieDetailEntity>>

    @Query("SELECT * FROM movie WHERE id LIKE :movieID")
    fun checkFavorite(movieID: Int): LiveData<MovieDetailEntity>

    @Insert
    fun createFavorite(movieDetailEntity: MovieDetailEntity)

    @Delete
    fun deleteFavorite(movieDetailEntity: MovieDetailEntity)

    @Query("SELECT * FROM movie WHERE id LIKE :movieID")
    fun findMovieById(movieID: Int): MovieDetailEntity
}