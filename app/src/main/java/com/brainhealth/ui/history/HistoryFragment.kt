package com.brainhealth.ui.history


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.brainhealth.R
import com.brainhealth.ViewModelFactory
import com.brainhealth.databinding.FragmentHistoryBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HistoryFragment : Fragment() {
    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.history_keseluruhan, R.string.sehat, R.string.tidak_sehat
        )
    }

    private var _binding: FragmentHistoryBinding? = null

    private val viewModel by viewModels<HistoryViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity())
        val viewPager: ViewPager2 = binding.vpHistory
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabHistory
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        setupView()
        return root
    }

    private fun setupView() {
        activity?.window?.statusBarColor = context?.let { ContextCompat.getColor(it, R.color.baby_blue) }!!
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}