package com.company.yandexmapstest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.company.yandexmapstest.MAPKIT_API_KEY
import com.company.yandexmapstest.R
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
//        Указываем ключ для Yandex.MapKit
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
//        Инициализируем библиотеку
        MapKitFactory.initialize(this)

        super.onCreate(savedInstanceState)
    }
}
