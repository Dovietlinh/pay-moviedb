package com.example.movietv.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movietv.data.remote.api.RestClient
import com.example.movietv.data.remote.entity.GenreResponse
import com.example.movietv.repository.genreRepository.GenreRepository
import io.reactivex.disposables.CompositeDisposable

class GenreViewModel :
    ViewModel() {
    val apiService = RestClient().getClient()
    private val genreRepository =
        GenreRepository(apiService)
    private val compositeDisposable = CompositeDisposable()
    val genreList: LiveData<GenreResponse> by lazy {
        genreRepository.fetchGenre(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}