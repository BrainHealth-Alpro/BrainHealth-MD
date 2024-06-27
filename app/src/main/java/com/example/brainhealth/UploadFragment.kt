package com.example.brainhealth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class UploadFragment : Fragment(R.layout.fragment_upload) {
//    private fun selectImageInAlbum() {
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "image/*"
//        if (intent.resolveActivity(packageManager) != null) {
//            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
//        }
//    }
//    private fun takePhoto() {
//        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if (intent1.resolveActivity(packageManager) != null) {
//            startActivityForResult(intent1, REQUEST_TAKE_PHOTO)
//        }
//    }
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

        view.findViewById<Button>(R.id.btnBrowse)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
                .setType("image/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Select a file"), 100)
        }
    }
}