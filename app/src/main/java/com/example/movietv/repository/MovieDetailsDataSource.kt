package com.example.movietv.repository

import com.example.movietv.api.ApiService
import com.example.movietv.model.remote.MovieDetails
import com.example.movietv.model.remote.TrailerResponse
import io.reactivex.Observable

class MovieDetailsDataSource(
        private val apiService: ApiService
) {
    fun fetchMovie(movieId: Int): Observable<MovieDetails> {
        return apiService.getMovieDetails(movieId)
    }

    fun fetchTrailer(movieId: Int): Observable<TrailerResponse> {
        return apiService.getTrailers(movieId)
    }

}