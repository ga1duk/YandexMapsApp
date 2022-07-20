package com.company.yandexmapstest.repository

import com.company.yandexmapstest.dao.MarkerDao
import com.company.yandexmapstest.entity.MarkerEntity
import com.company.yandexmapstest.entity.toDto
import com.company.yandexmapstest.error.DbError
import com.company.yandexmapstest.error.UnknownError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class MarkerRepositoryImpl @Inject constructor(private val dao: MarkerDao) : MarkerRepository {
    override val data = dao.getAllMarkers()
        .map(List<MarkerEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override suspend fun save(marker: MarkerEntity) {
        try {
            dao.insert(marker)
        } catch (e: IOException) {
            throw DbError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun updateDescriptionById(id: Long, content: String) {
        try {
            dao.updateDescriptionById(id, content)
        } catch (e: IOException) {
            throw DbError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun removeById(id: Long) {
        try {
            dao.removeById(id)
        } catch (e: IOException) {
            throw DbError
        } catch (e: Exception) {
            throw UnknownError
        }
    }
}