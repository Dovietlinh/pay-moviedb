package com.example.movietv.Repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movietv.Api.ApiService
import com.example.movietv.Model.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieSearchDataSourceFactory(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable,
    private val searchString: String
) : DataSource.Factory<Int, Movie>() {

    private val movieLiveDataSource = MutableLiveData<MovieSearchDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val movieSearchDataSource = MovieSearchDataSource(apiService, compositeDisposable, searchString)
        movieLiveDataSource.postValue(movieSearchDataSource)
        return movieSearchDataSource
    }
}