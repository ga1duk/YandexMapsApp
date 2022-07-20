package com.company.yandexmapstest.application

import android.app.Application
import com.company.yandexmapstest.MAPKIT_API_KEY
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MapsApplication : Application() {
    override fun onCreate() {
//        Указываем ключ для Yandex.MapKit
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        super.onCreate()
    }
}