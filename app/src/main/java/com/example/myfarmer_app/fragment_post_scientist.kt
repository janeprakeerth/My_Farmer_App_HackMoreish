package com.example.myfarmer_app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myfarmer_app.Authobj.db_post
import com.example.myfarmer_app.Authobj.db_scientist
import com.google.firebase.storage.FirebaseStorage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class fragment_post_scientist: Fragment(R.layout.fragment_post_scientist) {
    var image_post_scientist:ImageView?=null
    var PostUri : Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK && requestCode== com.github.dhaval2404.imagepicker.ImagePicker.REQUEST_CODE) {
            image_post_scientist?.setImageURI(data?.data)
            PostUri = data?.data

        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View? = inflater.inflate(R.layout.fragment_post_scientist , container , false)

        val btn_add_post_scientist  : Button = view!!.findViewById(R.id.btn_add_post_scientist)
        val camera_button_scientist:Button = view.findViewById(R.id.btn_camera_scientist)
        val gallery_button_scientist:Button = view.findViewById(R.id.btn_gallery_scientist)
        image_post_scientist= view.findViewById(R.id.iv_post_scientist)
        camera_button_scientist.setOnClickListener {
            com.github.dhaval2404.imagepicker.ImagePicker.with(this).cameraOnly().crop().start()
        }
        gallery_button_scientist.setOnClickListener {
            com.github.dhaval2404.imagepicker.ImagePicker.with(this).galleryMimeTypes(arrayOf("image/*")).crop().start()
        }


        btn_add_post_scientist.setOnClickListener {
            val currentUser = Authobj.auth.currentUser!!.uid

            val editText_scientist  : EditText = view.findViewById(R.id.et_add_post_description_scientist)
            val question1_scientist:EditText = view.findViewById(R.id.et_add_a_answer_scientist)


            if(editText_scientist.text.toString().isNotEmpty() && question1_scientist.text.toString().isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val documentSnapshot = db_scientist.whereEqualTo("uid", currentUser).get().await()
                        var scientist: scientist_registration_details? = null


                        for (document in documentSnapshot.documents) {
                            scientist = document.toObject(scientist_registration_details::class.java)

                        }

                        val scientist_name = scientist?.name?.toString()

                        val randomID = UUID.randomUUID().toString()

                        val StorageRef = FirebaseStorage.getInstance().getReference("postImage/$randomID")
                        StorageRef.putFile(PostUri!!).addOnSuccessListener {



                        }.addOnFailureListener {



                        }.await()

                        val descriptionn : String = editText_scientist.text.toString()
                        val question :String = question1_scientist.text.toString()

                        val post = Post(randomID,question,descriptionn ,System.currentTimeMillis().toLong(), scientist_name!!  )
                        Authobj.farmer_or_scientist = true

                        db_post.document().set(post)
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            else{

                Toast.makeText(context , "Plz enter the description" , Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }

}