package com.example.brainhealth.login

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.brainhealth.MainActivity
import com.example.brainhealth.R
import com.example.brainhealth.ViewModelFactory
import com.example.brainhealth.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    companion object {
        const val ROLE_ID = "roleId"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupView()
        observe()
    }

    private fun setupView() {
        val roleId = intent.getIntExtra(ROLE_ID, 1)
        if (roleId == 1) {
            binding.tvWelcome.setText(R.string.welcome_text_dokter)
            binding.registerButton.visibility = View.GONE
        }
        else if (roleId == 2) { // Pasien
            binding.ivHeader.setImageResource(R.drawable.pasien)
            binding.tvWelcome.setText(R.string.welcome_text_pasien)
            binding.registerButton.text = Html.fromHtml(getString(R.string.register),  HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    private fun observe() {
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.isError.observe(this) {
            if (it == true) {
                viewModel.message.observe(this) { msg ->
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.loginResponse.observe(this) {
            if (it != null) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}