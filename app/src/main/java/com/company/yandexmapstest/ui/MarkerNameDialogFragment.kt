package com.company.yandexmapstest.ui

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.company.yandexmapstest.entity.MarkerEntity
import com.company.yandexmapstest.util.MarkerPreferences
import com.company.yandexmapstest.viewmodel.MarkerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MarkerNameDialogFragment : DialogFragment() {

    @Inject
    lateinit var prefs: MarkerPreferences

    private val viewModel: MarkerViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        val etDescription = EditText(requireContext())
        etDescription.requestFocus()

        builder.setView(etDescription)

        builder.setTitle("Marker Description")
            .setMessage("type new description")
            .setCancelable(true)
            .setPositiveButton("ok") { _, _ ->
                viewModel.save(
                    MarkerEntity(
                        latitude = prefs.lat?.toDouble()!!,
                        longitude = prefs.lon?.toDouble()!!,
                        description = etDescription.text.toString()
                    )
                )
                dismiss()
            }
            .setNegativeButton("cancel") { _, _ ->
                dismiss()
            }

        return builder.show()
    }
}