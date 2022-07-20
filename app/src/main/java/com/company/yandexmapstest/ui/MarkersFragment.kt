package com.company.yandexmapstest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.company.yandexmapstest.R
import com.company.yandexmapstest.adapter.MarkerAdapter
import com.company.yandexmapstest.adapter.OnInteractionListener
import com.company.yandexmapstest.databinding.FragmentMarkersBinding
import com.company.yandexmapstest.dto.Marker
import com.company.yandexmapstest.ui.EditFragment.Companion.markerDescriptionArg
import com.company.yandexmapstest.ui.MapViewFragment.Companion.markerCoordinatesArg
import com.company.yandexmapstest.util.MarkerPreferences
import com.company.yandexmapstest.viewmodel.MarkerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MarkersFragment : Fragment() {

    @Inject
    lateinit var prefs: MarkerPreferences

    private val viewModel: MarkerViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMarkersBinding.inflate(inflater, container, false)

        val adapter = MarkerAdapter(object : OnInteractionListener {
            override fun onClick(marker: Marker) {
                findNavController().navigate(
                    R.id.action_markersFragment_to_mapViewFragment,
                    Bundle().apply {
                        markerCoordinatesArg = "${marker.latitude}, ${marker.longitude}"
                    })
            }

            override fun onEdit(marker: Marker) {
                val description = marker.description
                findNavController().navigate(
                    R.id.action_markersFragment_to_editFragment,
                    Bundle().apply {
                        markerDescriptionArg = description
                    }
                )
                prefs.id = marker.id.toString()
                super.onEdit(marker)
            }

            override fun onRemove(marker: Marker) {
                lifecycleScope.launch {
                    viewModel.removeById(marker.id ?: 0L)
                }
            }
        })

        binding.rvMarkers.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { markers ->
            adapter.submitList(markers)
        }

        return binding.root
    }
}