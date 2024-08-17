package com.example.brainhealth.ui.history

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainhealth.R
import com.example.brainhealth.databinding.FragmentDetailHistoryBinding

class DetailHistoryFragment : Fragment() {

    private var position: Int = 0
    private lateinit var username: String
    private lateinit var binding: FragmentDetailHistoryBinding
    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }


    private val viewModel: DetailHistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvHistory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvHistory.addItemDecoration(itemDecoration)

        return inflater.inflate(R.layout.fragment_detail_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        if (position == 1) {
            // TODO: All History
        } else if (position == 2) {
            // TODO: Sehat History
        } else {
            // TODO: Tidak Sehat History
        }
    }

//    private fun setUserData(userData: List<ItemsItem>) {
//        val adapter = UserAdapter()
//        adapter.submitList(userData)
//        binding.rvHistory.adapter = adapter
//    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}