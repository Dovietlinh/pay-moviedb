package com.example.movietv.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movietv.api.ApiService
import com.example.movietv.api.RestClient
import com.example.movietv.common.Constants
import com.example.movietv.model.local.MovieDetailEntity
import com.example.movietv.model.remote.Movie
import com.example.movietv.model.roomDatabase.MovieRoomDatabase
import com.example.movietv.repository.MoviePagedListRepository
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(application: Application) :
    ViewModel() {
    private val movieDao = MovieRoomDatabase.getDB(application).movieDao()

    private val compositeDisposable = CompositeDisposable()
    val apiService: ApiService = RestClient.getClient()
    private val moviePagedListRepository = MoviePagedListRepository(apiService, movieDao)
    val moviePagedList: LiveData<PagedList<Movie>> by lazy {
        moviePagedListRepository.fetchLiveMoviePagedList(compositeDisposable, Constants.API_POPULAR)
    }

    fun movieSearchPagedList(searchString: String): LiveData<PagedList<Movie>> {
        return moviePagedListRepository.fetchLiveMovieSearchPagedList(
            compositeDisposable,
            searchString
        )
    }

    val moviePagedListNowPlaying: LiveData<PagedList<Movie>> by lazy {
        moviePagedListRepository.fetchLiveMoviePagedList(
            compositeDisposable,
            Constants.API_NOW_PLAYING
        )
    }
    val moviePagedListUpcoming: LiveData<PagedList<Movie>> by lazy {
        moviePagedListRepository.fetchLiveMoviePagedList(
            compositeDisposable,
            Constants.API_UPCOMING
        )
    }
    val moviePagedListTopRate: LiveData<PagedList<Movie>> by lazy {
        moviePagedListRepository.fetchLiveMoviePagedList(compositeDisposable, Constants.API_TOPRATE)
    }

    fun moviePageListCategory(type: String): LiveData<PagedList<Movie>> {
        return moviePagedListRepository.fetchLiveMoviePagedList(compositeDisposable, type)
    }

    val getAllFavorite: LiveData<List<MovieDetailEntity>> by lazy {
        moviePagedListRepository.getAllFavorite
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}