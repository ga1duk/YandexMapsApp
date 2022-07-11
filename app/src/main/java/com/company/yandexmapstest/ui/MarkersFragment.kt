package com.company.yandexmapstest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.company.yandexmapstest.databinding.FragmentMarkersBinding

class MarkersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMarkersBinding.inflate(inflater, container, false)

        return binding.root
    }
}