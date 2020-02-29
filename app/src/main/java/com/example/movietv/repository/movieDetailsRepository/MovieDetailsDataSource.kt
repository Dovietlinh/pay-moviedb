package com.example.movietv.repository.movieDetailsRepository

import android.util.Log
import com.example.movietv.data.remote.api.ApiService
import com.example.movietv.data.remote.entity.MovieDetails
import com.example.movietv.data.remote.entity.TrailerResponse
import io.reactivex.Observable
import javax.inject.Inject

class MovieDetailsDataSource @Inject constructor(private val apiService: ApiService) {
    init {
        Log.d("TAG","MovieDetailsDataSource")
    }
    fun fetchMovie(movieId: Int): Observable<MovieDetails> {
        return apiService.getMovieDetails(movieId)
    }

    fun fetchTrailer(movieId: Int): Observable<TrailerResponse> {
        return apiService.getTrailers(movieId)
    }

}