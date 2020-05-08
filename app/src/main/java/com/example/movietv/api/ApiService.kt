package com.example.movietv.api

import com.example.movietv.model.remote.GenreRespose
import com.example.movietv.model.remote.MovieDetails
import com.example.movietv.model.remote.MovieRespose
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//Test push git
interface ApiService {
    @GET("movie/{path}")
    fun getMoviesByType(@Path("path") path:String, @Query("page") page: Int): Observable<MovieRespose>
    @GET("genre/movie/list")
    fun getListGenre(): Observable<GenreRespose>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Observable<MovieDetails>

    @GET("search/multi")
    fun getMoviesSearch(@Query("query") query:String, @Query("page") id: Int): Observable<MovieRespose>
}