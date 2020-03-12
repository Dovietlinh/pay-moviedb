package com.example.movietv.repository

import androidx.lifecycle.LiveData
import com.example.movietv.api.ApiService
import com.example.movietv.model.remote.GenreRespose
import io.reactivex.disposables.CompositeDisposable

class GenreRepository(private val apiService: ApiService) {
    fun fetchGenre(
        compositeDisposable: CompositeDisposable
    ): LiveData<GenreRespose> {

        val genreDataSource = GenreDataSource(apiService, compositeDisposable)
        genreDataSource.fetchGenre()

        return genreDataSource.genreResponse

    }

}