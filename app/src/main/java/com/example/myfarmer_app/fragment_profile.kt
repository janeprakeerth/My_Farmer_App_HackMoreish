package com.example.myfarmer_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myfarmer_app.Authobj.auth
import com.example.myfarmer_app.Authobj.db_farmer
import com.example.myfarmer_app.Authobj.farmer_email
import com.example.myfarmer_app.Authobj.farmer_name
import com.example.myfarmer_app.Authobj.farmer_uid
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class fragment_profile: Fragment(R.layout.fragment_profile) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View? = inflater.inflate(R.layout.fragment_profile , container , false)
        val emailid:TextView = view!!.findViewById(R.id.tv_email)
        val mobilenumber:TextView = view.findViewById(R.id.tv_phone)
        val name:TextView = view.findViewById(R.id.tv_name)

        val current_user = auth.currentUser?.uid
        CoroutineScope(Dispatchers.IO).launch{
                var querySnapshot = db_farmer.whereEqualTo("uid",current_user).get().await()
                var current_user_object:farmer_registration_details?=null
                for(document in querySnapshot.documents){
                    current_user_object =document.toObject<farmer_registration_details>()
                }
                withContext(Dispatchers.Main){
                    emailid.text =current_user_object?.email
                    mobilenumber.text= current_user_object?.mobileNumber
                    name.text = current_user_object?.name
                    farmer_name = name.toString()
                    farmer_uid = current_user_object?.uid.toString()
                    farmer_email = emailid.toString()
                }
        }
        return view
    }
}