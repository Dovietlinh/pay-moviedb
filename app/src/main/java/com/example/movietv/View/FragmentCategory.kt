package com.example.movietv.View

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietv.Adapter.MoviePagedListAdapter
import com.example.movietv.R
import com.example.movietv.Repository.MoviePagedListRepository
import com.example.movietv.ViewModel.MainActivityViewModel
import com.example.themoviedb.Api.ApiService
import com.example.themoviedb.Api.RestClient
import kotlinx.android.synthetic.main.fragment_category.*

class FragmentCategory : Fragment() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var movieListAdapter: MoviePagedListAdapter
    private lateinit var movieRepository: MoviePagedListRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_category, container, false)

        val apiService : ApiService = RestClient.getClient()

        movieRepository = MoviePagedListRepository(apiService)
        viewModel= getViewModel()
        initAdapter()
        return view
    }
    private fun initAdapter(){
        var rcvCategory : RecyclerView? = view?.findViewById(R.id.rcvCategory)
        movieListAdapter=MoviePagedListAdapter(context as Context)
        val gridLayoutManager=GridLayoutManager(context,3)
        rcvCategory?.layoutManager=gridLayoutManager
        rcvCategory?.setHasFixedSize(true)
        rcvCategory?.adapter=movieListAdapter
        viewModel.moviePagedList.observe(this, Observer {
            movieListAdapter.submitList(it)
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