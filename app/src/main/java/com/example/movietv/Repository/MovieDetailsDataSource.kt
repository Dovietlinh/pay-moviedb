package com.example.movietv.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movietv.Model.MovieDetails
import com.example.themoviedb.Api.ApiService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsDataSource(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) {
    private val movieDetailsResponse = MutableLiveData<MovieDetails>()
    val movieResponse: LiveData<MovieDetails> get() = movieDetailsResponse

    fun fetchMovieDetails(movieId: Int) {
        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            movieDetailsResponse.postValue(it)
                        },
                        {
                            Log.e("MovieDetailsDataSource", it.message)
                        }
                    )
            )
        } catch (e: Exception) {
            Log.e("MovieDetailsDataSource", e.message)
        }


    }

}