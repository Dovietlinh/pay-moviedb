package com.example.movietv.Repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.movietv.Model.Movie
import com.example.themoviedb.Api.ApiService
import com.example.themoviedb.Api.FIRST_PAGE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable,
    private val type:Int
) : PageKeyedDataSource<Int, Movie>() {
    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        if(type==1){
            compositeDisposable.add(
                apiService.getMoviePopular(page)
//                .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            callback.onResult(it.movieList, null, page + 1)
                            networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message)
                        })
            )
        }
        if(type==2){
            compositeDisposable.add(
                apiService.getMovieNowPlaying(page)
//                .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            callback.onResult(it.movieList, null, page + 1)
                            networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message)
                        })
            )
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        compositeDisposable.add(
            apiService.getMoviePopular(params.key)
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.totalPages >= params.key) {
                        callback.onResult(it.movieList, params.key + 1)
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        networkState.postValue(NetworkState.ENDOFLIST)
                    }
                },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message)
                    })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
}