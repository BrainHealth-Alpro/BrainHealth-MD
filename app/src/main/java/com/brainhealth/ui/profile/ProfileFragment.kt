package com.brainhealth.ui.profile

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.brainhealth.R
import com.brainhealth.Utils
import com.brainhealth.Utils.convertURL
import com.brainhealth.Utils.dateFormattedYYYYMMDD
import com.brainhealth.Utils.parseTanggalLahir
import com.brainhealth.Utils.reduceFile
import com.brainhealth.ViewModelFactory
import com.brainhealth.databinding.FragmentProfileBinding
import com.brainhealth.di.db.ProfileResponse
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class ProfileFragment : Fragment() {


    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var binding: FragmentProfileBinding

    private var currentImageUri: Uri? = null
    private var birthPlace = ""
    private var password = ""
    private var userId = 0
    private var type = "pasien"


    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(layoutInflater)
        val root: View = binding.root


        return root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            viewModel.getProfile(user.id)
        }
        viewModel.profile.observe(viewLifecycleOwner) {
            if (it != null) {
                password = it.kataSandi
                userId = it.id
                type = it.tipe
                setupAssets(it)
            }

        }

        viewModel.profileUpdateResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                showSuccess(it.message)
                viewModel.nullProfileUpdate()
            }
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
    }

    private fun setupAssets(data: ProfileResponse) {
        if (!isAdded) return
        setupData(data)
        setupBirthPlace(data.tempatLahir)
        setupBirthDate(data.tanggalLahir)
    }

    private fun setupData(data: ProfileResponse) {
        with(binding) {
            nameProfile.setText(data.namaLengkap, TextView.BufferType.EDITABLE)
            emailProfile.setText(data.email, TextView.BufferType.EDITABLE)
            notelpProfile.setText(data.nomorTelepon, TextView.BufferType.EDITABLE)
//            nameProfile.text = data.namaLengkap
//            emailProfile.text = data.email
//            notelpProfile.text = data.nomorTelepon
        }

        if (data.fotoProfil != null) {
            val link = convertURL(data.fotoProfil)
            Glide.with(this).load(link).into(binding.imageProfile)
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
            binding.birthDate.text = tanggalLahir.parseTanggalLahir()
        } else {
            binding.birthDate.text = "January 01, 2024"
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun save() {
        val fullName = binding.nameProfile.text
        val email = binding.emailProfile.text
        val phoneNum = binding.notelpProfile.text
        val birthDate = binding.birthDate.text.toString()
        if (currentImageUri == null) {
            birthDate.dateFormattedYYYYMMDD()?.let {
                viewModel.updateProfile(userId, fullName.toString(),
                    email.toString(), phoneNum.toString(), null, birthPlace,
                    it, null, type)
            }
        }
        currentImageUri?.let { uri ->
            val imageFile = Utils.uriToFile(uri, requireActivity()).reduceFile()

            birthDate.dateFormattedYYYYMMDD()?.let {
                viewModel.updateProfile(userId, fullName.toString(),
                    email.toString(), phoneNum.toString(), imageFile, birthPlace,
                    it, null, type)
            }
        }


    }

    private fun showSuccess(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()

    }

}