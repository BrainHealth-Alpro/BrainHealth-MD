package com.example.brainhealth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yalantis.ucrop.UCrop
import java.io.File
import java.lang.StringBuilder
import java.util.UUID

class UCropActivity : AppCompatActivity() {

    private var sourceUri: String = ""
    private var destinationUri: String = ""
    private lateinit var uri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ucrop)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (intent.extras != null) {
            sourceUri = intent.getStringExtra(HomeActivity.IMAGE_DATA).toString()
            uri = Uri.parse(sourceUri)
        }

        destinationUri = StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString()

        val options = UCrop.Options()

        UCrop.of(uri, Uri.fromFile(File(cacheDir, destinationUri)))
            .withOptions(options)
            .withMaxResultSize(2000, 2000)
            .start(this@UCropActivity)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri: Uri? = UCrop.getOutput(data!!)
            val intent = Intent()
            intent.putExtra(CROP, resultUri.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError: Throwable? = UCrop.getError(data!!)
        } else if (resultCode == Activity.RESULT_CANCELED) {
            setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }


    companion object {
        const val CROP = "crop"
    }
}