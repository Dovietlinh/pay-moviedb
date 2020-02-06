package com.example.movietv.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietv.Adapter.MoviePagedListAdapter
import com.example.movietv.Api.ApiService
import com.example.movietv.Api.RestClient
import com.example.movietv.Model.Movie
import com.example.movietv.R
import com.example.movietv.Repository.MoviePagedListRepository
import com.example.movietv.ViewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome : Fragment() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var movieListAdapter: MoviePagedListAdapter
    private lateinit var movieRepository: MoviePagedListRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        val apiService: ApiService = RestClient.getClient()
        movieRepository = MoviePagedListRepository(apiService)
        viewModel = getViewModel()
        //set Adapter movieList popular
        movieListAdapter = MoviePagedListAdapter(context!!)
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rcv_popular?.apply {
            this.setHasFixedSize(true)
            this.layoutManager = linearLayoutManager
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = movieListAdapter
        }
        viewModel.moviePagedList.observe(viewLifecycleOwner, Observer<PagedList<Movie>> {
            movieListAdapter.submitList(it)
        })
        //set Adapter movieList now playing
        val movieListAdapterNowPlaying = MoviePagedListAdapter(context!!)
        rcv_nowPlaying?.apply {
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = movieListAdapterNowPlaying
        }
        viewModel.moviePagedListNowPlaying.observe(viewLifecycleOwner, Observer<PagedList<Movie>> {
            movieListAdapterNowPlaying.submitList(it)
        })
        //set Adapter movieList upcoming
        val movieListAdapterUpcoming = MoviePagedListAdapter(context!!)
        rcv_upcoming?.apply {
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = movieListAdapterUpcoming
        }
        viewModel.moviePagedListUpcoming.observe(viewLifecycleOwner, Observer<PagedList<Movie>> {
            movieListAdapterUpcoming.submitList(it)
        })
        //set Adapter movieList topRate
        val movieListAdapterTopRate = MoviePagedListAdapter(context!!)
        rcv_topRate?.apply {
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = movieListAdapterTopRate
        }
        viewModel.moviePagedListTopRate.observe(viewLifecycleOwner, Observer<PagedList<Movie>> {
            movieListAdapterTopRate.submitList(it)
        })
    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}