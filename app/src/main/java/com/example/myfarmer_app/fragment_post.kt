package com.example.myfarmer_app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.myfarmer_app.Authobj.db_farmer
import com.example.myfarmer_app.Authobj.db_post
import com.example.myfarmer_app.Authobj.farmer_or_scientist
import com.google.firebase.storage.FirebaseStorage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class fragment_post: Fragment(R.layout.fragment_post) {
    var image_post:ImageView?=null
    var PostUri : Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK && requestCode== com.github.dhaval2404.imagepicker.ImagePicker.REQUEST_CODE) {
            image_post?.setImageURI(data?.data)
            PostUri = data?.data
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View? = inflater.inflate(R.layout.fragment_post, container, false)

        val btn_add_post: Button = view!!.findViewById(R.id.btn_add_post)
        val camera_button: Button = view.findViewById(R.id.btn_camera)
        val gallery_button: Button = view.findViewById(R.id.btn_gallery)
        val progress_bar_post : ProgressBar  = view.findViewById(R.id.progress_bar_post_farmer)
        image_post = view.findViewById(R.id.iv_post)
        camera_button.setOnClickListener {
            com.github.dhaval2404.imagepicker.ImagePicker.with(this).cameraOnly().crop().start()
        }
        gallery_button.setOnClickListener {
            com.github.dhaval2404.imagepicker.ImagePicker.with(this)
                .galleryMimeTypes(arrayOf("image/*")).crop().start()
        }


        btn_add_post.setOnClickListener {
            val currentUser = Authobj.auth.currentUser!!.uid
            val editText: EditText = view.findViewById(R.id.et_add_post_description)
            val question1: EditText = view.findViewById(R.id.et_add_a_question)
            progress_bar_post.visibility = View.VISIBLE
            if (editText.text.toString().isNotEmpty() && question1.text.toString().isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val documentSnapshot =
                            db_farmer.whereEqualTo("uid", currentUser).get().await()
                        var farmer: farmer_registration_details? = null
                        for (document in documentSnapshot.documents) {
                            farmer = document.toObject(farmer_registration_details::class.java)
                        }
                        val farmer_name = farmer?.name?.toString()
                        val randomID = UUID.randomUUID().toString()
                        val StorageRef =
                            FirebaseStorage.getInstance().getReference("postImage/$randomID")
                        StorageRef.putFile(PostUri!!).addOnSuccessListener {
                        }.addOnFailureListener {
                        }.await()
                        val descriptionn: String = editText.text.toString()
                        val question: String = question1.text.toString()
                        val post = Post(
                            randomID,
                            question,
                            descriptionn,
                            System.currentTimeMillis().toLong(),
                            farmer_name!!
                        )
                        farmer_or_scientist = false
                        db_post.document().set(post)

                        withContext(Dispatchers.Main)
                        {
                            progress_bar_post.visibility = View.INVISIBLE
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Plz enter the description", Toast.LENGTH_SHORT).show()
            }
        }



        return view

    }

}