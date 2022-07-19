package com.company.yandexmapstest.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.company.yandexmapstest.R
import com.company.yandexmapstest.databinding.FragmentEditBinding
import com.company.yandexmapstest.util.AndroidUtils
import com.company.yandexmapstest.util.MarkerDescArg
import com.company.yandexmapstest.util.MarkerPreferences
import com.company.yandexmapstest.viewmodel.MarkerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditFragment : Fragment() {

    @Inject
    lateinit var prefs: MarkerPreferences

    companion object {
        var Bundle.markerDescArg: String? by MarkerDescArg
    }

    private var fragmentBinding: FragmentEditBinding? = null


    private val viewModel: MarkerViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_marker, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                viewModel.editById(
                    prefs.id?.toLong()!!,
                    fragmentBinding?.etNewDescription?.text.toString()
                )
                findNavController().navigateUp()
                AndroidUtils.hideKeyboard(requireView())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditBinding.inflate(inflater, container, false)

        fragmentBinding = binding

        arguments?.markerDescArg
            ?.let(binding.etNewDescription::setText)

        binding.etNewDescription.requestFocus()

        return binding.root
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}