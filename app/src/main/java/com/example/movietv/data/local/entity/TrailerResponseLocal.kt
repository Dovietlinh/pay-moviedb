package com.example.movietv.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_trailers")
data class TrailerResponseLocal (
    @PrimaryKey val id:String,
    @ColumnInfo(name="movieID") val movieID:Int=0,
    @ColumnInfo(name = "key") val name: String = "",
    @ColumnInfo(name = "name") val key: String = ""
)
