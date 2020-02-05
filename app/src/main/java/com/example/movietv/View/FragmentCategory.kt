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
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietv.Adapter.MoviePagedListAdapter
import com.example.movietv.Model.Movie
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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }
    private fun initAdapter(){
//        var rcvCategory : RecyclerView? = view?.findViewById(R.id.rcvCategory)
        val apiService : ApiService = RestClient.getClient()
        movieRepository = MoviePagedListRepository(apiService)
        viewModel= getViewModel()

        movieListAdapter=MoviePagedListAdapter(context!!)
        val gridLayoutManager=GridLayoutManager(context,3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieListAdapter.getItemViewType(position)
                if (viewType == movieListAdapter.MOVIE_VIEW_TYPE) return  1
                else return 3
            }
        }
        rcvCategory?.apply {
            this.setHasFixedSize(true)
            this.layoutManager=gridLayoutManager
            this.itemAnimator=DefaultItemAnimator()
            this.adapter=movieListAdapter
        }
        viewModel.moviePagedList.observe(viewLifecycleOwner, Observer <PagedList<Movie>>{
            movieListAdapter.submitList(it)
            progress_bar.visibility=View.GONE
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