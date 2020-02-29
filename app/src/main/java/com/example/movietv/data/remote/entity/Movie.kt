package com.example.movietv.data.remote.entity


import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int = 0,
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    val title: String,
    val name: String = ""
)