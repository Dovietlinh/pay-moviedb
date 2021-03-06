package com.example.movietv.model.remote

import com.example.movietv.model.remote.Movie
import com.google.gson.annotations.SerializedName

data class MovieRespose(
    val page: Int,
    @SerializedName("results")
    val movieList: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)