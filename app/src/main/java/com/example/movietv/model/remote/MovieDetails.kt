package com.example.movietv.model.remote

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val id: Int,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val runtime: Int,
    val tagline: String,
    val title: String,
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("genres")
    val genreList: List<Genre>
)