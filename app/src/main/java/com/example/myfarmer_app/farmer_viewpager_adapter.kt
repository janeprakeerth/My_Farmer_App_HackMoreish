package com.example.myfarmer_app

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class farmer_viewpager_adapter(fm: FragmentManager?, private val context: Context, var totalTabs: Int) :
    FragmentPagerAdapter(fm!!) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                fragmen_login_tab_farmer()
            }
            1 -> {
                fragment_signup_tab_farmer()
            }
            else ->  fragmen_login_tab_farmer()

        }
    }
}