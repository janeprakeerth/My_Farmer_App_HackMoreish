package com.example.myfarmer_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfarmer_app.Authobj.auth
import com.example.myfarmer_app.Authobj.db_scientist
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.ArrayList

class fragment_my_question_scientist: Fragment(R.layout.fragment_myquestion_scientist) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            val view: View? =
                inflater.inflate(R.layout.fragment_myquestion_scientist, container, false)
            val scientistid = auth.currentUser?.uid
            var questionlist: ArrayList<MyquestionModel>
            var rv = view?.findViewById<RecyclerView>(R.id.rv_farmer_question_to_scientist)
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    var querySnapshot = db_scientist.whereEqualTo("uid", scientistid).get().await()
                    var current_user_object: scientist_registration_details? = null
                    for (document in querySnapshot.documents) {
                        current_user_object = document.toObject<scientist_registration_details>()
                        questionlist = current_user_object?.questionbyfarmers!!
                        withContext(Dispatchers.Main) {
                            var adapter = fragment_my_question_scientist_adapter(questionlist)
                            rv?.layoutManager = LinearLayoutManager(context)
                            rv?.adapter = adapter
                        }
                    }
                }
            }catch (e:Exception){
                Toast.makeText(context,e.message.toString(),Toast.LENGTH_LONG).show()
            }
        return view
    }

    }
