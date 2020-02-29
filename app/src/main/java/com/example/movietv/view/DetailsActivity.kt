package com.example.movietv.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movietv.R
import com.example.movietv.adapter.TrailerAdapter
import com.example.movietv.common.Constants.Companion.MOVIE_ID
import com.example.movietv.common.Constants.Companion.POSTER_BASE_URL
import com.example.movietv.data.local.entity.MovieDetailLocal
import com.example.movietv.data.remote.entity.MovieDetails
import com.example.movietv.viewModel.DetailsViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {
    private var checkIconFavorite: Boolean = false
    private lateinit var movieDetailLocal: MovieDetailLocal

    @Inject
    lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
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
//        val uri: Uri? = intent.data
//        var movieId: String? = null
//        if (uri != null) {
//            if ("http" == uri.scheme && "movieDb.com" == uri.host) {
//                try {
//                    movieId = uri.getQueryParameter("movieId")
//                    if (movieId != null) {
//                        viewModel = getViewModel(movieId.toInt())
//                    }
//
//                } catch (e: Exception) {
//                    Log.d("HomeFragment", e.message)
//                }
//            }
//        } else {
//            val movieId: Int = intent.getIntExtra(MOVIE_ID, 1)
//            viewModel = getViewModel(movieId)
//            progress_bar.visibility = View.VISIBLE
//        }
//
        val movieId: Int = intent.getIntExtra(MOVIE_ID, 1)
        progress_bar.visibility = View.VISIBLE
        viewModel.getFavorite(movieId).observe(this, Observer {
            if (it != null) {
                icCheckMyList.setBackgroundResource(R.drawable.ic_my_list_check)
                checkIconFavorite = true
            }
        })
        viewModel.movieDetails(movieId).observe(this, Observer {
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

        viewModel.getTrailers(movieId).observe(this, Observer {
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
//
//    private fun getViewModel(): DetailsViewModel {
//        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                @Suppress("UNCHECKED_CAST")
//                return DetailsViewModel(application) as T
//            }
//        })[DetailsViewModel::class.java]
//    }
}
