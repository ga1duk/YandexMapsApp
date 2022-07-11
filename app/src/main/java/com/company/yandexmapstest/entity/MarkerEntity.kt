package com.company.yandexmapstest.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MarkerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val latitude: Double,
    val longitude: Double,
    val userData: String? = null
)
