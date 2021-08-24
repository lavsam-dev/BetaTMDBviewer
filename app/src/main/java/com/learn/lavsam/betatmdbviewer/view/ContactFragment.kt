package com.learn.lavsam.betatmdbviewer.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.learn.lavsam.betatmdbviewer.databinding.ContactFragmentBinding
import com.learn.lavsam.betatmdbviewer.viewmodel.ContactAppState
import com.learn.lavsam.betatmdbviewer.viewmodel.ContactViewModel

class ContactFragment : Fragment() {

    companion object {
        fun newInstance() = ContactFragment()
    }

    private var _binding: ContactFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContactViewModel by lazy {
        ViewModelProvider(this).get(ContactViewModel::class.java)
    }

    private val adapter: ContactAdapter by lazy {
        ContactAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ContactFragmentBinding.inflate(layoutInflater, container, false)
        binding.contactListList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.contacts.observe(viewLifecycleOwner) {
            renderData(it)
        }
        checkPermission()
    }

    private fun renderData(data: ContactAppState) {
        when (data) {
            is ContactAppState.Success -> {
                binding.contactListList.show()
                binding.includedLoadingLayout.loadingLayout.hide()
                adapter.contacts = data.data
            }
            is ContactAppState.Loading -> {
                binding.contactListList.hide()
                binding.includedLoadingLayout.loadingLayout.show()
            }
        }
    }

    private fun checkPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_CONTACTS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    getContacts()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к контактам")
                        .setMessage("Объяснение")
                        .setPositiveButton("Предоставить доступ") { _, _ ->
                            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                        }
                        .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                getContacts()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
                context?.let {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к контактам")
                        .setMessage("Объяснение")
                        .setNegativeButton("Закрыть") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
            }
        }


    private fun getContacts() {
        viewModel.getContacts()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun View.show(): View {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
        return this
    }

    private fun View.hide(): View {
        if (visibility != View.GONE) {
            visibility = View.GONE
        }
        return this
    }
}