package com.example.movietv.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movietv.R
import com.example.movietv.adapter.TrailerAdapter
import com.example.movietv.common.Constants.Companion.MOVIE_ID
import com.example.movietv.common.Constants.Companion.POSTER_BASE_URL
import com.example.movietv.model.local.MovieDetailLocal
import com.example.movietv.model.remote.MovieDetails
import com.example.movietv.viewModel.DetailsViewModel
import kotlinx.android.synthetic.main.activity_details.dateDetails
import kotlinx.android.synthetic.main.activity_details.icCheckMyList
import kotlinx.android.synthetic.main.activity_details.imagePoster
import kotlinx.android.synthetic.main.activity_details.imageViewBanner
import kotlinx.android.synthetic.main.activity_details.progress_bar
import kotlinx.android.synthetic.main.activity_details.rateDetails
import kotlinx.android.synthetic.main.activity_details.rcvTrailer
import kotlinx.android.synthetic.main.activity_details.taglineDetails
import kotlinx.android.synthetic.main.activity_details.timeDetails
import kotlinx.android.synthetic.main.activity_details.titleDetails
import kotlinx.android.synthetic.main.activity_details.txtOverview

class DetailsActivity : AppCompatActivity() {
    private var checkIconFavorite: Boolean = false
    private lateinit var movieDetailLocal: MovieDetailLocal
    private lateinit var viewModel: DetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initValueCreateView()
    }

    fun addFavorite(view: View) {
        checkIconFavorite = if (!checkIconFavorite) {
            viewModel.insertFavorite(movieDetailLocal.id)
            icCheckMyList.setBackgroundResource(R.drawable.ic_my_list_check)
            Toast.makeText(this, "Added to My List", Toast.LENGTH_LONG).show()
            true
        } else {
            viewModel.removeFavorite(movieDetailLocal.id)
            icCheckMyList.setBackgroundResource(R.drawable.ic_my_list_add)
            Toast.makeText(this, "Removed from My List", Toast.LENGTH_LONG).show()
            false
        }
    }

    private fun initValueCreateView() {
        val uri: Uri? = intent.data
        var movieId: String? = null
        if (uri != null) {
            if ("http" == uri.scheme && "movieDb.com" == uri.host) {
                try {
                    movieId = uri.getQueryParameter("movieId")
                    if (movieId != null) {
                        viewModel = getViewModel(movieId.toInt())
                    }

                } catch (e: Exception) {
                    Log.d("HomeFragment", e.message)
                }
            }
        } else {
            val movieId: Int = intent.getIntExtra(MOVIE_ID, 1)
            viewModel = getViewModel(movieId)
            progress_bar.visibility = View.VISIBLE
        }
        viewModel.getFavorite.observe(this, Observer {
            if (it != null) {
                icCheckMyList.setBackgroundResource(R.drawable.ic_my_list_check)
                checkIconFavorite = true
            }
        })
        viewModel.movieDetails.observe(this, Observer {
            movieDetailLocal = MovieDetailLocal(
                    it.id, it.overview, it.posterPath, it.backdropPath, it.releaseDate, it.tagline,
                    it.title, it.runtime, it.rating
            )
            loadDataView(it)
            progress_bar.visibility = View.GONE
        })

        val trailerAdapter = TrailerAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
        rcvTrailer?.apply {
            this.setHasFixedSize(true)
            this.layoutManager = linearLayoutManager
            this.itemAnimator = DefaultItemAnimator()
            rcvTrailer.adapter = trailerAdapter
        }

        viewModel.getTrailers.observe(this, Observer {
            trailerAdapter.submitList(it.trailerList)
        })
    }

    private fun loadDataView(movieDetails: MovieDetails) {

        titleDetails.text = movieDetails.title
        taglineDetails.text = movieDetails.tagline
        val tagline = movieDetails.runtime.toString() + " " + getString(R.string.minutes)
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
                return DetailsViewModel(application, movieID) as T
            }
        })[DetailsViewModel::class.java]
    }
}
