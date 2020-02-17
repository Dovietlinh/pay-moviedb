package com.example.movietv.model.remote

import com.google.gson.annotations.SerializedName

class GenreResponse(
    @SerializedName("genres")
    val genreList: List<Genre>
)