package com.example.brainhealth.ui.history

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainhealth.R
import com.example.brainhealth.ViewModelFactory
import com.example.brainhealth.adapter.HistoryAdapter
import com.example.brainhealth.databinding.FragmentDetailHistoryBinding
import com.example.brainhealth.di.db.HistoryItem
import com.example.brainhealth.onboarding.OnboardingActivity
import kotlin.properties.Delegates

class DetailHistoryFragment : Fragment() {

    private var position = 0
    private lateinit var binding: FragmentDetailHistoryBinding
    companion object {
        const val ARG_POSITION = "position"
    }


    private val viewModel by viewModels<DetailHistoryViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailHistoryBinding.inflate(layoutInflater)
        val root: View = binding.root

        arguments?.let {
            position = it.getInt(ARG_POSITION)
        }


        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvHistory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvHistory.addItemDecoration(itemDecoration)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.isDanger.observe(requireActivity()) {
            println("WEYNARD DANGER $it")
            if (it) {
                binding.tvWarning.visibility = View.VISIBLE
            } else {
                binding.tvWarning.visibility = View.GONE
            }
        }

        if (position == 1) {
            viewModel.getSession().observe(requireActivity()) { user ->
                if (user != null) {
                    viewModel.getHistory(user.id)
                } else {
                    val intent = Intent(requireActivity(), OnboardingActivity::class.java)
                    startActivity(intent)
                }
            }

            viewModel.listHistory.observe(requireActivity()) {
                setData(it)
            }
            viewModel.isLoading.observe(requireActivity()) {
                showLoading(it)
            }

        } else if (position == 2) {
            viewModel.getSession().observe(requireActivity()) { user ->
                if (user != null) {
                    viewModel.getHistorySehat(user.id)
                } else {
                    val intent = Intent(requireActivity(), OnboardingActivity::class.java)
                    startActivity(intent)
                }
            }
            viewModel.listHistory.observe(requireActivity()) {
                setData(it)
            }
            viewModel.isLoading.observe(requireActivity()) {
                showLoading(it)
            }
        } else {
            viewModel.getSession().observe(requireActivity()) { user ->
                if (user != null) {
                    viewModel.getHistoryTidakSehat(user.id)
                } else {
                    val intent = Intent(requireActivity(), OnboardingActivity::class.java)
                    startActivity(intent)
                }
            }
            viewModel.listHistory.observe(requireActivity()) {
                setData(it)
            }
            viewModel.isLoading.observe(requireActivity()) {
                showLoading(it)
            }
        }
    }

    private fun setData(userData: List<HistoryItem>) {
        val adapter = HistoryAdapter()
        adapter.submitList(userData)
        binding.rvHistory.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}