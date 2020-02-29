package com.example.movietv.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movietv.R
import com.example.movietv.common.Constants.Companion.BASE_YOUTUBE_URL
import com.example.movietv.common.Constants.Companion.ENDPOINT_YOUTUBE_URL
import com.example.movietv.data.remote.entity.Trailer
import com.google.android.youtube.player.YouTubeIntents
import kotlinx.android.synthetic.main.item_movie_search.view.txtTitleSearch
import kotlinx.android.synthetic.main.item_trailer.view.imgTrailer


class TrailerAdapter(private val context: Context) : ListAdapter<Trailer, RecyclerView.ViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_trailer, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CustomViewHolder).bind(getItem(position), context)
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(trailer: Trailer?, context: Context) {
            val imageTrailerURL = BASE_YOUTUBE_URL + trailer?.key + ENDPOINT_YOUTUBE_URL
            Glide.with(itemView.context)
                    .load(imageTrailerURL)
                    .into(itemView.imgTrailer)
            itemView.txtTitleSearch.text = trailer?.trailerName
            itemView.setOnClickListener { view ->
                if (YouTubeIntents.canResolvePlayVideoIntent(context)) {
                    val intent: Intent = YouTubeIntents.createPlayVideoIntentWithOptions(context, trailer?.key, true, false)
                    startActivity(context, intent, null)
                } else {
                    Toast.makeText(view.context, "Youtube App is not present in the device", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Trailer>() {
        override fun areItemsTheSame(
            oldItem: Trailer,
            newItem: Trailer
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Trailer,
            newItem: Trailer
        ): Boolean = oldItem == newItem
    }
}