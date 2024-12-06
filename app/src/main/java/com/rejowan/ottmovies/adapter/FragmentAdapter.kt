package com.rejowan.ottmovies.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rejowan.ottmovies.ui.fragment.Home
import com.rejowan.ottmovies.ui.fragment.Latest

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                Home()
            }

            1 -> {
                Latest()
            }

            else -> {
                Home()
            }
        }
    }


}