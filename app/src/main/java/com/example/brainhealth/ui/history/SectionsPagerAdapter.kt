package com.example.brainhealth.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = DetailHistoryFragment()
        fragment.arguments = Bundle().apply {
            putInt(DetailHistoryFragment.ARG_POSITION, position+1)
        }

        return fragment
    }


}