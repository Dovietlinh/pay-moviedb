package com.example.movietv.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movietv.model.local.MovieDetailEntity
import io.reactivex.Observable

@Dao
interface MovieDao {

    @Query("Select * from movie where isFavorite = 1")
    fun getAllFavorite(): LiveData<List<MovieDetailEntity>>

    @Query("SELECT * FROM movie WHERE id LIKE :movieID AND isFavorite = 1")
    fun checkMovieIsFavorite(movieID: Int): LiveData<MovieDetailEntity>

    @Query("SELECT * FROM movie WHERE id LIKE :movieID")
    fun getMovie(movieID: Int): Observable<MovieDetailEntity>

    @Insert
    fun createFavorite(movieDetailEntity: MovieDetailEntity)

    @Update
    fun updateFavorite(movieDetailEntity: MovieDetailEntity)

    @Query("SELECT * FROM movie WHERE id LIKE :movieID")
    fun findMovieById(movieID: Int): MovieDetailEntity
}