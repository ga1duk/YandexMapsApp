package com.company.yandexmapstest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.company.yandexmapstest.MAPKIT_API_KEY
import com.company.yandexmapstest.R
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
//        Инициализируем библиотеку Yandex.Mapkit
        MapKitFactory.initialize(this)

        super.onCreate(savedInstanceState)
    }
}
