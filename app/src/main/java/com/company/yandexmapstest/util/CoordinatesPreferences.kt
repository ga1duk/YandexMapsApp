package com.company.yandexmapstest.util

import android.content.Context

class CoordinatesPreferences(context: Context) {

    private val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    companion object {
        private const val latKey = "latitude"
        private const val lonKey = "longitude"
    }

    var lat: String?
        get() = prefs.getString(latKey, null)
        set(value) {
            with(prefs.edit()) {
                putString(latKey, value)
                apply()
            }
        }
   var lon: String?
        get() = prefs.getString(lonKey, null)
        set(value) {
            with(prefs.edit()) {
                putString(lonKey, value)
                apply()
            }
        }

    fun clearPrefs() {
        with(prefs.edit()) {
            clear()
            apply()
        }
    }
}