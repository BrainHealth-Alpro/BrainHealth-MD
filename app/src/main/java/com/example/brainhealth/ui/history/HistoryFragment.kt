package com.example.brainhealth.ui.history

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.brainhealth.R
import com.example.brainhealth.databinding.FragmentHistoryBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HistoryFragment : Fragment() {
    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.history_keseluruhan, R.string.sehat, R.string.tidak_sehat
        )
    }

    private val username: String = ""
    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val historyViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity(), username.toString())
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