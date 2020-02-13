package com.example.movietv.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movietv.api.ApiService
import com.example.movietv.model.remote.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable,
    private val type: String
) : DataSource.Factory<Int, Movie>() {

    private val movieLiveDataSource = MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable, type)
        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}