package com.example.myfarmer_app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class fragment_login_tab_scientist : Fragment(R.layout.login_tab_fragment_scientist){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View? = inflater.inflate(R.layout.login_tab_fragment_scientist , container , false)
        val et_emailid: EditText? = view?.findViewById(R.id.email_scientist_login)
        val et_password: EditText? = view?.findViewById(R.id.password_scientist_login)
        val login_button: Button? = view?.findViewById(R.id.login_scientist)
        login_button?.setOnClickListener {
            val emailid:String = et_emailid?.text.toString()
            val password:String = et_password?.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    Authobj.auth.signInWithEmailAndPassword(emailid, password).await()
                    val intent = Intent(context, Scientist_Main_Interface::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return view
    }
}