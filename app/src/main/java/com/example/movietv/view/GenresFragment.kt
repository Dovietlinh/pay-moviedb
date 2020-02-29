package com.example.movietv.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietv.R
import com.example.movietv.adapter.MoviePagedListAdapter
import com.example.movietv.data.remote.entity.Genre
import com.example.movietv.viewModel.GenreViewModel

class GenresFragment : Fragment() {
    private lateinit var viewModel: GenreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_genres, container, false)
        val viewGroup: LinearLayout = view.findViewById(R.id.linearGenre)
        initView(viewGroup)
        return view
    }

    private fun initView(viewGroup: LinearLayout) {
        viewModel = getViewModel()
        viewModel.genreList.observe(viewLifecycleOwner, Observer {
            for (genre in it.genreList) {
                addViewGenre(genre,viewGroup)
            }
        })
    }

    private fun addViewGenre(genre: Genre, viewGroup: LinearLayout) {
        val params: RecyclerView.LayoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT, 180
        )
        params.topMargin = 20
        val recyclerView = RecyclerView(context!!)
        recyclerView.layoutParams = params
//        recyclerView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        val movieListAdapterGenre = MoviePagedListAdapter(context!!)
        recyclerView.apply {
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = movieListAdapterGenre
        }
//        viewModel..observe(viewLifecycleOwner, Observer<PagedList<Movie>> {
//            movieListAdapterGenre.submitList(it)
//        })

        val textView = TextView(context)
        textView.text = genre.name
        textView.textSize = 16.0f
        textView.setTextColor(Color.parseColor("#FFFFFF"))

        viewGroup.addView(textView)
        viewGroup.addView(recyclerView)
    }

    private fun getViewModel(): GenreViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return GenreViewModel() as T
            }
        })[GenreViewModel::class.java]
    }
}