package com.company.yandexmapstest.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.PointF
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.company.yandexmapstest.R
import com.company.yandexmapstest.dao.MarkerDao
import com.company.yandexmapstest.databinding.FragmentMapViewBinding
import com.company.yandexmapstest.db.AppDb
import com.company.yandexmapstest.entity.MarkerModel
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider

class MapViewFragment : Fragment(), UserLocationObjectListener {

    private lateinit var markerListener: MapObjectTapListener
    private lateinit var mapView: MapView
    private lateinit var mapKit: MapKit
    private lateinit var userLocationLayer: UserLocationLayer
    private lateinit var mapObjectCollection: MapObjectCollection
    private lateinit var dao: MarkerDao

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMapViewBinding.inflate(inflater, container, false)

        dao = AppDb.getInstance(requireContext()).markerDao

        mapView = binding.mapView

        mapKit = MapKitFactory.getInstance()
        mapKit.resetLocationManagerToDefault()

//        Проверяем, имеется ли разрешение на определение геопозиции пользователя
        requestLocationPermission()

        binding.btnMarkers.setOnClickListener {
            findNavController().navigate(R.id.action_mapViewFragment_to_markersFragment)
        }

//        Создаём коллекцию маркеров
        mapObjectCollection = mapView.map.mapObjects.addCollection()

        markerListener = MapObjectTapListener { _, point ->
            Toast.makeText(
                requireContext(),
                "${point.latitude}, ${point.longitude}",
                Toast.LENGTH_LONG
            ).show()
            true
        }

//        Обрабатываем нажатия на точки на карте
        val listener = object : InputListener {
            override fun onMapLongTap(map: Map, point: Point) {
            }

            override fun onMapTap(map: Map, point: Point) {
//                Добавляем маркер в точку нажатия (и в коллекцию маркеров)
                addMarker(point.latitude, point.longitude, R.drawable.mark)
            }
        }
        mapView.map.addInputListener(listener)

        return binding.root
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
//                Отображаем точку с текущей геопозицией по центру экрана
        userLocationLayer.setAnchor(
            PointF(
                (mapView.width * 0.5).toFloat(),
                (mapView.height * 0.5).toFloat()
            ),
            PointF(
                (mapView.width * 0.5).toFloat(),
                (mapView.height * 0.83).toFloat()
            )
        )
//                Перемещаем камеру в точку с текущей геопозицией пользователя, увеличиваем масштаб карты в 15 раз
        mapView.map.move(
            CameraPosition(Point(0.0, 0.0), 15F, 0F, 0F)
        )

        val pinIcon: CompositeIcon = userLocationView.pin.useCompositeIcon()

//                Меняем иконку для точки с текущей геопозицией
        pinIcon.setIcon(
            "pin",
            ImageProvider.fromResource(requireContext(), R.drawable.search_result),
            IconStyle().setAnchor(PointF(0.5f, 0.5f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(1f)
                .setScale(0.5f)
        )
    }

    override fun onObjectRemoved(userLocationView: UserLocationView) {
    }

    override fun onObjectUpdated(userLocationView: UserLocationView, p1: ObjectEvent) {
    }

    //
    private fun setUpUserLocationLayer() {
        userLocationLayer = mapKit.createUserLocationLayer(mapView.mapWindow)
        userLocationLayer.isVisible = true
//        userLocationLayer.isHeadingEnabled = true
        userLocationLayer.setObjectListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestLocationPermission() {
        when {
//            1. Проверяем, есть ли уже права
            ContextCompat.checkSelfPermission(
                requireContext(),
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
                    requireContext(),
                    getString(R.string.permission_not_granted_toast),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    fun addMarker(
        lat: Double,
        lon: Double,
        @DrawableRes imageRes: Int,
        userData: String? = null
    ): PlacemarkMapObject {
        val marker = mapObjectCollection.addPlacemark(
            Point(lat, lon),
            ImageProvider.fromResource(requireContext(), imageRes)
        )
        marker.userData = userData
        markerListener.let { marker.addTapListener(it) }
        dao.save(MarkerModel(lat, lon, userData))
        return marker
    }
}