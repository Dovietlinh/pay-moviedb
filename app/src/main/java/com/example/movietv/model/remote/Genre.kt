package com.example.movietv.model.remote

import com.google.gson.annotations.SerializedName

data class Genre(
    val id: Int,
    @SerializedName("name")
    val name: String
)