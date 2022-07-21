package com.company.yandexmapstest.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.company.yandexmapstest.R
import com.company.yandexmapstest.entity.MarkerEntity
import com.company.yandexmapstest.util.AndroidUtils.hideKeyboard
import com.company.yandexmapstest.util.AndroidUtils.showKeyboard
import com.company.yandexmapstest.util.MarkerPreferences
import com.company.yandexmapstest.viewmodel.MarkerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MarkerNameDialogFragment : DialogFragment() {

    @Inject
    lateinit var prefs: MarkerPreferences

    private val viewModel: MarkerViewModel by viewModels(ownerProducer = ::requireParentFragment)

    private lateinit var etDescription: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        etDescription = EditText(requireContext())
        etDescription.gravity = Gravity.CENTER_HORIZONTAL
        etDescription.requestFocus()
        builder.setView(etDescription)
        showKeyboard(activity)


        builder.setTitle(getString(R.string.dialog_title_text))
            .setMessage(getString(R.string.dialog_message_text))
            .setCancelable(true)
            .setPositiveButton(getString(R.string.positive_button_fragment_dialog_text)) { _, _ ->
                viewModel.save(
                    MarkerEntity(
                        latitude = prefs.lat?.toDouble() ?: 0.0,
                        longitude = prefs.lon?.toDouble() ?: 0.0,
                        description = etDescription.text.toString()
                    )
                )
                hideKeyboard(etDescription)
                dismiss()
            }
            .setNegativeButton(getString(R.string.negative_button_fragment_dialog_text)) { _, _ ->
                hideKeyboard(etDescription)
                dismiss()
            }

        return builder.show()
    }
}