package com.company.yandexmapstest.ui

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.company.yandexmapstest.R
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

        builder.setTitle(getString(R.string.dialog_title_text))
            .setMessage(getString(R.string.dialog_message_text))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.positive_button_fragment_dialog_text)) { _, _ ->
                viewModel.save(
                    MarkerEntity(
                        latitude = prefs.lat?.toDouble()!!,
                        longitude = prefs.lon?.toDouble()!!,
                        description = etDescription.text.toString()
                    )
                )
                dismiss()
            }
            .setNegativeButton(getString(R.string.negative_button_fragment_dialog_text)) { _, _ ->
                dismiss()
            }

        return builder.show()
    }
}