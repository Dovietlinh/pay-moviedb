package com.example.movietv.model.remote

import com.example.movietv.model.remote.Genre
import com.google.gson.annotations.SerializedName

class GenreRespose(
    @SerializedName("genres")
    val genreList: List<Genre>
)