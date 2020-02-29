package com.example.movietv.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movietv.data.local.entity.MovieDetailLocal
import com.example.movietv.data.local.entity.MovieResponseLocal
import com.example.movietv.data.local.entity.TrailerResponseLocal
import io.reactivex.Observable

@Dao
interface MovieDao {
    //movie detail
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovie(movieDetailLocal: MovieDetailLocal)

    @Update
    fun updateFavorite(movieDetailLocal: MovieDetailLocal)

    //trailer
    @Query("Select * from db_trailers where movieID LIKE :movieID")
    fun getAllTrailerByID(movieID: Int): Observable<List<TrailerResponseLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTrailer(trailer: TrailerResponseLocal)

    @Update
    fun updateTrailer(trailer: TrailerResponseLocal)

    //movieResponse
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovieResponse(movieResponseLocal: MovieResponseLocal)

    @Query("SELECT * FROM db_moviesResponse WHERE page LIKE :page AND type LIKE :type")
    fun getMoviesByPageAndType(page: Int, type: String): Observable<List<MovieResponseLocal>>

    @Query("SELECT type FROM db_moviesResponse WHERE id LIKE :movieID")
    fun getTypeMovieByID(movieID: Int): String

}