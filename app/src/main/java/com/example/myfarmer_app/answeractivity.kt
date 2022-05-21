package com.example.myfarmer_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myfarmer_app.Authobj.auth
import com.example.myfarmer_app.Authobj.db_farmer
import com.example.myfarmer_app.databinding.ActivityAnsweractivityBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class answeractivity : AppCompatActivity() {
    var binding:ActivityAnsweractivityBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnsweractivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val farmer_question = intent.getStringExtra("question")
        var question_image_id = intent.getStringExtra("imageid")
        var farmerId = intent.getStringExtra("farmerid").toString()
        Toast.makeText(this,question_image_id.toString(),Toast.LENGTH_LONG).show()
            try {
                val storageRef = FirebaseStorage.getInstance().reference
                storageRef.child("postImage/${question_image_id}").downloadUrl.addOnSuccessListener {
                    Glide.with(this).load(it.toString()).into(binding?.ivFarmerQuestionImage!!)
                }.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }

        binding?.tvAnsweractivity?.text = farmer_question.toString()
        binding?.btnAnsweractivity?.setOnClickListener {
            if(binding?.etAnsweractivity?.text?.toString()!!.isNotEmpty()){
                val answer = binding?.etAnsweractivity?.text?.toString()
                val answermodel = MyanswerModel(farmerId,auth.currentUser?.uid.toString(),answer!!,farmer_question.toString(),question_image_id.toString())
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        db_farmer.document(farmerId)
                            .update("scientistanswer", FieldValue.arrayUnion(answermodel)).await()
                        FirebaseFirestore.getInstance().collection("Scientists")
                            .document(auth.currentUser?.uid.toString())
                            .update("myanswer", FieldValue.arrayUnion(answermodel)).await()
                    }catch (e:Exception){
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@answeractivity,e.message,Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }else{
                Toast.makeText(this,"Please Enter the answer to the question",Toast.LENGTH_LONG).show()
            }
        }
    }
}