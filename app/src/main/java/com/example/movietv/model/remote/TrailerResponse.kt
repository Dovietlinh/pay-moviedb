package com.example.movietv.model.remote

import com.google.gson.annotations.SerializedName

data class TrailerResponse(
        val page: Int,
        @SerializedName("results")
        val trailerList: List<Trailer>
)
data class Trailer(
        val id: String,
        @SerializedName("key")
        val key: String,
        @SerializedName("name")
        val trailerName: String
)