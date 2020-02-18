package com.example.movietv.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movietv.R
import com.example.movietv.common.Constants
import com.example.movietv.common.Constants.Companion.POSTER_BASE_URL
import com.example.movietv.model.local.MovieDetailLocal
import com.example.movietv.view.DetailsActivity
import kotlinx.android.synthetic.main.item_movie_search.view.imgMovieSearch
import kotlinx.android.synthetic.main.item_movie_search.view.txtTitleSearch

class MovieFavoriteAdapter(private val context: Context) :
    ListAdapter<MovieDetailLocal, RecyclerView.ViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_movie_search, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CustomViewHolder).bind(getItem(position), context)
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieDetailLocal: MovieDetailLocal?, context: Context) {
            val movieBackdropURL = POSTER_BASE_URL + movieDetailLocal?.backdropPath
            Glide.with(itemView.context)
                .load(movieBackdropURL)
                .into(itemView.imgMovieSearch)
            itemView.txtTitleSearch.text = movieDetailLocal?.title
            itemView.setOnClickListener {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(Constants.MOVIE_ID, movieDetailLocal?.id)
                context.startActivity(intent)
            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieDetailLocal>() {
        override fun areItemsTheSame(
                oldItem: MovieDetailLocal,
                newItem: MovieDetailLocal
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
                oldItem: MovieDetailLocal,
                newItem: MovieDetailLocal
        ): Boolean = oldItem == newItem
    }


}