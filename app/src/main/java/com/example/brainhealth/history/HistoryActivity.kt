package com.example.brainhealth.history

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.brainhealth.adapter.ViewPagerAdapter
import com.example.brainhealth.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        tabs()
        setupAction()
    }

    private fun tabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(AllHistoryFragment(), "Semua")
        adapter.addFragment(HealthyFragment(), "Sehat")
        adapter.addFragment(SickFragment(),"Tidak Sehat")

        binding.vpHistory.adapter = adapter
        binding.tabHistory.setupWithViewPager(binding.vpHistory)

    }

    private fun setupAction() {
        binding.mainButton.setOnClickListener {
            val intent = Intent(this@HistoryActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}