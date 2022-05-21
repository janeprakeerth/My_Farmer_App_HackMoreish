package com.example.myfarmer_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.myfarmer_app.databinding.ActivityFarmerOrScientistSelecterBinding

class farmer_or_scientist_selecter : AppCompatActivity() {
    var binding : ActivityFarmerOrScientistSelecterBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFarmerOrScientistSelecterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        YoYo.with(Techniques.RubberBand).duration(1000).repeat(10000).playOn(binding?.tvHello)
        binding?.farmer?.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        binding?.scientist?.setOnClickListener {
            val intent = Intent(this,login_scientist_activity::class.java)
            startActivity(intent)
        }
    }
}