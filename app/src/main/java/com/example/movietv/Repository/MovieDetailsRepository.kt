package com.example.movietv.Repository

import androidx.lifecycle.LiveData
import com.example.movietv.Api.ApiService
import com.example.movietv.Model.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: ApiService) {
    private lateinit var movieDetailsNetworkDataSource: MovieDetailsDataSource

    fun fetchMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.movieResponse

    }

}