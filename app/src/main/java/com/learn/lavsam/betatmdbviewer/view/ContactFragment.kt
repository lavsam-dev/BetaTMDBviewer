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

private const val ACCESS_TITLE = "Доступ к контактам"
private const val ACCESS_MESSAGE = "Объяснение"
private const val ACCESS_POSITIVE_BUTTON = "Предоставить доступ"
private const val ACCESS_NEGATIVE_BUTTON = "Не надо"
private const val ACCESS_DISMISS_BUTTON = "Закрыть"
private const val VISIBILITY_GONE = View.GONE
private const val VISIBILITY_VISIBLE = View.VISIBLE

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
                        .setTitle(ACCESS_TITLE)
                        .setMessage(ACCESS_MESSAGE)
                        .setPositiveButton(ACCESS_POSITIVE_BUTTON) { _, _ ->
                            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                        }
                        .setNegativeButton(ACCESS_NEGATIVE_BUTTON) { dialog, _ -> dialog.dismiss() }
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
                getContacts()
            } else {
                context?.let {
                    AlertDialog.Builder(it)
                        .setTitle(ACCESS_TITLE)
                        .setMessage(ACCESS_MESSAGE)
                        .setNegativeButton(ACCESS_DISMISS_BUTTON) { dialog, _ -> dialog.dismiss() }
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
        if (visibility != VISIBILITY_VISIBLE) {
            visibility = VISIBILITY_VISIBLE
        }
        return this
    }

    private fun View.hide(): View {
        if (visibility != VISIBILITY_GONE) {
            visibility = VISIBILITY_GONE
        }
        return this
    }
}