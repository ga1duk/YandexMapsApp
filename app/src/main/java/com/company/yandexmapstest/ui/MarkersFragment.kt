package com.company.yandexmapstest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.company.yandexmapstest.R
import com.company.yandexmapstest.adapter.MarkerAdapter
import com.company.yandexmapstest.adapter.OnInteractionListener
import com.company.yandexmapstest.dao.MarkerDao
import com.company.yandexmapstest.databinding.FragmentMarkersBinding
import com.company.yandexmapstest.ui.MapViewFragment.Companion.textArg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MarkersFragment : Fragment() {

    @Inject
    lateinit var dao: MarkerDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMarkersBinding.inflate(inflater, container, false)


        val adapter = MarkerAdapter(object : OnInteractionListener {
            override fun onClick(marker: String) {
                findNavController().navigate(
                    R.id.action_markersFragment_to_mapViewFragment,
                    Bundle().apply {
                        textArg = marker
                    })
            }
        })

        binding.rvMarkers.adapter = adapter

        lifecycleScope.launch {
            try {
                val markers = mutableListOf<String>()
                for (i in dao.getAllMarkers()) {
                    markers.add("${i.latitude}, ${i.longitude}")
                }
                adapter.submitList(markers)
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Не получилось вывести список. Попробуйте позже",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        return binding.root
    }
}