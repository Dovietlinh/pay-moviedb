package com.example.movietv.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietv.api.RestClient
import com.example.movietv.model.local.MovieDetailEntity
import com.example.movietv.model.remote.MovieDetails
import com.example.movietv.model.roomDatabase.MovieRoomDatabase
import com.example.movietv.repository.MovieDetailsRepository
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application, movieID: Int) :
    ViewModel() {
    private val movieDao = MovieRoomDatabase.getDB(application).movieDao()

    private val compositeDisposable = CompositeDisposable()
    var apiService = RestClient.getClient()
    private val movieRepository: MovieDetailsRepository =
        MovieDetailsRepository(apiService, movieDao)

    val movieDetails: LiveData<MovieDetails> by lazy {
        val movieDetailsCache = MutableLiveData<MovieDetails>()
        movieRepository.fetchMovieCaching(movieID).subscribe {
            movieDetailsCache.postValue(it)
        }
        movieDetailsCache
    }

    fun insertFavorite(movieID: Int) = viewModelScope.launch(Dispatchers.IO) {
        movieRepository.insertMovieFavorite(movieID)
    }

    fun removeFavorite(movieID: Int) = viewModelScope.launch(Dispatchers.IO) {
        movieRepository.removeMovieFavorite(movieID)
    }

    val getFavorite: LiveData<MovieDetailEntity> = movieRepository.checkMovieFavorite(movieID)


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}