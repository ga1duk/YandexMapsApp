package com.company.yandexmapstest

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.PointF
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CompositeIcon
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.RotationType
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider

private lateinit var mapview: MapView
private lateinit var mapKit: MapKit
private lateinit var userLocationLayer: UserLocationLayer

class MainActivity : AppCompatActivity(), UserLocationObjectListener {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)

        mapview = findViewById(R.id.mapview)
        mapview.map.isRotateGesturesEnabled = false

        mapKit = MapKitFactory.getInstance()
        mapKit.resetLocationManagerToDefault()

        requestLocationPermission()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestLocationPermission() {
        when {
//            1. Проверяем, есть ли уже права
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                setUpUserLocationLayer()
            }
//            2. Должны показать обоснование необходимости прав
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
            }
//            3. Запрашиваем права
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                setUpUserLocationLayer()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.permission_not_granted_toast),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    private fun setUpUserLocationLayer() {
        userLocationLayer = mapKit.createUserLocationLayer(mapview.mapWindow)
        userLocationLayer.isVisible = true
        userLocationLayer.isHeadingEnabled = true
        userLocationLayer.setObjectListener(this)
    }

    override fun onStop() {
        mapview.onStop()
        mapKit.onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        mapKit.onStart()
        mapview.onStart()
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationLayer.setAnchor(
            PointF(
                (mapview.width * 0.5).toFloat(),
                (mapview.height * 0.5).toFloat()
            ),
            PointF(
                (mapview.width * 0.5).toFloat(),
                (mapview.height * 0.83).toFloat()
            )
        )
        mapview.map.move(CameraPosition(Point(0.0, 0.0), 15F, 0F, 0F))

        val pinIcon: CompositeIcon = userLocationView.pin.useCompositeIcon()

        pinIcon.setIcon(
            "pin",
            ImageProvider.fromResource(this, R.drawable.search_result),
            IconStyle().setAnchor(PointF(0.5f, 0.5f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(1f)
                .setScale(0.5f)
        )
    }

    override fun onObjectRemoved(p0: UserLocationView) {
    }

    override fun onObjectUpdated(userLocationView: UserLocationView, p1: ObjectEvent) {
    }
}