package com.example.movietv.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movietv.Model.Movie
import com.example.movietv.Repository.MoviePagedListRepository
import com.example.movietv.Repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val moviePagedListRepository: MoviePagedListRepository):ViewModel() {
    private val compositeDisposable=CompositeDisposable()
    val  moviePagedList : LiveData<PagedList<Movie>> by lazy {
        moviePagedListRepository.fetchLiveMoviePagedList(compositeDisposable,1)
    }
    val  moviePagedListNowPlaying : LiveData<PagedList<Movie>> by lazy {
        moviePagedListRepository.fetchLiveMoviePagedList(compositeDisposable,2)
    }
    val  networkState : LiveData<NetworkState> by lazy {
        moviePagedListRepository.getNetworkState()
    }
    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}