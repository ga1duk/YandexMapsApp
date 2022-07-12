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
import com.company.yandexmapstest.ui.MapViewFragment.Companion.textArg
import com.company.yandexmapstest.viewmodel.MarkerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MarkersFragment : Fragment() {

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
                        textArg = "${marker.latitude}, ${marker.longitude}"
                    })
            }

            override fun onRemove(marker: Marker) {
                lifecycleScope.launch {
                    viewModel.removeById(marker.id!!)
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