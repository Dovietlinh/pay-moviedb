package com.example.movietv.view

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
import com.example.movietv.R
import com.example.movietv.adapter.MovieSearchPagedListAdapter
import com.example.movietv.viewModel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_search.edtSearchMulti
import kotlinx.android.synthetic.main.fragment_search.rcvListMovieSearch

class SearchFragment : Fragment() {
    private lateinit var viewModel: MovieViewModel
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
        initViewAdapter()
    }

    private fun initViewAdapter() {
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
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //do nothing
            }

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