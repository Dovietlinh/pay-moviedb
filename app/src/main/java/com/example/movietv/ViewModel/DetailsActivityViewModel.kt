package com.example.movietv.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movietv.Model.MovieDetails
import com.example.movietv.Repository.MovieDetailsRepository
import io.reactivex.disposables.CompositeDisposable

class DetailsActivityViewModel(private val movieRepository : MovieDetailsRepository, movieID: Int):ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val  movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchMovieDetails(compositeDisposable,movieID)
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}