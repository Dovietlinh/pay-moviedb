package com.example.movietv.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movietv.api.ApiService
import com.example.movietv.model.remote.MovieDetails
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsDataSource(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) {
    private val movieDetailsResponse = MutableLiveData<MovieDetails>()
    val movieResponse: LiveData<MovieDetails> get() = movieDetailsResponse

    fun fetchMovie(movieId: Int): Observable<MovieDetails> {
        return apiService.getMovieDetails(movieId)

    }

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