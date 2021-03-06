package com.example.movietv.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieDetailEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "posterPath") val posterPath: String,
    @ColumnInfo(name = "backdropPath") val backdropPath: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: String,
    @ColumnInfo(name = "tagline") val tagline: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "runtime") val runtime: Int,
    @ColumnInfo(name = "rating") val rating: Double
)