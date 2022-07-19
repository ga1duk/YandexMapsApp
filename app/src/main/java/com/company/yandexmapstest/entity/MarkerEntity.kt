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
    val description: String
) {
    fun toDto() = Marker(id, latitude, longitude, description)

    companion object {
        fun fromDto(dto: Marker) =
            MarkerEntity(
                dto.id, dto.latitude, dto.longitude, dto.description
            )
    }
}


fun List<MarkerEntity>.toDto(): List<Marker> = map(MarkerEntity::toDto)
fun List<Marker>.toEntity(): List<MarkerEntity> = map(MarkerEntity::fromDto)
