package com.example.movietv.repository.movieRepository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.movietv.common.Constants.Companion.FIRST_PAGE
import com.example.movietv.data.local.dao.MovieDao
import com.example.movietv.data.local.entity.MovieResponseLocal
import com.example.movietv.data.remote.api.ApiService
import com.example.movietv.data.remote.entity.Movie
import com.example.movietv.data.remote.entity.MovieResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    private val compositeDisposable: CompositeDisposable,
    private val type: String
) : PageKeyedDataSource<Int, Movie>(), IMovieDataSource {
    private var page = FIRST_PAGE

    override fun getMovieLocal(page: Int): Observable<MovieResponse> {
        var totalPage = 0
        return movieDao.getMoviesByPageAndType(page, "%$type%")
            .subscribeOn(Schedulers.io())
            .map { listMovieResponse ->
                val movies: MutableList<Movie> = mutableListOf()
                for (t in listMovieResponse) {
                    movies.add(
                        Movie(
                            t.id,
                            t.posterPath,
                            t.backdropPath,
                            t.title
                        )
                    )
                    totalPage = t.totalPages
                }
                return@map MovieResponse(
                    page,
                    movies,
                    totalPage,
                    0
                )
            }
    }

    override fun getMovieRemote(page: Int): Observable<MovieResponse> {
        return apiService.getMoviesByType(type, page)
            .subscribeOn(Schedulers.io())
            .doOnNext { movieResponse ->
                for (mr in movieResponse.movieList) {
                    var txtBackdropPath = ""
                    mr.backdropPath?.let {
                        txtBackdropPath = it
                    }
                    var txtPosterPath = ""
                    mr.posterPath?.let {
                        txtPosterPath = it
                    }
                    movieDao.saveMovieResponse(
                        MovieResponseLocal(
                            mr.id, movieResponse.page, txtPosterPath, txtBackdropPath,
                            mr.title, type, movieResponse.totalPages
                        )
                    )
                }
            }
    }

    override fun getMovieCaching(page: Int): Observable<MovieResponse> {
        val getApi: Observable<MovieResponse> = getMovieRemote(page)
        val getDb: Observable<MovieResponse> = getMovieLocal(page)
        return Observable.mergeDelayError(getDb, getApi)
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        compositeDisposable.add(
            getMovieCaching(page)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        try {
                            callback.onResult(it.movieList, null, page + 1)
                        } catch (ex: Exception) {
                        }
                    },
                    {
                        Log.e("MovieDataSource", it.message)
                    })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        compositeDisposable.add(
            getMovieCaching(params.key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    try {
                        if (it.totalPages >= params.key) {
                            callback.onResult(it.movieList, params.key + 1)
                        }
                    } catch (ex: Exception) {
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