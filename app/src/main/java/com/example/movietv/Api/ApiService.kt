package com.example.themoviedb.Api

import com.example.movietv.Model.MovieDetails
import com.example.movietv.Model.MovieRespose
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/{path}")
    fun getMoviesByType(@Path("path") path:String, @Query("page") page: Int): Observable<MovieRespose>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Observable<MovieDetails>
}