@file:Suppress("SpellCheckingInspection")

package com.example.movietv.Model

import com.google.gson.annotations.SerializedName

data class MovieRespose(
    val page: Int,
    @SerializedName("results")
    val movieList: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)