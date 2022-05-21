package com.example.myfarmer_app

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class scientist_viewpager_adapter(fm: FragmentManager?, private val context: Context, var totalTabs: Int) :
    FragmentPagerAdapter(fm!!) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                fragment_login_tab_scientist()
            }
            1 -> {
                fragment_signup_tab_scientist()
            }

            else ->  fragment_login_tab_scientist()

        }
    }
}