package com.example.movietv.repository.genreRepository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movietv.data.remote.api.ApiService
import com.example.movietv.data.remote.entity.GenreResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GenreDataSource (
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) {
    private val genreListResponse = MutableLiveData<GenreResponse>()
    val genreResponse: LiveData<GenreResponse> get() = genreListResponse

    fun fetchGenre() {
        try {
            compositeDisposable.add(
                apiService.getListGenre()
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            genreListResponse.postValue(it)
                        },
                        {
                            Log.e("GenreDataSource", it.message)
                        }
                    )
            )
        } catch (e: Exception) {
            Log.e("GenreDataSource", e.message)
        }


    }

}