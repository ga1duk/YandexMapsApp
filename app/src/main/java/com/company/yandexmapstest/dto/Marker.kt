package com.company.yandexmapstest.dto

data class Marker(
    val id: Long? = null,
    val latitude: Double,
    val longitude: Double,
    val description: String? = null
)