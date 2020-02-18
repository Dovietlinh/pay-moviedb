package com.example.movietv.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movietv.model.local.MovieDetailLocal
import io.reactivex.Observable

@Dao
interface MovieDao {

    @Query("Select * from db_movies where isFavorite = 1")
    fun getAllFavorite(): LiveData<List<MovieDetailLocal>>

    @Query("SELECT * FROM db_movies WHERE id LIKE :movieID AND isFavorite = 1")
    fun checkMovieIsFavorite(movieID: Int): LiveData<MovieDetailLocal>

    @Query("SELECT * FROM db_movies WHERE id LIKE :movieID")
    fun getMovie(movieID: Int): Observable<MovieDetailLocal>

    @Query("UPDATE db_movies SET isFavorite=0 WHERE id LIKE :movieID")
    fun remoteMovieFavorite(movieID: Int)

    @Query("UPDATE db_movies SET isFavorite=1 WHERE id LIKE :movieID")
    fun insertMovieFavorite(movieID: Int)

    @Insert
    fun saveMovie(movieDetailLocal: MovieDetailLocal)

    @Update
    fun updateFavorite(movieDetailLocal: MovieDetailLocal)
}