package com.example.movietv.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movietv.R
import com.example.movietv.common.Constants
import com.example.movietv.common.Constants.Companion.MOVIE_ID
import com.example.movietv.model.remote.Movie
import com.example.movietv.repository.NetworkState
import com.example.movietv.view.DetailsActivity
import kotlinx.android.synthetic.main.item_movie_search.view.imgMovieSearch
import kotlinx.android.synthetic.main.item_movie_search.view.txtTitleSearch

class MovieSearchPagedListAdapter(private val context: Context) :
    PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        view = layoutInflater.inflate(R.layout.item_movie_search, parent, false)
        return MovieItemViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == Constants.MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position), context)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            Constants.NETWORK_VIEW_TYPE
        } else {
            Constants.MOVIE_VIEW_TYPE
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie?, context: Context) {
            if (movie?.backdropPath != null) {
                val movieBackdropURL = Constants.POSTER_BASE_URL + movie?.backdropPath
                Glide.with(itemView.context)
                    .load(movieBackdropURL)
                    .into(itemView.imgMovieSearch)
            }
            movie?.name?.let {
                itemView.txtTitleSearch.text = it
            }
            movie?.title?.let {
                itemView.txtTitleSearch.text = it
            }
            itemView.setOnClickListener {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(MOVIE_ID, movie?.id)
                context.startActivity(intent)
            }

        }

    }
}