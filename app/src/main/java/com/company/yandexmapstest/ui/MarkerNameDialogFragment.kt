package com.company.yandexmapstest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.company.yandexmapstest.databinding.FragmentDialogMarkerNameBinding


class MarkerNameDialogFragment : DialogFragment() {

    interface MarkerNameDialogListener {
        fun onFinishEditDialog(inputText: String)
    }

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
            sendBackResult()
        }
    }

    private fun sendBackResult() {
        val listener: MarkerNameDialogListener = targetFragment as MarkerNameDialogListener
        listener.onFinishEditDialog(binding.etDescription.text.toString())
        dismiss()
    }
}