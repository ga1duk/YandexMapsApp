package com.company.yandexmapstest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.company.yandexmapstest.entity.MarkerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkerDao {
    @Query("SELECT * FROM MarkerEntity ORDER BY id DESC")
    fun getAllMarkers(): Flow<List<MarkerEntity>>

    @Insert
    suspend fun insert(marker: MarkerEntity)

    @Query("UPDATE MarkerEntity SET description = :description WHERE id = :id")
    suspend fun updateDescriptionById(id: Long, description: String)

    @Query("DELETE FROM MarkerEntity WHERE id = :id")
    suspend fun removeById(id: Long)
}