package com.example.movietv.Repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movietv.Model.Movie
import com.example.themoviedb.Api.ApiService
import io.reactivex.disposables.CompositeDisposable

//lấy dữ liệu bằng dataSource và pagedlist
class MovieDataSourceFactory(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable,
    private val type:Int
) : DataSource.Factory<Int, Movie>() {

    val movieLiveDataSource = MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable,type)
        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}