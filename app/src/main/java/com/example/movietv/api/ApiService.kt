package com.example.movietv.api

import com.example.movietv.model.remote.GenreResponse
import com.example.movietv.model.remote.MovieDetails
import com.example.movietv.model.remote.MovieResponse
import com.example.movietv.model.remote.TrailerResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/{path}")
    fun getMoviesByType(@Path("path") path: String, @Query("page") page: Int): Observable<MovieResponse>

    @GET("genre/movie/list")
    fun getListGenre(): Observable<GenreResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Observable<MovieDetails>

    @GET("search/multi")
    fun getMoviesSearch(@Query("query") query: String, @Query("page") id: Int): Observable<MovieResponse>

    @GET("movie/{movie_id}/videos")
    fun getTrailers(@Path("movie_id") id: Int): Observable<TrailerResponse>
}