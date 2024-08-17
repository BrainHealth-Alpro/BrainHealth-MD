package com.example.brainhealth.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.brainhealth.R
import com.example.brainhealth.databinding.ActivityOnboarding3Binding
import com.example.brainhealth.login.LoginActivity
import com.example.brainhealth.login.LoginActivity.Companion.ROLE_ID

class Onboarding3Activity : AppCompatActivity() {

    private lateinit var binding : ActivityOnboarding3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboarding3Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.dokterButton.setOnClickListener {
            val intent = Intent(this@Onboarding3Activity, LoginActivity::class.java)
            intent.putExtra(ROLE_ID, 1)
            startActivity(intent)
        }

        binding.pasienButton.setOnClickListener {
            val intent = Intent(this@Onboarding3Activity, LoginActivity::class.java)
            intent.putExtra(ROLE_ID, 2)
            startActivity(intent)
        }
    }
}