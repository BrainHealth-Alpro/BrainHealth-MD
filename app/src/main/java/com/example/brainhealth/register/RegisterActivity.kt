package com.example.brainhealth.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.brainhealth.ViewModelFactory
import com.example.brainhealth.databinding.ActivityRegisterBinding
import com.example.brainhealth.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        setupAction()

        observe()
    }

    private fun setupAction() {
        binding.signInButton.setOnClickListener {
            val fullName = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val phoneNum = binding.etNotelp.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.postData(fullName, email, phoneNum, password)
        }
    }

    private fun observe() {
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        viewModel.message.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        viewModel.registerResponse.observe(this) {
            if (it != null) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                intent.putExtra(LoginActivity.ROLE_ID, 2)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}