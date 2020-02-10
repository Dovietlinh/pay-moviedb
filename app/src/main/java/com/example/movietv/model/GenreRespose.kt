package com.example.movietv.model

import com.google.gson.annotations.SerializedName

class GenreRespose(
    @SerializedName("genres")
    val genreList: List<Genre>
)