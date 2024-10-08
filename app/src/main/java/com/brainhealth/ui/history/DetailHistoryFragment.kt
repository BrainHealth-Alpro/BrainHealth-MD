package com.brainhealth.ui.history

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.brainhealth.ViewModelFactory
import com.brainhealth.databinding.FragmentDetailHistoryBinding
import com.brainhealth.di.db.HistoryItem
import com.brainhealth.onboarding.OnboardingActivity
import com.brainhealth.adapter.HistoryAdapter

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

        observe()

        return root
    }
    override fun onResume() {
        super.onResume()
        observe()
    }


    private fun observe() {
        viewModel.isDanger.observe(viewLifecycleOwner) {
            if (it) {
                binding.tvWarning.visibility = View.VISIBLE
            } else {
                binding.tvWarning.visibility = View.GONE
            }
        }
        if (position == 1) {
            viewModel.getSession().observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    viewModel.getHistory(user.id)
                } else {
                    val intent = Intent(requireActivity(), OnboardingActivity::class.java)
                    startActivity(intent)
                }
            }

            viewModel.listHistory.observe(viewLifecycleOwner) {
                setData(it)
            }
            viewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            viewModel.isError.observe(viewLifecycleOwner) {
                showError(it)
            }

        } else if (position == 2) {
            viewModel.getSession().observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    viewModel.getHistorySehat(user.id)
                } else {
                    val intent = Intent(requireActivity(), OnboardingActivity::class.java)
                    startActivity(intent)
                }
            }
            viewModel.listHistory.observe(viewLifecycleOwner) {
                setData(it)
            }
            viewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            viewModel.isError.observe(viewLifecycleOwner) {
                showError(it)
            }
        } else {
            viewModel.getSession().observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    viewModel.getHistoryTidakSehat(user.id)
                } else {
                    val intent = Intent(requireActivity(), OnboardingActivity::class.java)
                    startActivity(intent)
                }
            }
            viewModel.listHistory.observe(viewLifecycleOwner) {
                setData(it)
            }
            viewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            viewModel.isError.observe(viewLifecycleOwner) {
                showError(it)
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

    private fun showError(isError: Boolean) {
        if (isError) {
            Toast.makeText(requireActivity(), "Ada kesalahan", Toast.LENGTH_SHORT).show()
        }
    }
}