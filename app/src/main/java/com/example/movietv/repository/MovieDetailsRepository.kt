package com.example.movietv.repository

import androidx.lifecycle.LiveData
import com.example.movietv.api.ApiService
import com.example.movietv.model.dao.MovieDao
import com.example.movietv.model.local.MovieDetailLocal
import com.example.movietv.model.local.TrailerResponseLocal
import com.example.movietv.model.remote.Genre
import com.example.movietv.model.remote.MovieDetails
import com.example.movietv.model.remote.Trailer
import com.example.movietv.model.remote.TrailerResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MovieDetailsRepository(apiService: ApiService, private val movieDao: MovieDao) {
    private var checkMovieIsFavorite = false
    private val movieDetailsDataSource = MovieDetailsDataSource(apiService)

    fun fetchTrailer(movieID: Int): Observable<TrailerResponse> {
        var getApi: Observable<TrailerResponse> = getTrailerApi(movieID)
        var getDb: Observable<TrailerResponse> = getTrailerDb(movieID)
        return Observable.mergeDelayError(getDb, getApi)
    }

    private fun getTrailerApi(movieID: Int): Observable<TrailerResponse> {
        return movieDetailsDataSource.fetchTrailer(movieID).subscribeOn(Schedulers.io())
            .doOnNext {
                for (t in it.trailerList) {
                    movieDao.saveTrailer(TrailerResponseLocal(t.id, it.id, t.trailerName, t.key))
                }
            }
    }

    private fun getTrailerDb(movieID: Int): Observable<TrailerResponse> {
        return movieDao.getAllTrailerByID(movieID)
            .map {
                val listTrailer: MutableList<Trailer> = mutableListOf()
                for (t in it) {
                    listTrailer.add(Trailer(t.id, t.key, t.name))
                }
                return@map TrailerResponse(movieID, listTrailer)
            }
    }

    fun fetchMovieCaching(
        movieID: Int
    ): Observable<MovieDetails> {
        var getApi: Observable<MovieDetails> = getMovieApi(movieID)
        var getDb: Observable<MovieDetails> = getMovieDb(movieID)
        return Observable.mergeDelayError(getDb, getApi)
    }

    private fun getMovieApi(
        movieID: Int
    ): Observable<MovieDetails> {
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

    private fun getMovieDb(movieID: Int): Observable<MovieDetails> {
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

    fun checkMovieFavorite(movieID: Int): LiveData<MovieDetailLocal> {
        return movieDao.checkMovieIsFavorite(movieID)
    }
}