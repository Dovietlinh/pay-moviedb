package com.example.movietv.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movietv.api.RestClient
import com.example.movietv.model.local.MovieDetailEntity
import com.example.movietv.model.remote.MovieDetails
import com.example.movietv.model.roomDatabase.MovieRoomDatabase
import com.example.movietv.repository.MovieDetailsRepository
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailsViewModel(application: Application, movieID: Int) :
    ViewModel() {

    private var parentJob = Job()
    private val cContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(cContext)
    private val movieDao = MovieRoomDatabase.getDB(application).movieDao()

    private val compositeDisposable = CompositeDisposable()
    val apiService = RestClient.getClient()
    private val movieRepository: MovieDetailsRepository =
        MovieDetailsRepository(apiService, movieDao)

    val movieDetails: LiveData<MovieDetails> by lazy {
        movieRepository.fetchMovieDetails(compositeDisposable, movieID)
    }

    fun insertFavorite(movie: MovieDetailEntity) = scope.launch(Dispatchers.IO) {
        movieRepository.insertMovieFavorite(movie)
    }

    fun removeFavorite(movieID: Int) = scope.launch(Dispatchers.IO) {
        val movieDetailEntity = movieRepository.findMovieFavorite(movieID)
        movieRepository.removeMovieFavorite(movieDetailEntity)
    }

    val getFavorite: LiveData<MovieDetailEntity> = movieRepository.checkMovieFavorite(movieID)


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        parentJob.cancel()
    }
}