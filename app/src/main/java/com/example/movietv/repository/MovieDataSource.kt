package com.example.movietv.repository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.movietv.api.ApiService
import com.example.movietv.common.Constants.Companion.FIRST_PAGE
import com.example.movietv.model.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable,
    private val type: String
) : PageKeyedDataSource<Int, Movie>() {
    private var page = FIRST_PAGE
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        compositeDisposable.add(
            apiService.getMoviesByType(type, page)
//                .observeOn(AndroidSchedulers.mainThread())
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
            apiService.getMoviesByType(type, params.key)
//                .observeOn(AndroidSchedulers.mainThread())
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