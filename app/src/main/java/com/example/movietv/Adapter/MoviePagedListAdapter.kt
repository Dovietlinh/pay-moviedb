package com.example.movietv.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movietv.Model.Movie
import com.example.movietv.R
import com.example.themoviedb.Api.POSTER_BASE_URL
import kotlinx.android.synthetic.main.item_backdrop.view.*

class MoviePagedListAdapter(val context: Context):PagedListAdapter<Movie,RecyclerView.ViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
            view = layoutInflater.inflate(R.layout.item_backdrop, parent, false)
            return MovieItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieItemViewHolder).bind(getItem(position),context)
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
    override fun getItemCount(): Int {
        return 1
    }
    class MovieItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie?,context: Context) {
            val moviePosterURL = POSTER_BASE_URL + movie?.posterPath
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.imgMovieCategory)

            itemView.setOnClickListener{
//                val intent = Intent(context, SingleMovie::class.java)
//                intent.putExtra("id", movie?.id)
//                context.startActivity(intent)
            }

        }

    }
}