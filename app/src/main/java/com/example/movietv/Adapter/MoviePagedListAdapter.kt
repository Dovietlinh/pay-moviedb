package com.example.movietv.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movietv.Common.Constants.Companion.POSTER_BASE_URL
import com.example.movietv.Model.Movie
import com.example.movietv.R
import com.example.movietv.Repository.NetworkState
import com.example.movietv.View.DetailsActivity
import kotlinx.android.synthetic.main.item_backdrop.view.*


class MoviePagedListAdapter(public val context: Context):PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        if(viewType==MOVIE_VIEW_TYPE){
            view = layoutInflater.inflate(R.layout.item_backdrop, parent, false)
            return MovieItemViewHolder(view)
        }else{
            view = layoutInflater.inflate(R.layout.item_network_state, parent, false)
            return MovieItemViewHolder(view)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position),context)
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
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
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
            val moviePosterURL = POSTER_BASE_URL + movie?.posterPath
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.imgMovieCategory)
            itemView.setOnClickListener {
//                Toast.makeText(context,movie!!.id.toString(),Toast.LENGTH_LONG).show()
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra("id", movie?.id)
                context.startActivity(intent)
            }

        }

    }
}