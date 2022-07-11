package com.company.yandexmapstest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.company.yandexmapstest.entity.MarkerEntity

@Dao
interface MarkerDao {
    @Query("SELECT * FROM MarkerEntity ORDER BY id DESC")
    suspend fun getAllMarkers(): List<MarkerEntity>

    @Insert
    suspend fun insert(marker: MarkerEntity)
}