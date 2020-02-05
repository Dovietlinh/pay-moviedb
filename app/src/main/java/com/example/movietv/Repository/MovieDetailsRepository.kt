package com.example.movietv.Repository

import androidx.lifecycle.LiveData
import com.example.movietv.Model.MovieDetails
import com.example.themoviedb.Api.ApiService

import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (private val apiService : ApiService) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsDataSource

    fun fetchMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.movieResponse

    }

}