package com.example.myfarmer_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfarmer_app.Authobj.document
import com.example.myfarmer_app.Authobj.name
import com.example.myfarmer_app.Authobj.postid
import com.example.myfarmer_app.databinding.ActivityCommentBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class Comment_Activity : AppCompatActivity() {
    var binding:ActivityCommentBinding?=null
    var adapter:CommentAdapter?=null
    val array:ArrayList<ArrayList<HashMap<String,String>>>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCommentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        binding?.rvComment?.layoutManager =LinearLayoutManager(this)
        binding?.ivSend?.setOnClickListener {
            if(binding?.addComment?.text!!.isNotEmpty()){
                val comment_text = hashMapOf(
                    "name" to name,
                    "comment" to binding?.addComment?.text?.toString()
                )
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        FirebaseFirestore.getInstance().collection("posts").document("$postid")
                            .update("comments", FieldValue.arrayUnion(comment_text)).await()
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@Comment_Activity,"Comment Updated",Toast.LENGTH_SHORT).show()
                            fetch_data()
                        }

                    }catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Comment_Activity, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else{
                Toast.makeText(this,"Please type your comment",Toast.LENGTH_SHORT).show()
            }
        }
        adapter = CommentAdapter()
        try {
            fetch_data()
        }catch (e:Exception){
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }
        binding?.rvComment?.adapter = adapter
        binding?.rvComment?.addItemDecoration(DividerItemDecoration(binding?.rvComment?.context,(binding?.rvComment?.layoutManager as LinearLayoutManager).orientation))
    }
    fun fetch_data()
    {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val current_user = Authobj.auth.currentUser?.uid
                    var querySnapshot = Authobj.db_post.whereEqualTo("uid",current_user).get().await()
                    var current_user_object:Post?=null
                    withContext(Dispatchers.Main) {
                         FirebaseFirestore.getInstance().collection("posts").document("$postid")
                            .get().addOnSuccessListener {
                                current_user_object = it.toObject<Post>()
                                 array?.add(current_user_object?.comments!!)
                                 adapter?.update_list(current_user_object?.comments)
                             }
                    }
                }catch (e:Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@Comment_Activity,e.message,Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
}