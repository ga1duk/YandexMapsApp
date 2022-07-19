package com.company.yandexmapstest.util

import android.content.Context

private const val PREFS_FILE_NAME = "marker"

class MarkerPreferences(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val idKey = "id"
        private const val latKey = "latitude"
        private const val lonKey = "longitude"
        private const val descKey = "description"
    }

    var id: String?
        get() = prefs.getString(idKey, null)
        set(value) {
            with(prefs.edit()) {
                putString(idKey, value)
                apply()
            }
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

    var description: String?
        get() = prefs.getString(descKey, null)
        set(value) {
            with(prefs.edit()) {
                putString(descKey, value)
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