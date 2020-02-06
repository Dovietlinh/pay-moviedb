package com.example.movietv.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.movietv.Common.Constants.Companion.FIRST_PAGE
import com.example.movietv.Model.Movie
import com.example.themoviedb.Api.ApiService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieSearchDataSource (
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable,
    private val searchString: String
) : PageKeyedDataSource<Int, Movie>() {
    private var page = FIRST_PAGE
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        compositeDisposable.add(
            apiService.getMoviesSearch(searchString, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movieList, null, page + 1)
                    },
                    {
                        Log.e("MovieDataSource", it.message)
                    })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        compositeDisposable.add(
            apiService.getMoviesSearch(searchString, params.key)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.totalPages >= params.key) {
                        callback.onResult(it.movieList, params.key + 1)
                    } else {
                    }
                },
                    {
                        Log.e("MovieDataSource", it.message)
                    })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}