package com.example.themoviedb.Api

import com.example.movietv.Model.MovieRespose
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

open interface ApiService {
    @GET("movie/popular")
    fun getMoviePopular(@Query("page") page: Int): Observable<MovieRespose>
}