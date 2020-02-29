package com.example.movietv.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "db_moviesResponse", primaryKeys = ["id", "type"])
data class MovieResponseLocal(
    @ColumnInfo(name = "id") val id: Int=0,
    @ColumnInfo(name = "page") val page: Int = 0,
    @ColumnInfo(name = "poster_path") val posterPath: String = "",
    @ColumnInfo(name = "backdrop_path") val backdropPath: String = "",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "type") val type: String = "",
    @ColumnInfo(name = "totalPages") val totalPages: Int = 0
)