package com.example.myfarmer_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.myfarmer_app.databinding.SplashscreenlayotBinding
import kotlinx.coroutines.*


class SplashscreenActivity : AppCompatActivity() {

    var binding : SplashscreenlayotBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashscreenlayotBinding.inflate(layoutInflater)
        setContentView(binding?.root)





        binding?.imgBack?.animate()?.translationY(-2800f)?.setDuration(1000)?.setStartDelay(4000)
        binding?.idLogo?.animate()?.translationY(2000f)?.setDuration(1000)?.setStartDelay(4000)
        binding?.appName?.animate()?.translationY(1600f)?.setDuration(1000)?.setStartDelay(4000)
        binding?.lottie?.animate()?.translationY(1400f)?.setDuration(1000)?.setStartDelay(4000)


        class ScreenSlidePageAdapter(fm: FragmentManager) :
            FragmentStatePagerAdapter(fm) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        fragment1()
                    }

                    1 -> {
                        fragmnet2()
                    }

                    2 -> {
                        fragment3()
                    }



                    else ->
                        fragment1()

                }
            }

            override fun getCount(): Int {
                return 3
            }
        }


        CoroutineScope(Dispatchers.IO).launch {

            delay(5000)
            withContext(Dispatchers.Main) {

                val adapter = ScreenSlidePageAdapter(supportFragmentManager)

                binding?.pager?.adapter = adapter
            }
        }

    }
}