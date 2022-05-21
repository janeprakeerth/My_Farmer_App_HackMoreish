package com.example.myfarmer_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myfarmer_app.databinding.ActivityScientistanswertofarmeractivityBinding

class scientistanswertofarmeractivity : AppCompatActivity() {
    var binding :ActivityScientistanswertofarmeractivityBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScientistanswertofarmeractivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val question = intent.getStringExtra("question")
        val answer = intent.getStringExtra("answer")
        Toast.makeText(this,question.toString(),Toast.LENGTH_LONG).show()
        binding?.question?.text = question.toString()
        binding?.answer?.text = answer.toString()
    }
}