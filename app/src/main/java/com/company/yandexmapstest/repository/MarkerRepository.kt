package com.company.yandexmapstest.repository

import com.company.yandexmapstest.dto.Marker
import com.company.yandexmapstest.entity.MarkerEntity
import kotlinx.coroutines.flow.Flow

interface MarkerRepository {
    val data: Flow<List<Marker>>
    suspend fun save(marker: MarkerEntity)
    suspend fun updateDescriptionById(id: Long, content: String)
    suspend fun removeById(id: Long)
}