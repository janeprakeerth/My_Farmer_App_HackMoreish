package com.example.myfarmer_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfarmer_app.Authobj.auth
import com.example.myfarmer_app.databinding.ActivityQuestionansweractivityBinding
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class questionansweractivity : AppCompatActivity() {
    var binding :ActivityQuestionansweractivityBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionansweractivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var querySnapshot = Authobj.db_farmer.whereEqualTo("uid", auth.currentUser?.uid).get().await()
                var current_user_object: farmer_registration_details? = null
                for (document in querySnapshot.documents) {
                    current_user_object = document.toObject<farmer_registration_details>()
                }
                withContext(Dispatchers.Main) {
//                    Toast.makeText(
//                        this@questionansweractivity,
//                        current_user_object?.scientistanswer.toString(),
//                        Toast.LENGTH_SHORT
//                    ).show()
                    val adapter = questionansweradapter(current_user_object?.scientistanswer!!)
                    binding?.rvQuestionansweractivity?.adapter = adapter
                    binding?.rvQuestionansweractivity?.layoutManager = LinearLayoutManager(this@questionansweractivity)
                    binding?.rvQuestionansweractivity?.addItemDecoration(DividerItemDecoration(binding?.rvQuestionansweractivity?.context,(binding?.rvQuestionansweractivity?.layoutManager as LinearLayoutManager).orientation))

                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@questionansweractivity,e.message,Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}