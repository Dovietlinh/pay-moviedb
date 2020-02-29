package com.example.movietv.data.remote.entity

import com.google.gson.annotations.SerializedName

data class Genre(
    val id: Int,
    @SerializedName("name")
    val name: String
)