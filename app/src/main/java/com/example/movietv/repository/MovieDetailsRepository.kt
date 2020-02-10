package com.example.movietv.repository

import androidx.lifecycle.LiveData
import com.example.movietv.api.ApiService
import com.example.movietv.model.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: ApiService) {
    fun fetchMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetails> {

        val movieDetailsDataSource = MovieDetailsDataSource(apiService, compositeDisposable)
        movieDetailsDataSource.fetchMovieDetails(movieId)

        return movieDetailsDataSource.movieResponse

    }

}