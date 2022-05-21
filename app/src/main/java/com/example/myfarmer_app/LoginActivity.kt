package com.example.myfarmer_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.myfarmer_app.databinding.ActivityLoginBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    var binding: ActivityLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        binding?.tabLayout?.addTab(binding?.tabLayout!!.newTab().setText("Login"))
        binding?.tabLayout?.addTab(binding?.tabLayout!!.newTab().setText("Sign - Up"))
        binding?.tabLayout?.setTabGravity(TabLayout.GRAVITY_FILL)
        val adapter = farmer_viewpager_adapter(supportFragmentManager,this,binding?.tabLayout!!.tabCount)
        binding?.viewPager?.adapter = adapter
        binding?.viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding?.tabLayout))
        binding?.tabLayout?.setupWithViewPager(binding?.viewPager)
        binding?.tabLayout?.setTabTextColors(R.color.black,R.color.black)
    }
}