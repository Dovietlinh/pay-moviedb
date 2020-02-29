package com.example.movietv.repository.movieRepository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movietv.common.Constants.Companion.POST_PER_PAGE
import com.example.movietv.data.local.dao.MovieDao
import com.example.movietv.data.local.entity.MovieDetailLocal
import com.example.movietv.data.remote.api.ApiService
import com.example.movietv.data.remote.entity.Movie
import com.example.movietv.repository.movieSearchRepository.MovieSearchDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService: ApiService, private val movieDao: MovieDao) {
    private lateinit var moviePagedList: LiveData<PagedList<Movie>>

    fun fetchLiveMoviePagedList(
        compositeDisposable: CompositeDisposable,
        type: String
    ): LiveData<PagedList<Movie>> {
        val moviesDataSourceFactory =
            MovieDataSourceFactory(apiService, movieDao, compositeDisposable, type)

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
            MovieSearchDataSourceFactory(
                apiService,
                compositeDisposable,
                searchString
            )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesSearchDataSourceFactory, config).build()

        return moviePagedList
    }

    val getAllFavorite: LiveData<List<MovieDetailLocal>> = movieDao.getAllFavorite()

//    fun getNetworkState(): LiveData<NetworkState> {
//        return Transformations.switchMap<MovieDataSource, NetworkState>(
//            moviesDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState
//        )
//    }
}