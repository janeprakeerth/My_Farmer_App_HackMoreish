package com.example.myfarmer_app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myfarmer_app.databinding.ActivityQuestionactivityBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class questionactivity : AppCompatActivity() {
    var binding:ActivityQuestionactivityBinding?=null
    var myquestion:MyquestionModel?= null
    var PostUri : Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val scientistid = intent.getStringExtra("scientistid")
        val farmerid = intent.getStringExtra("farmerid")
        binding?.btnAskQuestion?.setOnClickListener {
            val question = binding?.etQuestion?.text?.toString()
            myquestion = MyquestionModel(scientistid!!,question!!,"",farmerid!!)
            CoroutineScope(Dispatchers.IO).launch{
                try{
                    val randomID = UUID.randomUUID().toString()
                    val StorageRef =
                        FirebaseStorage.getInstance().getReference("postImage/$randomID")
                    StorageRef.putFile(PostUri!!).addOnSuccessListener {
                    }.addOnFailureListener {
                    }.await()
                    myquestion = MyquestionModel(scientistid,question,randomID,farmerid)
                    FirebaseFirestore.getInstance().collection("Farmers").document(farmerid)
                        .update("myquestion", FieldValue.arrayUnion(myquestion)).await()
                    FirebaseFirestore.getInstance().collection("Scientists").document(scientistid)
                        .update("questionbyfarmers",FieldValue.arrayUnion(myquestion)).await()
                }catch (e:Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@questionactivity,e.message,Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        binding?.btnAddImageToTheQuestion?.setOnClickListener {
            com.github.dhaval2404.imagepicker.ImagePicker.with(this)
                .galleryMimeTypes(arrayOf("image/*")).crop().start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK && requestCode== com.github.dhaval2404.imagepicker.ImagePicker.REQUEST_CODE) {
            binding?.ivQuestionImage?.setImageURI(data?.data)
            PostUri = data?.data
        }

    }
}