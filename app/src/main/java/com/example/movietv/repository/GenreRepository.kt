package com.example.movietv.repository

import androidx.lifecycle.LiveData
import com.example.movietv.api.ApiService
import com.example.movietv.model.remote.GenreResponse
import io.reactivex.disposables.CompositeDisposable

class GenreRepository(private val apiService: ApiService) {
    fun fetchGenre(
        compositeDisposable: CompositeDisposable
    ): LiveData<GenreResponse> {

        val genreDataSource = GenreDataSource(apiService, compositeDisposable)
        genreDataSource.fetchGenre()

        return genreDataSource.genreResponse

    }

}