package com.example.movietv.Repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movietv.Api.ApiService
import com.example.movietv.Common.Constants.Companion.POST_PER_PAGE
import com.example.movietv.Model.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService: ApiService) {
    private lateinit var moviePagedList: LiveData<PagedList<Movie>>
    private lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(
        compositeDisposable: CompositeDisposable,
        type: String
    ): LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable, type)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

//    fun getNetworkState(): LiveData<NetworkState> {
//        return Transformations.switchMap<MovieDataSource, NetworkState>(
//            moviesDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState
//        )
//    }
}