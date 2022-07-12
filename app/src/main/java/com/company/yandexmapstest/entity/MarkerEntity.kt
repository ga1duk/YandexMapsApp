package com.company.yandexmapstest.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.company.yandexmapstest.dto.Marker

@Entity
data class MarkerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val latitude: Double,
    val longitude: Double,
    val userData: String? = null
) {
    fun toDto() = Marker(id, latitude, longitude, userData)
}


fun List<MarkerEntity>.toDto(): List<Marker> = map(MarkerEntity::toDto)
