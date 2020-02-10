package com.example.movietv.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movietv.api.RestClient
import com.example.movietv.model.MovieDetails
import com.example.movietv.repository.MovieDetailsRepository
import io.reactivex.disposables.CompositeDisposable

class DetailsViewModel(movieID: Int) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val apiService = RestClient.getClient()
    private val movieRepository: MovieDetailsRepository=MovieDetailsRepository(apiService)
    val movieDetails: LiveData<MovieDetails> by lazy {
        movieRepository.fetchMovieDetails(compositeDisposable, movieID)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}