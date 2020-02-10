package com.example.movietv.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.movietv.R
import com.example.movietv.common.Constants.Companion.MOVIE_ID
import com.example.movietv.common.Constants.Companion.POSTER_BASE_URL
import com.example.movietv.model.MovieDetails
import com.example.movietv.viewModel.DetailsViewModel
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        init()
        viewModel.movieDetails.observe(this, Observer {
            loadDataView(it)
            progress_bar.visibility = View.GONE
        })
    }

    private fun init() {
        val uri: Uri? = intent.data
        var movieId: String? = null
        if (uri != null) {
            if ("http" == uri.scheme && "movieDb.com" == uri.host) {
                try {
                    movieId =  uri.getQueryParameter("movieId")
                    if(movieId != null) {
                        viewModel = getViewModel(movieId.toInt())
                    }

                } catch (e: Exception) {
                    Log.d("HomeFragment",e.message)
                }
            }
        }else{
            val movieId: Int = intent.getIntExtra(MOVIE_ID, 1)
            viewModel = getViewModel(movieId)
            progress_bar.visibility = View.VISIBLE
        }
    }

    private fun loadDataView(movieDetails: MovieDetails) {
        titleDetails.text = movieDetails.title
        taglineDetails.text = movieDetails.tagline
        val tagline=movieDetails.runtime.toString()+" "+ getString(R.string.minutes)
        timeDetails.text = tagline
        dateDetails.text = movieDetails.releaseDate
        rateDetails.text = movieDetails.rating.toString()
        txtOverview.text = movieDetails.overview
//        genreDetails.text=movieDetails.genreList.toString()
        val moviePosterURL = POSTER_BASE_URL + movieDetails.posterPath
        val movieBackdropURL = POSTER_BASE_URL + movieDetails.backdropPath
        Glide.with(this)
            .load(movieBackdropURL)
            .into(imageViewBanner)
        Glide.with(this)
            .load(moviePosterURL)
            .into(imagePoster)
    }

    private fun getViewModel(movieID: Int): DetailsViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(movieID) as T
            }
        })[DetailsViewModel::class.java]
    }
}
