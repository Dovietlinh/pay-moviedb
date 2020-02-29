package com.example.movietv.repository.movieRepository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movietv.data.local.dao.MovieDao
import com.example.movietv.data.remote.api.ApiService
import com.example.movietv.data.remote.entity.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    private val compositeDisposable: CompositeDisposable,
    private val type: String
) : DataSource.Factory<Int, Movie>() {

    private val movieLiveDataSource = MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, movieDao, compositeDisposable, type)
        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}