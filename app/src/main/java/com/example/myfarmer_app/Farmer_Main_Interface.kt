package com.example.myfarmer_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.myfarmer_app.databinding.ActivityFarmerMainInterfaceBinding
import com.example.myfarmer_app.databinding.ActivityMainBinding

class Farmer_Main_Interface : AppCompatActivity() {
    var binding :ActivityFarmerMainInterfaceBinding?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFarmerMainInterfaceBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btmNavMain?.add(MeowBottomNavigation.Model(0 ,R.drawable.ic_baseline_home_24))
        binding?.btmNavMain?.add(MeowBottomNavigation.Model(1 ,R.drawable.ic_baseline_post_add_24))
        binding?.btmNavMain?.add(MeowBottomNavigation.Model(2 , R.drawable.ic_baseline_add_circle_24))
        binding?.btmNavMain?.add(MeowBottomNavigation.Model(3 , R.drawable.ic_baseline_question_mark_24))
        binding?.btmNavMain?.add(MeowBottomNavigation.Model(4 , R.drawable.ic_baseline_person_outline_24))


        val fragmentHome = fragment_home()
        val fragmentPost = fragment_post()
        val fragmentAdd = fragment_add()
        val fragmentMyQuestion = fragment_my_question()
        val fragmentProfile = fragment_profile()
        setcurrentfragment(fragmentHome)

        binding?.btmNavMain?.setOnClickMenuListener {
            when(it.id)
            {
                0->{
                    setcurrentfragment(fragmentHome)
                }
                1->{
                    setcurrentfragment(fragmentPost)
                }
                2->{
                    setcurrentfragment(fragmentAdd)

                }
                3->{
                    setcurrentfragment(fragmentMyQuestion)

                }
                4->{
                    setcurrentfragment(fragmentProfile)

                }

            }
        }


    }
    fun setcurrentfragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply{
            replace(binding?.flForFragments?.id!! ,fragment )
            commit()
        }
    }
}