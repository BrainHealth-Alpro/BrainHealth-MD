package com.example.brainhealth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.brainhealth.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    companion object {
        const val IMAGE_DATA = "imageData"
    }
    private var res = ""
    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val uploadFragment = UploadFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_activity_fragment_container, uploadFragment)
            commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("onActivityResult", "ENTER")
        Log.d("onActivityResult", "requestCode == $requestCode, resultCode == $resultCode")
        //if (requestCode == 100 && resultCode == RESULT_OK) { // aneh bet ini, requestCode selalu random dan resultCode selalu -1
            Log.d("onActivityResult", "OK NICE")
//            val selectedFile = data?.data // The URI with the location of the file
//
//            // Convert URI to Bitmap
//            selectedFile?.let { uri ->
//                val inputStream = contentResolver.openInputStream(uri)
//                val bitmap = BitmapFactory.decodeStream(inputStream)
//
//                // Display Bitmap in ImageView
//                val submitFragment = SubmitFragment()
//                val imageView = submitFragment.view?.findViewById<ImageView>(R.id.imgChosen)
//                imageView?.setImageBitmap(bitmap)
//
//                supportFragmentManager.beginTransaction().apply {
//                    replace(R.id.main_activity_fragment_container, submitFragment)
//                    commit()
//                }
//            }
        //}
    }
}
