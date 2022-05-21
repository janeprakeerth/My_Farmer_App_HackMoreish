package com.example.myfarmer_app

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class fragment_my_question_scientist_adapter(var questionlist:ArrayList<MyquestionModel>):RecyclerView.Adapter<fragment_my_question_scientist_adapter.questionlistviewholder>() {
    class questionlistviewholder(view: View):RecyclerView.ViewHolder(view){
        val farmername:TextView = view.findViewById(R.id.tv_farmer_name_each_question)
        val card:CardView = view.findViewById(R.id.cv_chat_scientist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): questionlistviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_farmer_question,parent,false)
        return questionlistviewholder(view)
    }

    override fun onBindViewHolder(holder: questionlistviewholder, position: Int) {
        val question = questionlist[position]
        var current_user_object: farmer_registration_details? = null
        CoroutineScope(Dispatchers.IO).launch {

            var querySnapshot = Authobj.db_farmer.whereEqualTo("uid", question.farmerid).get().await()
            for (document in querySnapshot.documents) {
                current_user_object = document.toObject<farmer_registration_details>()
                withContext(Dispatchers.Main) {
                    holder.farmername.text = current_user_object?.name.toString()
                }
            }
        }
        holder.card.setOnClickListener {
            val intent = Intent(holder.itemView.context,answeractivity::class.java)
            intent.putExtra("question",question.question)
            intent.putExtra("imageid",question.image)
            intent.putExtra("farmerid",question.farmerid)
//            Toast.makeText(holder.itemView.context,question.farmerid,Toast.LENGTH_LONG).show()
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return questionlist.size
    }
}