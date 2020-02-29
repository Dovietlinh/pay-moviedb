package com.example.movietv.repository.movieSearchRepository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.movietv.data.remote.api.ApiService
import com.example.movietv.common.Constants.Companion.FIRST_PAGE
import com.example.movietv.data.remote.entity.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieSearchDataSource(
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