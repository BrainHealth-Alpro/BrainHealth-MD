package com.brainhealth.ui.history

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brainhealth.R
import com.brainhealth.ViewModelFactory

class RecommendationFragment : Fragment() {

    companion object {
        fun newInstance() = RecommendationFragment()
    }

    private val viewModel by viewModels<RecommendationViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_recommendation, container, false)
    }
}