package com.company.yandexmapstest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.company.yandexmapstest.databinding.FragmentDialogMarkerNameBinding
import com.company.yandexmapstest.entity.MarkerEntity
import com.company.yandexmapstest.util.CoordinatesPreferences
import com.company.yandexmapstest.viewmodel.MarkerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MarkerNameDialogFragment : DialogFragment() {

    @Inject
    lateinit var prefs: CoordinatesPreferences

    private val viewModel: MarkerViewModel by viewModels(ownerProducer = ::requireParentFragment)

    private lateinit var binding: FragmentDialogMarkerNameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogMarkerNameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = "MarkerName"
        dialog?.setTitle(title)
        binding.etDescription.requestFocus()
        dialog?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnOk.setOnClickListener {
            viewModel.save(
                MarkerEntity(
                    latitude = prefs.lat?.toDouble()!!,
                    longitude = prefs.lon?.toDouble()!!,
                    description = binding.etDescription.text.toString()
                )
            )
            dismiss()
        }
    }
}