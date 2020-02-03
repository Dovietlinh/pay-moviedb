package com.example.movietv.Model


import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
data class Movie(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String
)