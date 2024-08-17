package com.example.brainhealth

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.brainhealth.Utils.getImageUri
import com.example.brainhealth.databinding.FragmentUploadBinding

class UploadFragment : Fragment() {
    private var _binding: FragmentUploadBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private var currentImageUri: Uri? = null

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnBrowse.setOnClickListener {
            startGallery()
        }

        binding.btnCamera.setOnClickListener {
            startCamera()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val submitFragment = SubmitFragment()
//
//        view.findViewById<Button>(R.id.btnBrowse)?.setOnClickListener {
//            activity?.apply {
//                supportFragmentManager.beginTransaction().apply {
//                    replace(R.id.main_activity_fragment_container, submitFragment)
//                    addToBackStack(null) // Optional: add to back stack if you want to navigate back
//                    commit()
//                }
//            }
//        }

//        view.findViewById<Button>(R.id.btnBrowse)?.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK)
//                .setType("image/*")
//                .setAction(Intent.ACTION_GET_CONTENT)
//
//            startActivityForResult(Intent.createChooser(intent, "Select a file"), 100)
//        }
        viewModel.currentImageUri.observe(requireActivity()) {
            if (it != null) {
                viewModel.setCurrentImageUri(null) // agar bisa kembali ke halaman ini
                val submitFragment = SubmitFragment().apply {
                    arguments = Bundle().apply {
                        putString("URI", it.toString())
                    }
                }
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.main_activity_fragment_container, submitFragment)
                    addToBackStack(null) // Optional: add to back stack if you want to navigate back
                    commit()
                }
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireActivity())
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.setCurrentImageUri(uri)
//            showImage()
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            currentImageUri?.let { viewModel.setCurrentImageUri(it) }
        }
    }

//    private fun showImage() {
//        currentImageUri?.let {
//            binding.previewImage.setImageURI(it)
//        }
//    }

}