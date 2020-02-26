package com.example.movietv.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movietv.api.ApiService
import com.example.movietv.common.Constants.Companion.POST_PER_PAGE
import com.example.movietv.model.remote.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService: ApiService) {
    private lateinit var moviePagedList: LiveData<PagedList<Movie>>

    fun fetchLiveMoviePagedList(
        compositeDisposable: CompositeDisposable,
        type: String
    ): LiveData<PagedList<Movie>> {
        val moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable, type)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePagedList
    }

    fun fetchLiveMovieSeachPagedList(
        compositeDisposable: CompositeDisposable,
        searchString: String
    ): LiveData<PagedList<Movie>> {
        val moviesSearchDataSourceFactory =
            MovieSearchDataSourceFactory(apiService, compositeDisposable, searchString)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesSearchDataSourceFactory, config).build()

        return moviePagedList
    }
    // Todo
//    fun getNetworkState(): LiveData<NetworkState> {
//        return Transformations.switchMap<MovieDataSource, NetworkState>(
//            moviesDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState
//        )
//    }
}