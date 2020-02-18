package com.example.movietv.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.movietv.api.ApiService
import com.example.movietv.model.dao.MovieDao
import com.example.movietv.model.local.MovieDetailLocal
import com.example.movietv.model.remote.Genre
import com.example.movietv.model.remote.MovieDetails
import com.example.movietv.model.remote.TrailerResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MovieDetailsRepository(apiService: ApiService, private val movieDao: MovieDao) {
    private var checkMovieExistDb = false
    private var checkMovieIsFavorite = false
    private val movieDetailsDataSource = MovieDetailsDataSource(apiService)
    fun fetchTrailer(movieID: Int): Observable<TrailerResponse> {
        return movieDetailsDataSource.fetchTrailer(movieID)
    }

    fun fetchMovieCaching(
            movieID: Int
    ): Observable<MovieDetails> {
        var getApi: Observable<MovieDetails> = getMovieApi(movieID)
        var getDb: Observable<MovieDetails> = getMovieDb(movieID)
        return Observable.mergeDelayError(getDb, getApi)
    }

    private fun getMovieApi(
            movieID: Int
    ): Observable<MovieDetails> {
        return movieDetailsDataSource.fetchMovie(movieID).subscribeOn(Schedulers.io())
//            .delay(2L, java.util.concurrent.TimeUnit.SECONDS)
                .doOnNext {
                    if (!checkMovieExistDb) {
                        Log.d("CachingDb", "api add")
                        movieDao.saveMovie(
                                MovieDetailLocal(
                                        it.id, it.overview, it.posterPath, it.backdropPath,
                                        it.releaseDate, it.tagline, it.title, it.runtime, it.rating
                                )
                        )
                    } else {
                        Log.d("CachingDb", "api update")
                        movieDao.updateFavorite(
                                MovieDetailLocal(
                                        it.id, it.overview, it.posterPath, it.backdropPath, it.releaseDate,
                                        it.tagline, it.title, it.runtime, it.rating, checkMovieIsFavorite
                                )
                        )
                    }
                }
    }

    private fun getMovieDb(movieID: Int): Observable<MovieDetails> {
        val listGenre: MutableList<Genre> = mutableListOf()
        return movieDao.getMovie(movieID)
                .doOnNext {
                    checkMovieIsFavorite = it.isFavorite
                    checkMovieExistDb = true
                    Log.d("CachingDb", "db")
                }
                .map {
                    return@map MovieDetails(
                            it.id, it.overview, it.posterPath, it.backdropPath, it.releaseDate, it.runtime,
                            it.tagline, it.title, it.rating, listGenre
                    )
                }
    }

    fun insertMovieFavorite(movieID: Int) {
        movieDao.insertMovieFavorite(movieID)
    }

    fun removeMovieFavorite(movieID: Int) {
        movieDao.remoteMovieFavorite(movieID)
    }

    fun checkMovieFavorite(movieID: Int): LiveData<MovieDetailLocal> {
        return movieDao.checkMovieIsFavorite(movieID)
    }
}