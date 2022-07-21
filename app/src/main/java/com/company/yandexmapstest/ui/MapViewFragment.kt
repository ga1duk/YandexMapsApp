package com.company.yandexmapstest.ui

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.company.yandexmapstest.R
import com.company.yandexmapstest.databinding.FragmentMapViewBinding
import com.company.yandexmapstest.util.MarkerCoordinatesArg
import com.company.yandexmapstest.util.MarkerPreferences
import com.company.yandexmapstest.viewmodel.MarkerViewModel
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Circle
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


//  Всё, что закомментировано - относится к определению текущей геопозиции пользователя
//  При необходимости - раскомментировать

private const val TAG = "fragment_dialog_marker"
private const val DEFAULT_LAT = 55.749625
private const val DEFAULT_LON = 37.629020

@AndroidEntryPoint
class MapViewFragment : Fragment()/*, UserLocationObjectListener*/ {

    @Inject
    lateinit var prefs: MarkerPreferences

    companion object {
        var Bundle.markerCoordinatesArg: String? by MarkerCoordinatesArg
    }

    private val viewModel: MarkerViewModel by viewModels(ownerProducer = ::requireParentFragment)

    private lateinit var mapView: MapView
    private lateinit var mapKit: MapKit

    //    private lateinit var userLocationLayer: UserLocationLayer
    private lateinit var mapObjectCollection: MapObjectCollection
    private lateinit var markerNameDialog: MarkerNameDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMapViewBinding.inflate(inflater, container, false)

        mapView = binding.mapView

        mapKit = MapKitFactory.getInstance()
        mapKit.resetLocationManagerToDefault()

//        Для определения текущей геопозиции, убрать этот кусок кода
        mapView.map.move(
            CameraPosition(Point(DEFAULT_LAT, DEFAULT_LON), 10F, 0F, 0F)
        )

////        Проверяем, имеется ли разрешение на определение геопозиции пользователя
//        requestLocationPermission()

//        Показать выбранный маркер из списка на карте и увеличить зум в 10 раз
        if (arguments?.markerCoordinatesArg != null) {
            val parts = arguments?.markerCoordinatesArg?.split(",")
            val lat = parts?.get(0)
            val lon = parts?.get(1)
            if (lat != null && lon != null) {
                mapView.map.mapObjects.addPlacemark(
                    Point(lat.toDouble(), lon.toDouble()),
                    ImageProvider.fromResource(requireContext(), R.drawable.mark)
                )
                mapView.map.move(
                    CameraPosition(Point(lat.toDouble(), lon.toDouble()), 10F, 0F, 0F)
                )
            }
        }

//        Создаём коллекцию маркеров
        mapObjectCollection = mapView.map.mapObjects.addCollection()

//        Обрабатываем нажатия на точки на карте
        val listener = object : InputListener {
            override fun onMapLongTap(map: Map, point: Point) {
//                Добавляем маркер в точку нажатия и показываем диалог для ввода описания к маркеру
                mapObjectCollection.addPlacemark(
                    Point(point.latitude, point.longitude),
                    ImageProvider.fromResource(
                        requireContext(), R.drawable.mark
                    )
                )
                prefs.lat = point.latitude.toString()
                prefs.lon = point.longitude.toString()
                showDialog()
            }

            override fun onMapTap(map: Map, point: Point) {
            }
        }

        mapView.map.addInputListener(listener)

        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            if (state.error) {
                Snackbar.make(binding.root, R.string.error_toast, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        return binding.root
    }

//    override fun onObjectAdded(userLocationView: UserLocationView) {
////                Отображаем точку с текущей геопозицией по центру экрана
//        userLocationLayer.setAnchor(
//            PointF(
//                (mapView.width * 0.5).toFloat(),
//                (mapView.height * 0.5).toFloat()
//            ),
//            PointF(
//                (mapView.width * 0.5).toFloat(),
//                (mapView.height * 0.83).toFloat()
//            )
//        )
////                Перемещаем камеру в точку с текущей геопозицией пользователя, увеличиваем масштаб карты в 15 раз
//        mapView.map.move(
//            CameraPosition(Point(0.0, 0.0), 15F, 0F, 0F)
//        )
//
//        val pinIcon: CompositeIcon = userLocationView.pin.useCompositeIcon()
//
////                Меняем иконку для точки с текущей геопозицией
//        pinIcon.setIcon(
//            "pin",
//            ImageProvider.fromResource(requireContext(), R.drawable.search_result),
//            IconStyle().setAnchor(PointF(0.5f, 0.5f))
//                .setRotationType(RotationType.ROTATE)
//                .setZIndex(1f)
//                .setScale(0.5f)
//        )
//    }
//
//    override fun onObjectRemoved(userLocationView: UserLocationView) {
//    }
//
//    override fun onObjectUpdated(userLocationView: UserLocationView, p1: ObjectEvent) {
//    }

//    private fun setUpUserLocationLayer() {
//        userLocationLayer = mapKit.createUserLocationLayer(mapView.mapWindow)
//        userLocationLayer.isVisible = true
//        userLocationLayer.isHeadingEnabled = true
//        userLocationLayer.setObjectListener(this)
//    }

//    @RequiresApi(Build.VERSION_CODES.M)
//    private fun requestLocationPermission() {
//        when {
////            1. Проверяем, есть ли уже права
//            ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED -> {
//                setUpUserLocationLayer()
//            }
////            2. Должны показать обоснование необходимости прав
//            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
//            }
////            3. Запрашиваем права
//            else -> {
//                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//            }
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (isGranted) {
//                setUpUserLocationLayer()
//            } else {
//                Toast.makeText(
//                    requireContext(),
//                    getString(R.string.permission_not_granted_toast),
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }

    fun showDialog() {
        if (fragmentManager != null) {
            val fm = fragmentManager
            markerNameDialog = MarkerNameDialogFragment()
            markerNameDialog.setTargetFragment(this@MapViewFragment, 300)
            if (fm != null) {
                markerNameDialog.show(fm, TAG)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.markers_list -> {
                findNavController()
                    .navigate(R.id.action_mapViewFragment_to_markersFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
//        Обязательно добавляем следующие строки кода, согласно документации к Yandex.Mapkit API
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
//        Обязательно добавляем следующие строки кода, согласно документации к Yandex.Mapkit API
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}