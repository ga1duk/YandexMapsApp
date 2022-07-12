package com.company.yandexmapstest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.company.yandexmapstest.dto.Marker
import com.company.yandexmapstest.entity.MarkerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkerDao {
    @Query("SELECT * FROM MarkerEntity ORDER BY id DESC")
    fun getAllMarkers(): Flow<List<MarkerEntity>>

    @Insert
    suspend fun save(marker: MarkerEntity)

    @Query("DELETE FROM MarkerEntity WHERE id = :id")
    suspend fun removeById(id: Long)
}