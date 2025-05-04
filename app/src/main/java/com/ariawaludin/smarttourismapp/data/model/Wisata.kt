package com.ariawaludin.smarttourismapp.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wisata")
data class Wisata(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String,
    val location: String
)
