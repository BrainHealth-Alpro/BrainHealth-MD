package com.example.brainhealth.ui.home

import android.text.Html;
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.brainhealth.R
import com.example.brainhealth.ViewModelFactory
import com.example.brainhealth.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resData = arguments?.getString("RESULT")
        if (resData == "Notumor") {
            binding.ivResult.setImageResource(R.drawable.brain_verdict_healthy)
            val resText = getString(R.string.result_healthy)
            binding.tvResult.text = Html.fromHtml(resText,  HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        else {
            binding.ivResult.setImageResource(R.drawable.brain_verdict_disease)
            val resText = getString(R.string.result_disease, resData)
            binding.tvResult.text = Html.fromHtml(resText,  HtmlCompat.FROM_HTML_MODE_LEGACY)
        }

        binding.btnReTake.setOnClickListener {
            val uploadFragment = UploadFragment()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.main_activity_fragment_container, uploadFragment)
                addToBackStack(null) // Optional: add to back stack if you want to navigate back
                commit()
            }
        }
    }


}