package com.company.yandexmapstest.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.company.yandexmapstest.R
import com.company.yandexmapstest.databinding.FragmentEditBinding
import com.company.yandexmapstest.util.AndroidUtils
import com.company.yandexmapstest.util.AndroidUtils.showKeyboard
import com.company.yandexmapstest.util.MarkerDescriptionArg
import com.company.yandexmapstest.util.MarkerPreferences
import com.company.yandexmapstest.viewmodel.MarkerViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditFragment : Fragment() {

    @Inject
    lateinit var prefs: MarkerPreferences

    companion object {
        var Bundle.markerDescriptionArg: String? by MarkerDescriptionArg
    }

    private var fragmentBinding: FragmentEditBinding? = null


    private val viewModel: MarkerViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditBinding.inflate(inflater, container, false)

        fragmentBinding = binding

        showKeyboard(activity)

        arguments?.markerDescriptionArg
            ?.let(binding.etNewDescription::setText)

        binding.etNewDescription.requestFocus()


        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            if (state.error) {
                Snackbar.make(binding.root, R.string.error_toast, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_marker, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                viewModel.editById(
                    prefs.id?.toLong() ?: 0L,
                    fragmentBinding?.etNewDescription?.text.toString()
                )
                findNavController().navigateUp()
                AndroidUtils.hideKeyboard(requireView())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}