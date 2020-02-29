package com.example.movietv.repository.movieSearchRepository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movietv.data.remote.api.ApiService
import com.example.movietv.data.remote.entity.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieSearchDataSourceFactory(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable,
    private val searchString: String
) : DataSource.Factory<Int, Movie>() {

    private val movieLiveDataSource = MutableLiveData<MovieSearchDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val movieSearchDataSource =
            MovieSearchDataSource(
                apiService,
                compositeDisposable,
                searchString
            )
        movieLiveDataSource.postValue(movieSearchDataSource)
        return movieSearchDataSource
    }
}