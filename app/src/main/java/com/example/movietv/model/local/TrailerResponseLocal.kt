package com.example.movietv.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "db_trailerResponse")
data class TrailerResponseLocal (
    @PrimaryKey val id:Int=0,
    @ColumnInfo(name = "key") val name: String = "",
    @ColumnInfo(name = "name") val key: String = ""
)
