package com.company.yandexmapstest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.company.yandexmapstest.dao.MarkerDao
import com.company.yandexmapstest.databinding.FragmentMarkersBinding
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

        lifecycleScope.launch {
            try {
                binding.tvMarker.text =
                    "${dao.getAllMarkers()[0].latitude}, ${dao.getAllMarkers()[0].longitude}"
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