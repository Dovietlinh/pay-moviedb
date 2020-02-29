package com.example.movietv.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietv.data.local.entity.MovieDetailLocal
import com.example.movietv.data.remote.entity.MovieDetails
import com.example.movietv.data.remote.entity.TrailerResponse
import com.example.movietv.repository.movieDetailsRepository.MovieDetailsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val movieRepository: MovieDetailsRepository) :
    ViewModel() {
//    private val movieDao = MovieRoomDatabase.getDB(application).movieDao()
    init {
        Log.d("TAG", "DetailsViewModel")
    }
    private val compositeDisposable = CompositeDisposable()
//    var apiService = RestClient().getClient()
//    private val movieRepository: MovieDetailsRepository =
//        MovieDetailsRepository(apiService, movieDao)

    fun movieDetails(movieID: Int): LiveData<MovieDetails> {
        val movieDetailsCache = MutableLiveData<MovieDetails>()
        movieRepository.getMovieCaching(movieID).subscribe {
            movieDetailsCache.postValue(it)
        }
        return movieDetailsCache
    }

    fun getTrailers(movieID: Int): LiveData<TrailerResponse> {
        val mutableLiveData = MutableLiveData<TrailerResponse>()
        movieRepository.fetchTrailer(movieID).subscribeOn(Schedulers.io())
            .subscribe {
                mutableLiveData.postValue(it)
            }
        return mutableLiveData
    }

    fun insertFavorite(movieID: Int) = viewModelScope.launch(Dispatchers.IO) {
        movieRepository.insertMovieFavorite(movieID)
    }

    fun removeFavorite(movieID: Int) = viewModelScope.launch(Dispatchers.IO) {
        movieRepository.removeMovieFavorite(movieID)
    }

    fun getFavorite(movieID: Int): LiveData<MovieDetailLocal> {
        return movieRepository.getMovieFavorite(movieID)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}