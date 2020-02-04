package com.example.themoviedb.Api

import com.example.movietv.Model.MovieRespose
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/{path}")
    fun getMoviesByType(@Path("path") path:String, @Query("page") page: Int): Observable<MovieRespose>
}