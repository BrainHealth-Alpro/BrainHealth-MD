package com.brainhealth.ui.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.brainhealth.R
import com.brainhealth.Utils.reduceFile
import com.brainhealth.Utils.uriToFile
import com.brainhealth.ViewModelFactory
import com.brainhealth.databinding.FragmentSubmitBinding
import com.brainhealth.UCropActivity

class SubmitFragment : Fragment() {
    private var _binding: FragmentSubmitBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private var currentImageUri: Uri? = null
    private var errorMessage = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubmitBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uri = arguments?.getString("URI")
        val username = arguments?.getString("username") ?: ""
        val userId = arguments?.getInt("userId", 0) ?: 0
        currentImageUri = Uri.parse(uri)
        binding.imgChosen.setImageURI(currentImageUri)
        binding.tvUsername.text = username
        binding.btnCrop.setOnClickListener {
            val intent = Intent(requireActivity(), UCropActivity::class.java)
            intent.putExtra(HomeFragment.IMAGE_DATA, currentImageUri.toString())
            launcherCropImage.launch(intent)
        }


        binding.btnSubmit.setOnClickListener {
            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, requireActivity()).reduceFile()
                viewModel.uploadImage(imageFile, userId, username)
            }  ?: Toast.makeText(requireActivity(), "Failed upload", Toast.LENGTH_SHORT).show()


        }

        viewModel.isError.observe(viewLifecycleOwner) {
            showError(it)
        }

        viewModel.message.observe(viewLifecycleOwner) {
            if (it != null) {
                errorMessage = it
            }
        }

        viewModel.uploadResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel.setUploadResponseNull() // Bisa dihapus jika tidak perlu, hanya agar bisa diback
                val res = it.result
                val resultFragment = ResultFragment().apply {
                    arguments = Bundle().apply {
                        putString("RESULT", res)
                    }
                }
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.main_activity_fragment_container, resultFragment)
//                    addToBackStack(null) // Optional: add to back stack if you want to navigate back
                    commit()
                }
            }

        }

        viewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
    }


    private val launcherCropImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.getStringExtra(UCropActivity.CROP)?.let { croppedResult ->
                    val uri = Uri.parse(croppedResult)
                    currentImageUri = uri
                    binding.imgChosen.setImageURI(currentImageUri)
                }
            }
        }



    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showError(isError: Boolean) {
        if (isError) {
            Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

}