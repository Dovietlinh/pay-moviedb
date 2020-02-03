package com.example.themoviedb.Api

import com.example.movietv.Model.MovieRespose
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getMoviePopular(@Query("page") page: Int): Single<MovieRespose>
    @GET("movie/now_playing")
    fun getMovieNowPlaying(@Query("page") page: Int): Single<MovieRespose>
    @GET("movie/upcoming")
    fun getMovieUpcoming(@Query("page") page: Int): Single<MovieRespose>
    @GET("movie/top_rated")
    fun getMovieTopRated(@Query("page") page: Int): Single<MovieRespose>
}