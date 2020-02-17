package com.example.movietv.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movietv.api.ApiService
import com.example.movietv.model.dao.MovieDao
import com.example.movietv.model.local.MovieDetailEntity
import com.example.movietv.model.remote.Genre
import com.example.movietv.model.remote.MovieDetails
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsRepository(private val apiService: ApiService, private val movieDao: MovieDao) {
    private var checkDb = false
    private var checkMovieIsFavorite = false
    fun fetchMovieCaching(
        compositeDisposable: CompositeDisposable,
        movieID: Int
    ): LiveData<MovieDetails> {
        val movieDetailsCaching = MutableLiveData<MovieDetails>()
        var getApi: Observable<MovieDetails> = getMovieApi(compositeDisposable, movieID)
        var getDb: Observable<MovieDetails> = getMovieDb(movieID)
        Observable.mergeDelayError(getDb, getApi)
            .subscribe {
                movieDetailsCaching.postValue(it)
            }
        return movieDetailsCaching
    }

    private fun getMovieApi(
        compositeDisposable: CompositeDisposable,
        movieID: Int
    ): Observable<MovieDetails> {
        val movieDetailsDataSource = MovieDetailsDataSource(apiService, compositeDisposable)
        return movieDetailsDataSource.fetchMovie(movieID).subscribeOn(Schedulers.io())
//            .delay(2L, java.util.concurrent.TimeUnit.SECONDS)
            .doOnNext {
                if (!checkDb) {
                    Log.d("CachingDb", "api add")
                    movieDao.createFavorite(
                        MovieDetailEntity(
                            it.id, it.overview, it.posterPath, it.backdropPath,
                            it.releaseDate, it.tagline, it.title, it.runtime, it.rating
                        )
                    )
                } else {
                    Log.d("CachingDb", "api update")
                    movieDao.updateFavorite(
                        MovieDetailEntity(
                            it.id, it.overview, it.posterPath, it.backdropPath,
                            it.releaseDate, it.tagline, it.title, it.runtime, it.rating,checkMovieIsFavorite
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
                checkDb = true
                Log.d("CachingDb", "db")
            }
            .map {
                return@map MovieDetails(
                    it.id, it.overview, it.posterPath, it.backdropPath, it.releaseDate, it.runtime,
                    it.tagline, it.title, it.rating, listGenre
                )
            }
    }

    fun insertMovieFavorite(movie: MovieDetailEntity) {
        movieDao.updateFavorite(movie)
    }

    fun findMovieFavorite(movieID: Int): MovieDetailEntity {
        return movieDao.findMovieById(movieID)
    }

    fun removeMovieFavorite(movie: MovieDetailEntity) {
        movieDao.updateFavorite(movie)
    }

    fun checkMovieFavorite(movieID: Int): LiveData<MovieDetailEntity> {
        return movieDao.checkMovieIsFavorite(movieID)
    }
}