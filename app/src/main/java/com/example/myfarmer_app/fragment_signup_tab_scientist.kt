package com.example.myfarmer_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myfarmer_app.Authobj.scientistId
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class fragment_signup_tab_scientist : Fragment(R.layout.sign_up_tab_fragment_scientist) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = inflater.inflate(R.layout.sign_up_tab_fragment_scientist, container, false)
        val et_email_register: EditText?= view?.findViewById(R.id.email_scientist_signup)
        val et_mobile_numebr: EditText? = view?.findViewById(R.id.scientistmobilenumber)
        val et_password: EditText? = view?.findViewById(R.id.password_signup_scientist)
        val et_confirmpassword: EditText? = view?.findViewById(R.id.confirmpassword_signup_scientist)
        val et_name: EditText? = view?.findViewById(R.id.name_signup_scientist)
        val et_specialization:EditText? = view?.findViewById(R.id.specialization_signup_scientist)
        val signup: Button? = view?.findViewById(R.id.SignUp_scientist)
        signup?.setOnClickListener {
            val emailid = et_email_register?.text?.toString()
            val mobile_number = et_mobile_numebr?.text?.toString()
            val password = et_password?.text?.toString()
            val confirmpassword = et_confirmpassword?.text?.toString()
            val name = et_name?.text?.toString()
            val specialization = et_specialization?.text?.toString()
            if(emailid!!.isNotEmpty()&&password!!.isNotEmpty()&&confirmpassword!!.isNotEmpty()){

                if(password==confirmpassword) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try{
                            Authobj.auth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener { task->
                                if(task.isSuccessful){
                                    val currentUser = Authobj.auth.currentUser
                                    scientistId = currentUser?.uid
                                    val scientist = scientist_registration_details(emailid,mobile_number!!,password,confirmpassword,name!!,
                                        scientistId.toString(),null,specialization!!)
                                    val documentrefernce: DocumentReference = Authobj.db_scientist.document(
                                        Authobj.scientistId!!)
                                    documentrefernce.set(scientist).addOnCompleteListener {
                                        Toast.makeText(context,"User Has Been Successfully created",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }catch (e: Exception){
                            withContext(Dispatchers.Main){
                                Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }else{
                    Toast.makeText(context,"Password and confirm password must be same", Toast.LENGTH_SHORT).show()
                }
            }

        }
        return view
    }
}