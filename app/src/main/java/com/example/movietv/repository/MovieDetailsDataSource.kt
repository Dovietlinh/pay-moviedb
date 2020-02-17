package com.example.movietv.repository

import com.example.movietv.api.ApiService
import com.example.movietv.model.remote.MovieDetails
import io.reactivex.Observable

class MovieDetailsDataSource(
    private val apiService: ApiService
) {
    fun fetchMovie(movieId: Int): Observable<MovieDetails> {
        return apiService.getMovieDetails(movieId)

    }

}