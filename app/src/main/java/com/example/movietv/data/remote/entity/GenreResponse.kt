package com.example.movietv.data.remote.entity

import com.example.movietv.data.remote.entity.Genre
import com.google.gson.annotations.SerializedName

class GenreResponse(
    @SerializedName("genres")
    val genreList: List<Genre>
)