package com.example.movietv.repository.movieRepository

import com.example.movietv.data.remote.entity.MovieResponse
import io.reactivex.Observable

interface IMovieDataSource {
    fun getMovieLocal(page: Int): Observable<MovieResponse>
    fun getMovieRemote(page: Int): Observable<MovieResponse>
    fun getMovieCaching(page: Int): Observable<MovieResponse>
}