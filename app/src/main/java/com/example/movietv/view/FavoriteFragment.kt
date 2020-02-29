package com.example.movietv.view

import android.os.Bundle
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
import com.example.movietv.R
import com.example.movietv.adapter.MovieFavoriteAdapter
import com.example.movietv.viewModel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_favorite.rcvListMovieFavorite

class FavoriteFragment : Fragment() {
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewAdapter()
    }

    private fun initViewAdapter() {
        viewModel = getViewModel()
        val movieListAdapter = MovieFavoriteAdapter(context!!)
        val linearLayoutManager = LinearLayoutManager(context)
        rcvListMovieFavorite?.apply {
            this.setHasFixedSize(true)
            this.layoutManager = linearLayoutManager
            this.itemAnimator = DefaultItemAnimator()
            rcvListMovieFavorite.adapter = movieListAdapter
        }
        viewModel.getAllFavorite.observe(viewLifecycleOwner, Observer {
            movieListAdapter.submitList(it)
        })
    }

    private fun getViewModel(): MovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(activity!!.application) as T
            }
        })[MovieViewModel::class.java]
    }
}