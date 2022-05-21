package com.example.myfarmer_app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myfarmer_app.Authobj.auth
import com.example.myfarmer_app.Authobj.farmer_or_scientist
import com.example.myfarmer_app.Authobj.name
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class fragmen_login_tab_farmer : Fragment(R.layout.login_tab_fragment_farmer) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = inflater.inflate(R.layout.login_tab_fragment_farmer, container, false)
        val et_emailid: EditText? = view?.findViewById(R.id.email_farmer)
        val et_password: EditText? = view?.findViewById(R.id.password)
        val progress_bar : ProgressBar? = view?.findViewById(R.id.progress_bar_login_farmer)
        val login_button: Button? = view?.findViewById(R.id.login_farmer)
        login_button?.setOnClickListener {
            val emailid: String = et_emailid?.text.toString()
            val password: String = et_password?.text.toString()
            progress_bar?.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(emailid, password).await()
                    val current_user = auth.currentUser?.uid
                    try {
                        var querySnapshot =
                            Authobj.db_farmer.whereEqualTo("uid", current_user).get().await()
                        var current_user_object: farmer_registration_details? = null
                        for (document in querySnapshot.documents) {
                            current_user_object = document.toObject<farmer_registration_details>()
                        }
                        withContext(Dispatchers.Main) {
                            name = current_user_object?.name.toString()
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    withContext(Dispatchers.Main)
                    {
                        progress_bar?.visibility = View.INVISIBLE
                    }
                    val intent = Intent(context, Farmer_Main_Interface::class.java)
                    startActivity(intent)
                }catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return view
    }
}