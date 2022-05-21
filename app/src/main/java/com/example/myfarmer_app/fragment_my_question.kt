package com.example.myfarmer_app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfarmer_app.Authobj.auth
import com.example.myfarmer_app.Authobj.db_scientist
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class fragment_my_question: Fragment(R.layout.fragment_my_question) {
    private lateinit var scientistlistrecyclerview:RecyclerView
    private lateinit var scientistlayoutmanager:RecyclerView.LayoutManager
    private lateinit var scientistchatadapter:farmer_chat_adapter
    val scientistlist  = arrayListOf<scientist_registration_details>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View? = inflater.inflate(R.layout.fragment_my_question , container , false)
        val questionanswers:FloatingActionButton = view!!.findViewById(R.id.questionanswers)
        questionanswers.setOnClickListener {
            val intent = Intent(context,questionansweractivity::class.java)
            startActivity(intent)
        }
        try {
        scientistlistrecyclerview = view?.findViewById(R.id.rv_farmer_chat)!!
        scientistlayoutmanager = LinearLayoutManager(context)
            scientistchatadapter = farmer_chat_adapter()
            db_scientist.get().addOnSuccessListener {
                if(!it.isEmpty()){
                    val scientists = it.documents
                    for(i in scientists){
                        if(i.id == auth.currentUser?.uid){

                        }else{
                            val scientist = i.toObject<scientist_registration_details>()
                            scientistlist.add(scientist!!)
                        }
                    }
                }
                scientistchatadapter.update_list(scientistlist)
                scientistlist.clear()
            }
            scientistlistrecyclerview.adapter = scientistchatadapter
            scientistlistrecyclerview.layoutManager = scientistlayoutmanager
            scientistlistrecyclerview.addItemDecoration(DividerItemDecoration(scientistlistrecyclerview.context,(scientistlistrecyclerview.layoutManager as LinearLayoutManager).orientation))

        }catch (e:Exception){
            Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
        }
        return view
    }
}