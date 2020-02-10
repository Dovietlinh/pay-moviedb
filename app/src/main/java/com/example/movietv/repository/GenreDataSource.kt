package com.example.movietv.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movietv.api.ApiService
import com.example.movietv.model.GenreRespose
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GenreDataSource (
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) {
    private val genreListResponse = MutableLiveData<GenreRespose>()
    val genreResponse: LiveData<GenreRespose> get() = genreListResponse

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