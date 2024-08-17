package com.example.brainhealth.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainhealth.R
import com.example.brainhealth.adapter.ItemRowAdapter
import com.example.brainhealth.databinding.FragmentHealthyBinding
import com.example.brainhealth.di.db.HistoryData

class HealthyFragment : Fragment() {

    private lateinit var binding: FragmentHealthyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHealthyBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()

        return view
    }

    private fun setupRecyclerView() {
        val healthyData = filterHealthyData()
        val adapter = ItemRowAdapter(requireContext(), healthyData)
        binding.rvHealthy.adapter = adapter
        binding.rvHealthy.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun filterHealthyData(): List<HistoryData> {
        val allData = createStaticData()
        return allData.filter { it.result == "Tidak ada tumor" }
    }

    private fun createStaticData(): List<HistoryData> {
        return listOf(
            HistoryData("John Doe", "Tidak ada tumor", "-"),
            HistoryData("Jane Smith", "Terdapat tumor terdeteksi", "Glioma"),
            HistoryData("Michael Brown", "Terdapat tumor terdeteksi", "Miningioma"),
            HistoryData("Dazzy Rawl", "Tidak ada tumor", "-")
        )
    }
}
