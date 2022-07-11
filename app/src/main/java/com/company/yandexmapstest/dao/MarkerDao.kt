package com.company.yandexmapstest.dao

import com.company.yandexmapstest.entity.MarkerModel

interface MarkerDao {
    fun save(markerModel: MarkerModel)
    fun delete()
}