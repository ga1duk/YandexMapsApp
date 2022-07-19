package com.company.yandexmapstest.repository

import com.company.yandexmapstest.dao.MarkerDao
import com.company.yandexmapstest.entity.MarkerEntity
import com.company.yandexmapstest.entity.toDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarkerRepositoryImpl @Inject constructor(private val dao: MarkerDao) : MarkerRepository {
    override val data = dao.getAllMarkers()
        .map(List<MarkerEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override suspend fun save(marker: MarkerEntity) {
        dao.insert(marker)
    }

    override suspend fun updateDescriptionById(id: Long, content: String) {
        dao.updateDescriptionById(id, content)
    }

    override suspend fun removeById(id: Long) {
        dao.removeById(id)
    }
}