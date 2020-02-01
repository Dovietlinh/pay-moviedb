package com.example.movietv.Model


import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
class Movie(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    val title: String
)