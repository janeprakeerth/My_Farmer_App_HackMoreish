package com.example.myfarmer_app

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfarmer_app.Authobj.db_farmer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class questionansweradapter(var questionanswerlist:ArrayList<MyanswerModel>): RecyclerView.Adapter<questionansweradapter.questionanswerviewholder>() {
    class questionanswerviewholder(view: View): RecyclerView.ViewHolder(view){
        val farmername:TextView= view.findViewById<TextView>(R.id.tv_question)
        val cardview:CardView = view.findViewById(R.id.cv_questionanswer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): questionanswerviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_question_answer , parent , false )
        return questionanswerviewholder(view)
    }

    override fun onBindViewHolder(holder: questionanswerviewholder, position: Int) {
        var questionanswer = questionanswerlist[position]
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var querySnapshot =
                    db_farmer.whereEqualTo("uid", questionanswer.farmerId).get().await()
                var current_user_object: farmer_registration_details? = null
                for (document in querySnapshot.documents) {
                    current_user_object = document.toObject<farmer_registration_details>()
                    withContext(Dispatchers.Main) {
                        holder.farmername.text = current_user_object?.name.toString()
                    }
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(holder.itemView.context,e.message,Toast.LENGTH_LONG).show()
                }
            }
        }
        holder.cardview.setOnClickListener {
            val intent = Intent(holder.itemView.context,scientistanswertofarmeractivity::class.java)
            intent.putExtra("question",questionanswer.question)
            intent.putExtra("answer",questionanswer.answer)
//            Toast.makeText(holder.itemView.context,questionanswer.question,Toast.LENGTH_LONG).show()
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return questionanswerlist.size
    }


}