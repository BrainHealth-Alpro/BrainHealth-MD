package com.example.brainhealth.ui.profile

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.brainhealth.R
import com.example.brainhealth.ViewModelFactory
import com.example.brainhealth.databinding.FragmentProfileBinding
import com.example.brainhealth.di.db.ProfileResponse
import java.text.SimpleDateFormat
import java.util.Locale

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var currentImageUri: Uri? = null
    private var birthPlace = ""
    private var birthDate = ""


    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel.getSession().observe(requireActivity()) { user ->
            viewModel.getProfile(user.id)
        }

        viewModel.profile.observe(requireActivity()) {
            setupAssets(it)
        }

        binding.imageContainer.setOnClickListener{
            startGallery()
        }

        binding.updateButton.setOnClickListener {
            save()
        }
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }



        return root

    }

    private fun setupAssets(data: ProfileResponse) {
        setupData(data)
        setupBirthPlace(data.tempatLahir)
        setupBirthDate(data.tanggalLahir)
    }

    private fun setupData(data: ProfileResponse) {
        with(binding) {
//            nameProfile.setText(data.namaLengkap, TextView.BufferType.EDITABLE)
//            emailProfile.setText(data.email, TextView.BufferType.EDITABLE)
//            notelpProfile.setText(data.nomorTelepon, TextView.BufferType.EDITABLE)
            nameProfile.text = data.namaLengkap
            emailProfile.text = data.email
            notelpProfile.text = data.nomorTelepon
        }

        if (data.fotoProfil != null) {
            currentImageUri = data.fotoProfil.toUri()
            showImage()
        } else {
            binding.imageProfile.setImageResource(R.drawable.pasien)
        }

    }


    private fun setupBirthPlace(tempatLahir: String?) {
        val items = viewModel.indonesianCities
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, items)
        binding.birthPlace.adapter = adapter

        if (tempatLahir != null) {
            val initPos = items.indexOf(tempatLahir)
            if (initPos != -1) {
                binding.birthPlace.setSelection(initPos)
            }

        }

        binding.birthPlace.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                birthPlace = items[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun setupBirthDate(tanggalLahir: String?) {
        if (tanggalLahir != null ) {
            binding.birthDate.text = tanggalLahir
        }
        binding.birthDate.setOnClickListener {
            val initialDate = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                { view, year, monthOfYear, dayOfMonth ->
                    val date = Calendar.getInstance()
                    date.set(Calendar.YEAR, year)
                    date.set(Calendar.MONTH, monthOfYear)
                    date.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val simpleDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
                    val formattedDate = simpleDateFormat.format(date.time)
                    binding.birthDate.text = formattedDate

                }, initialDate.get(Calendar.YEAR),
                initialDate.get(Calendar.MONTH),
                initialDate.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }

    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.imageProfile.setImageURI(it)
        }
    }

    private fun save() {
        //...
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}