package com.example.myfarmer_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfarmer_app.databinding.ActivityChooseFarmerOrScientistBinding

class choose_farmer_or_scientist : AppCompatActivity() {
    var binding : ActivityChooseFarmerOrScientistBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChooseFarmerOrScientistBinding.inflate(layoutInflater)
        setContentView(binding?.root)


    }
}