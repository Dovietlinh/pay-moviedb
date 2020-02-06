package com.example.movietv.View

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietv.Adapter.MovieSearchPagedListAdapter
import com.example.movietv.Api.ApiService
import com.example.movietv.Api.RestClient
import com.example.movietv.R
import com.example.movietv.Repository.MoviePagedListRepository
import com.example.movietv.ViewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class FragmentSearch : Fragment() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var movieRepository: MoviePagedListRepository
    private lateinit var movieListAdapter: MovieSearchPagedListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        val apiService: ApiService = RestClient.getClient()
        movieRepository = MoviePagedListRepository(apiService)
        viewModel = getViewModel()
        movieListAdapter = MovieSearchPagedListAdapter(context!!)
        val linearLayoutManager = LinearLayoutManager(context)

        rcvListMovieSearch?.apply {
            this.setHasFixedSize(true)
            this.layoutManager = linearLayoutManager
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = movieListAdapter
        }
        edtSearchMulti.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.movieSearchPagedList(s.toString()).observe(viewLifecycleOwner, Observer {
                    movieListAdapter.submitList(it)
                })
            }

            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

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