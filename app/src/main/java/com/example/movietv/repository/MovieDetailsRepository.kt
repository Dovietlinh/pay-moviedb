package com.example.movietv.repository

import androidx.lifecycle.LiveData
import com.example.movietv.api.ApiService
import com.example.movietv.model.dao.MovieDao
import com.example.movietv.model.local.MovieDetailEntity
import com.example.movietv.model.remote.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: ApiService, private val movieDao: MovieDao) {

    fun fetchMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetails> {
        val movieDetailsDataSource = MovieDetailsDataSource(apiService, compositeDisposable)
        movieDetailsDataSource.fetchMovieDetails(movieId)
        return movieDetailsDataSource.movieResponse
    }

    val getAllFavorite: LiveData<List<MovieDetailEntity>> = movieDao.getAllFavorite()

    fun insertMovieFavorite(movie: MovieDetailEntity) {
        movieDao.createFavorite(movie)
    }

    fun findMovieFavorite(movieID: Int): MovieDetailEntity {
        return movieDao.findMovieById(movieID)
    }

    fun removeMovieFavorite(movie: MovieDetailEntity) {
        movieDao.deleteFavorite(movie)
    }

    fun checkMovieFavorite(movieID: Int): LiveData<MovieDetailEntity> {
        return movieDao.checkFavorite(movieID)
    }
}