package com.example.movietv.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movietv.Model.Movie
import com.example.movietv.Repository.MoviePagedListRepository
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val moviePagedListRepository: MoviePagedListRepository):ViewModel() {
    private val compositeDisposable=CompositeDisposable()
    val  moviePagedList : LiveData<PagedList<Movie>> by lazy {
        moviePagedListRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}