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
import com.example.movietv.model.local.MovieDetailEntity
import com.example.movietv.view.DetailsActivity
import kotlinx.android.synthetic.main.item_movie_search.view.*

class MovieFavoriteAdapter(private val context: Context) :
    ListAdapter<MovieDetailEntity, RecyclerView.ViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        view = layoutInflater.inflate(R.layout.item_movie_search, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CustomViewHolder).bind(getItem(position), context)
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieDetailEntity: MovieDetailEntity?, context: Context) {
            val movieBackdropURL = POSTER_BASE_URL + movieDetailEntity?.backdropPath
            Glide.with(itemView.context)
                .load(movieBackdropURL)
                .into(itemView.imgMovieSearch)
            itemView.txtTitleSearch.text = movieDetailEntity?.title
            itemView.setOnClickListener {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(Constants.MOVIE_ID, movieDetailEntity?.id)
                context.startActivity(intent)
            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MovieDetailEntity>() {
        override fun areItemsTheSame(
            oldItem: MovieDetailEntity,
            newItem: MovieDetailEntity
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: MovieDetailEntity,
            newItem: MovieDetailEntity
        ): Boolean = oldItem == newItem
    }


}