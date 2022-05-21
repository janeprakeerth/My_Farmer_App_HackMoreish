package com.example.myfarmer_app
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
import com.example.myfarmer_app.Authobj.db_chat
import com.example.myfarmer_app.Authobj.db_farmer
import com.example.myfarmer_app.Authobj.farmerId
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class fragment_signup_tab_farmer:Fragment(R.layout.sign_up_tab_fragment_farmer) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View? = inflater.inflate(R.layout.sign_up_tab_fragment_farmer , container , false)
        val et_email_register:EditText?= view?.findViewById(R.id.email_register_farmer)
        val et_mobile_numebr:EditText? = view?.findViewById(R.id.farmermobilenumber)
        val et_password:EditText? = view?.findViewById(R.id.password_farmer_register)
        val et_confirmpassword:EditText? = view?.findViewById(R.id.confirmpassword_farmer_register)
        val et_name:EditText? = view?.findViewById(R.id.name_farmer_register)
        val signup:Button? = view?.findViewById(R.id.SignUp_farmer_register)
        //val progressBar: ProgressBar? = view?.findViewById(R.id.progressBar)
        val progress_bar_farmer : ProgressBar = view!!.findViewById(R.id.progress_bar_signup_farmer)
        signup?.setOnClickListener {
            val emailid = et_email_register?.text?.toString()
            val mobile_number = et_mobile_numebr?.text?.toString()
            val password = et_password?.text?.toString()
            val confirmpassword = et_confirmpassword?.text?.toString()
            val name = et_name?.text?.toString()
            if(emailid!!.isNotEmpty()&&password!!.isNotEmpty()&&confirmpassword!!.isNotEmpty()){

                if(password==confirmpassword) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try{
                            withContext(Dispatchers.Main)
                            {
                                progress_bar_farmer?.visibility = View.VISIBLE
                            }
                            auth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener { task->
                                if(task.isSuccessful){
                                    val currentUser = auth.currentUser
                                    farmerId = currentUser?.uid
                                    val farmer = farmer_registration_details(emailid,mobile_number!!,password,confirmpassword,name!! ,
                                        farmerId.toString())
                                    val documentrefernce: DocumentReference = db_farmer.document(farmerId!!)
                                    documentrefernce.set(farmer).addOnCompleteListener {
                                        Toast.makeText(context,"Successfully added user name",Toast.LENGTH_SHORT).show()


                                            progress_bar_farmer?.visibility = View.INVISIBLE

                                        //progress_bar_farmer?.visibility = View.VISIBLE
                                    }
//                                    try {
//                                        val documentReference1: DocumentReference =
//                                            db_chat.document(farmerId!!)
//                                        documentReference1.collection("scientist_list")
//                                    }catch (e:Exception){
//                                        Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
//                                    }
                                }
                            }
                        }catch (e:Exception){
                            withContext(Dispatchers.Main){
                                Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }else{
                    Toast.makeText(context,"Password and confirm password must be same",Toast.LENGTH_SHORT).show()
                }
            }

        }
        return view
    }
}