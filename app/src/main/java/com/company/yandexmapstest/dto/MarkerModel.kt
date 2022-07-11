package com.company.yandexmapstest.dto

data class MarkerModel(
    val latitude: Double,
    val longitude: Double,
    val userData: String? = null
)