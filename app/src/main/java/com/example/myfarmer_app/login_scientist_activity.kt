package com.example.myfarmer_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myfarmer_app.databinding.ActivityLoginBinding
import com.example.myfarmer_app.databinding.ActivityLoginScientistBinding
import com.google.android.material.tabs.TabLayout

class login_scientist_activity : AppCompatActivity() {
    var binding: ActivityLoginScientistBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginScientistBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        binding?.tabLayout?.addTab(binding?.tabLayout!!.newTab().setText("Login"))
        binding?.tabLayout?.addTab(binding?.tabLayout!!.newTab().setText("Sign - Up"))
        binding?.tabLayout?.setTabGravity(TabLayout.GRAVITY_FILL)
        val adapter = scientist_viewpager_adapter(supportFragmentManager,this,binding?.tabLayout!!.tabCount)
        binding?.viewPager?.adapter = adapter
        binding?.viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding?.tabLayout))
        binding?.tabLayout?.setupWithViewPager(binding?.viewPager)
        binding?.tabLayout?.setTabTextColors(R.color.black,R.color.black)
    }
}