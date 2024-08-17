package com.example.brainhealth.ui.home

import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build.VERSION_CODES.S
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.brainhealth.R
import com.example.brainhealth.Utils.getImageUri
import com.example.brainhealth.ViewModelFactory
import com.example.brainhealth.databinding.FragmentUploadBinding

class UploadFragment : Fragment() {
    private var _binding: FragmentUploadBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private var currentImageUri: Uri? = null
    private var userId: Int = -1
    private var type: String = ""
    private var username: String = ""

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

        viewModel.getSession().observe(requireActivity()) { user ->
            userId = user.id
            type = user.type
            username = user.name
        }


        binding.btnBrowse.setOnClickListener {
            startGallery()
        }

        binding.btnCamera.setOnClickListener {
            startCamera()
        }

        binding.btnDcm.setOnClickListener {
            startDcm()
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
                        putInt("userId", userId)
                        putString("username", username)
                    }
                }
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.main_activity_fragment_container, submitFragment)
                    addToBackStack(null) // Optional: add to back stack if you want to navigate back
                    commit()
                }
            }
        }

        viewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireActivity())
        launcherIntentCamera.launch(currentImageUri)
    }

    private fun startDcm() {
        val builder = AlertDialog.Builder(requireActivity(), R.style.CustomAlertDialog)
        val dialogView = layoutInflater.inflate(R.layout.custom_alert_dialog, null)
        val title = dialogView.findViewById<TextView>(R.id.titleDialog)
        title.text = getString(R.string.dcm_header)
        val input = dialogView.findViewById<EditText>(R.id.editText).text
        val button = dialogView.findViewById<Button>(R.id.submit_button)
        builder.setView(dialogView)
        val dialog = builder.create()

        dialog.show()

        button.setOnClickListener {
            val link = input.toString()
            dialog.dismiss()
            if (type == "pasien") {
                viewModel.uploadLink(link, userId, username)
                viewModel.uploadResponse.observe(requireActivity()) {
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
                            addToBackStack(null) // Optional: add to back stack if you want to navigate back
                            commit()
                        }
                    }
                }
            } else {
                val builder2 = AlertDialog.Builder(requireActivity(), R.style.CustomAlertDialog)
                val dialogView2 = layoutInflater.inflate(R.layout.custom_alert_dialog, null)
                val title2 = dialogView2.findViewById<TextView>(R.id.titleDialog)
                title2.text = getString(R.string.input_name_header)
                val input2 = dialogView2.findViewById<EditText>(R.id.editText).text
                val button2 = dialogView2.findViewById<Button>(R.id.submit_button)
                builder2.setView(dialogView2) // Corrected this line
                val dialog2 = builder2.create()

                dialog2.show()

                button2.setOnClickListener {
                    username = input2.toString()
                    viewModel.uploadLink(link, userId, username)
                    viewModel.uploadResponse.observe(requireActivity()) {
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
                                addToBackStack(null) // Optional: add to back stack if you want to navigate back
                                commit()
                            }
                        }
                    }
                    dialog2.dismiss()
                }
            }
        }
    }


    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            if (type == "dokter")
                showDialog(uri)
            else viewModel.setCurrentImageUri(uri)
//            showImage()

        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {

            currentImageUri?.let {
                if (type == "dokter")
                    showDialog(it)
                else viewModel.setCurrentImageUri(it)
            }
        }
    }

//    private fun showImage() {
//        currentImageUri?.let {
//            binding.previewImage.setImageURI(it)
//        }
//    }

    private fun showDialog(uri: Uri) {
        val builder = AlertDialog.Builder(requireActivity(), R.style.CustomAlertDialog)
        val dialogView = layoutInflater.inflate(R.layout.custom_alert_dialog, null)
        val title = dialogView.findViewById<TextView>(R.id.titleDialog)
        title.text = getString(R.string.input_name_header)
        val input = dialogView.findViewById<EditText>(R.id.editText).text
        val button = dialogView.findViewById<Button>(R.id.submit_button)
        builder.setView(dialogView)
        val dialog = builder.create()

        dialog.show()

        button.setOnClickListener {
            username = input.toString()
            viewModel.setCurrentImageUri(uri)
            dialog.dismiss()
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            binding.progressText.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            binding.progressText.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        }
    }

}