package com.company.yandexmapstest.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.company.yandexmapstest.entity.MarkerModel

class MarkerDaoImpl(private val db: SQLiteDatabase) : MarkerDao {

    companion object {
        val DDL = """
            CREATE TABLE ${MarkerColumns.TABLE} (
            ${MarkerColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${MarkerColumns.COLUMN_LAT} TEXT NOT NULL,
            ${MarkerColumns.COLUMN_LON} TEXT NOT NULL,
            ${MarkerColumns.COLUMN_INFO} TEXT
            );
        """.trimIndent()
    }

    object MarkerColumns {
        const val TABLE = "markers"
        const val COLUMN_ID = "id"
        const val COLUMN_LAT = "latitude"
        const val COLUMN_LON = "longitude"
        const val COLUMN_INFO = "information"
    }

    override fun save(markerModel: MarkerModel) {
        val values = ContentValues().apply {
            put(MarkerColumns.COLUMN_LAT, markerModel.latitude)
            put(MarkerColumns.COLUMN_LON, markerModel.longitude)
            put(MarkerColumns.COLUMN_INFO, markerModel.userData)
        }

        db.replace(MarkerColumns.TABLE, null, values)
    }

    override fun delete() {
        db.execSQL(
            """DROP TABLE markers;""".trimIndent()
        )
    }
}