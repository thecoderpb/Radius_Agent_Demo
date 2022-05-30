package com.assignment.radiusagentdemo.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.assignment.radiusagentdemo.fragments.FirstFragment
import com.assignment.radiusagentdemo.fragments.SecondFragment
import com.assignment.radiusagentdemo.fragments.ThirdFragment

@Suppress("DEPRECATION")
class ViewPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val COUNT = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FirstFragment(1,)
            1 -> SecondFragment(2,)
            2 -> ThirdFragment(3,)
            else -> FirstFragment(1,)
        }

    }

    override fun getCount(): Int {
        return COUNT
    }

}