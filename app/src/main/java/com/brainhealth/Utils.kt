package com.brainhealth

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.Date
import java.util.Locale

object Utils {

    fun isEmailValid(email: CharSequence): Boolean {
        val regex = Regex("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        return regex.matches(email)
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    fun String.parseTanggalLahir(): String? {
        val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)

        // Convert the date
        val parsedDate = inputFormat.parse(this)
        val formattedDate = parsedDate?.let { outputFormat.format(it) }

        return formattedDate
    }


    fun String.dateFormattedYYYYMMDD(): String? {
        val inputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Convert the date
        val parsedDate = inputFormat.parse(this)
        val formattedDate = parsedDate?.let { outputFormat.format(it) }
        return formattedDate
    }

    private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

    fun getImageUri(context: Context): Uri {
        var uri: Uri? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MyCamera/")
            }
            uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
        }
        return uri ?: getImageUriForPreQ(context)
    }

    private fun getImageUriForPreQ(context: Context): Uri {
        val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(filesDir, "/MyCamera/$timeStamp.jpg")
        if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()
        return FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            imageFile
        )
    }

    private fun createCustomTempFile(context: Context): File {
        val filesDir = context.externalCacheDir
        return File.createTempFile(timeStamp, ".jpg", filesDir)
    }

    fun uriToFile(imageUri: Uri, context: Context): File {
        val myFile = createCustomTempFile(context)
        val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
        val outputStream = FileOutputStream(myFile)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
        outputStream.close()
        inputStream.close()
        return myFile
    }

    private const val MAXIMAL_SIZE = 1000000

    fun File.reduceFile(): File {
        val file = this
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > MAXIMAL_SIZE)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }

    fun convertURL(localPath: String): String {
        val baseUrl = BuildConfig.BASE_URL
        val relativePath = localPath.replace("/app", "")
        return "$baseUrl$relativePath"
    }
}