package com.example.myfarmer_app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.lang.Exception

class fragment3 : Fragment(R.layout.fragment3pager) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view  : View = inflater.inflate(R.layout.fragment3pager , container , false)

        val tv  : Button = view.findViewById(R.id.tv_next)
        try {
            tv.setOnClickListener {
                try {
                    val intent = Intent(context, farmer_or_scientist_selecter::class.java)
                    startActivity(intent)
                    activity?.finish()
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }catch (e:Exception){
            Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
        }

        return view


    }
}