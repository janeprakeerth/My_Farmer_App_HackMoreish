package com.example.myfarmer_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.myfarmer_app.databinding.ActivityFarmerMainInterfaceBinding
import com.example.myfarmer_app.databinding.ActivityMainBinding
import com.example.myfarmer_app.databinding.ActivityScientistMainInterfaceBinding

class Scientist_Main_Interface : AppCompatActivity() {
    var binding :ActivityScientistMainInterfaceBinding?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScientistMainInterfaceBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btmNavMainScientist?.add(MeowBottomNavigation.Model(0 ,R.drawable.ic_baseline_home_24))
        binding?.btmNavMainScientist?.add(MeowBottomNavigation.Model(1 ,R.drawable.ic_baseline_post_add_24))
        binding?.btmNavMainScientist?.add(MeowBottomNavigation.Model(2 , R.drawable.ic_baseline_question_mark_24))
        binding?.btmNavMainScientist?.add(MeowBottomNavigation.Model(3 , R.drawable.ic_baseline_person_outline_24))

        val fragmentHome = fragment_home_scientist()
        val fragmentPost = fragment_post_scientist()
        val fragmentMyQuestion = fragment_my_question_scientist()
        val fragmentProfile = fragment_profile_scientist()
        setcurrentfragment(fragmentHome)

        binding?.btmNavMainScientist?.setOnClickMenuListener {
            when(it.id)
            {
                0->{
                    setcurrentfragment(fragmentHome)
                }
                1->{
                    setcurrentfragment(fragmentPost)
                }

                2->{
                    setcurrentfragment(fragmentMyQuestion)

                }
                3->{
                    setcurrentfragment(fragmentProfile)

                }
            }
        }


    }
    fun setcurrentfragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply{
            replace(binding?.flForFragmentsScientist?.id!! ,fragment )
            commit()
        }
    }
}