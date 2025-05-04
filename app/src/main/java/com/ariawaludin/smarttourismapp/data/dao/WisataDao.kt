package com.ariawaludin.smarttourismapp.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ariawaludin.smarttourismapp.data.model.Wisata

@Dao
interface WisataDao {

    @Insert
    suspend fun insertWisata(wisata: Wisata)

    @Query("SELECT * FROM wisata")
    suspend fun getAllWisata(): List<Wisata>
}

