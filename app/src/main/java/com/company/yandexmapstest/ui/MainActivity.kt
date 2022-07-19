package com.company.yandexmapstest.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.company.yandexmapstest.MAPKIT_API_KEY
import com.company.yandexmapstest.R
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
//        Указываем ключ для Yandex.MapKit
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
//        Инициализируем библиотеку
        MapKitFactory.initialize(this)

        super.onCreate(savedInstanceState)
    }
}
