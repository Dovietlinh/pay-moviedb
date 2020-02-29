package com.example.movietv.data.remote.entity

import com.google.gson.annotations.SerializedName

data class TrailerResponse(
        val id: Int,
        @SerializedName("results")
        val trailerList: List<Trailer>
)

data class Trailer(
        @SerializedName(value = "id")
        val id:String,
        @SerializedName("key")
        val key: String,
        @SerializedName("name")
        val trailerName: String
)