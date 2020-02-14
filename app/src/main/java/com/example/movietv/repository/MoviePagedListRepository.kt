package com.example.movietv.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movietv.api.ApiService
import com.example.movietv.common.Constants.Companion.POST_PER_PAGE
import com.example.movietv.model.dao.MovieDao
import com.example.movietv.model.local.MovieDetailEntity
import com.example.movietv.model.remote.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService: ApiService, movieDao: MovieDao) {
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

    fun fetchLiveMovieSearchPagedList(
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

    val getAllFavorite: LiveData<List<MovieDetailEntity>> = movieDao.getAllFavorite()
    // Todo
//    fun getNetworkState(): LiveData<NetworkState> {
//        return Transformations.switchMap<MovieDataSource, NetworkState>(
//            moviesDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState
//        )
//    }
}