package com.example.movietv.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.movietv.Model.MovieDetails
import com.example.movietv.R
import com.example.movietv.Repository.MovieDetailsRepository
import com.example.movietv.ViewModel.DetailsActivityViewModel
import com.example.themoviedb.Api.POSTER_BASE_URL
import com.example.themoviedb.Api.RestClient
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var movieRepository: MovieDetailsRepository
    private lateinit var viewModel: DetailsActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        init()
        viewModel.movieDetails.observe(this, Observer {
            loadDataView(it)
            progress_bar.visibility= View.GONE
        })
    }
    fun init(){
        val movieId: Int = intent.getIntExtra("id",1)
        val apiService=RestClient.getClient()
        movieRepository= MovieDetailsRepository(apiService)
        viewModel=getViewModel(movieId)
        progress_bar.visibility= View.VISIBLE
    }
    fun loadDataView(moviewDetails: MovieDetails){
        titleDetails.text=moviewDetails.title
        taglineDetails.text=moviewDetails.tagline
        timeDetails.text=moviewDetails.runtime.toString() +" minutes"
        dateDetails.text=moviewDetails.releaseDate
        rateDetails.text=moviewDetails.rating.toString()
        txtOverview.text=moviewDetails.overview
//        genreDetails.text=moviewDetails.genreList.toString()
        val moviePosterURL = POSTER_BASE_URL + moviewDetails.posterPath
        val movieBackdropURL = POSTER_BASE_URL + moviewDetails.backdropPath
        Glide.with(this)
            .load(movieBackdropURL)
            .into(imageViewBanner)
        Glide.with(this)
            .load(moviePosterURL)
            .into(imagePoster)
    }
    private fun getViewModel(movieID:Int): DetailsActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return DetailsActivityViewModel(movieRepository,movieID) as T
            }
        })[DetailsActivityViewModel::class.java]
    }
}
