package com.example.movietv.repository.movieDetailsRepository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.movietv.data.local.dao.MovieDao
import com.example.movietv.data.local.entity.MovieDetailLocal
import com.example.movietv.data.local.entity.TrailerResponseLocal
import com.example.movietv.data.remote.entity.Genre
import com.example.movietv.data.remote.entity.MovieDetails
import com.example.movietv.data.remote.entity.Trailer
import com.example.movietv.data.remote.entity.TrailerResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
    private val movieDetailsDataSource: MovieDetailsDataSource,
    private val movieDao: MovieDao
) {
    init {
        Log.d("TAG", "MovieDetailsRepository")
    }

    private var checkMovieIsFavorite = false
//    private val movieDetailsDataSource =
//        MovieDetailsDataSource(
//            apiService
//        )

    fun fetchTrailer(movieID: Int): Observable<TrailerResponse> {
        var getApi: Observable<TrailerResponse> = getTrailerRemote(movieID)
        var getDb: Observable<TrailerResponse> = getTrailerLocal(movieID)
        return Observable.mergeDelayError(getDb, getApi)
    }

    private fun getTrailerRemote(movieID: Int): Observable<TrailerResponse> {
        return movieDetailsDataSource.fetchTrailer(movieID).subscribeOn(Schedulers.io())
            .doOnNext {
                for (t in it.trailerList) {
                    movieDao.saveTrailer(
                        TrailerResponseLocal(
                            t.id,
                            it.id,
                            t.trailerName,
                            t.key
                        )
                    )
                }
            }
    }

    private fun getTrailerLocal(movieID: Int): Observable<TrailerResponse> {
        return movieDao.getAllTrailerByID(movieID)
            .map {
                val listTrailer: MutableList<Trailer> = mutableListOf()
                for (t in it) {
                    listTrailer.add(
                        Trailer(
                            t.id,
                            t.key,
                            t.name
                        )
                    )
                }
                return@map TrailerResponse(
                    movieID,
                    listTrailer
                )
            }
    }

    fun getMovieCaching(movieID: Int): Observable<MovieDetails> {
        var getApi: Observable<MovieDetails> = getMovieRemote(movieID)
        var getDb: Observable<MovieDetails> = getMovieLocal(movieID)
        return Observable.mergeDelayError(getDb, getApi)
    }

    private fun getMovieRemote(movieID: Int): Observable<MovieDetails> {
        return movieDetailsDataSource.fetchMovie(movieID).subscribeOn(Schedulers.io())
//            .delay(2L, java.util.concurrent.TimeUnit.SECONDS)
            .doOnNext {
                movieDao.saveMovie(
                    MovieDetailLocal(
                        it.id, it.overview, it.posterPath, it.backdropPath, it.releaseDate,
                        it.tagline, it.title, it.runtime, it.rating, checkMovieIsFavorite
                    )
                )
            }
    }

    private fun getMovieLocal(movieID: Int): Observable<MovieDetails> {
        val listGenre: MutableList<Genre> = mutableListOf()
        return movieDao.getMovie(movieID)
            .doOnNext {
                checkMovieIsFavorite = it.isFavorite
            }
            .map {
                return@map MovieDetails(
                    it.id, it.overview, it.posterPath, it.backdropPath, it.releaseDate, it.runtime,
                    it.tagline, it.title, it.rating, listGenre
                )
            }
    }

    fun insertMovieFavorite(movieID: Int) {
        movieDao.insertMovieFavorite(movieID)
    }

    fun removeMovieFavorite(movieID: Int) {
        movieDao.remoteMovieFavorite(movieID)
    }

    fun getMovieFavorite(movieID: Int): LiveData<MovieDetailLocal> {
        return movieDao.checkMovieIsFavorite(movieID)
    }

}