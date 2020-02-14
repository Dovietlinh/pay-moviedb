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
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movietv.R
import com.example.movietv.adapter.MoviePagedListAdapter
import com.example.movietv.common.Constants.Companion.MOVIE_VIEW_TYPE
import com.example.movietv.common.Constants.Companion.TITLE_CATEGORY
import com.example.movietv.common.Constants.Companion.TYPE_MOVIE
import com.example.movietv.model.remote.Movie
import com.example.movietv.viewModel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_category.*


class CategoryFragment : Fragment() {
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieListAdapter: MoviePagedListAdapter
    private var type: String? = null
    private var title: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        type = this.arguments?.getString(TYPE_MOVIE)
        title = this.arguments?.getString(TITLE_CATEGORY)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(type, title)
    }

    private fun initAdapter(type: String?, title: String?) {
        viewModel = getViewModel()
        progress_bar.visibility = View.VISIBLE
        movieListAdapter = MoviePagedListAdapter(context!!)
        val gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieListAdapter.getItemViewType(position)
                return if (viewType == MOVIE_VIEW_TYPE) 1
                else 3
            }
        }
        rcvCategory?.apply {
            this.setHasFixedSize(true)
            this.layoutManager = gridLayoutManager
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = movieListAdapter
        }
        txtTitleCategory.text = title
        viewModel.moviePageListCategory(type as String)
            .observe(viewLifecycleOwner, Observer<PagedList<Movie>> {
                movieListAdapter.submitList(it)
                progress_bar.visibility = View.GONE
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