package com.company.yandexmapstest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.company.yandexmapstest.dao.MarkerDao
import com.company.yandexmapstest.entity.MarkerEntity


@Database(entities = [MarkerEntity::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun markerDao(): MarkerDao
}

