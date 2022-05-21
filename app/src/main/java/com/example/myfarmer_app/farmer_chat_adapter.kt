package com.example.myfarmer_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfarmer_app.Authobj.auth

class farmer_chat_adapter:
    RecyclerView.Adapter<farmer_chat_adapter.scientistlistviewholder>() {
    val scientistlist = arrayListOf<scientist_registration_details>()
    class scientistlistviewholder(view:View):RecyclerView.ViewHolder(view) {
        val name:TextView = view.findViewById(R.id.tv_scientist_name_each_chat)
        val specialization:TextView = view.findViewById(R.id.tv_scientist_specialization)
        val cv_chat:CardView = view.findViewById(R.id.cv_chat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): scientistlistviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_farmer_chat_layout,parent,false)
        return scientistlistviewholder(view)
    }

    override fun onBindViewHolder(holder: scientistlistviewholder, position: Int) {
        val scientist = scientistlist[position]
        holder.name.text = scientist.name.toString()
        holder.specialization.text = scientist.specialization.toString()
        holder.cv_chat.setOnClickListener {
            val intent  = Intent(holder.itemView.context,questionactivity::class.java)
            val farmerid = auth.currentUser?.uid.toString()
            intent.putExtra("scientistid",scientist.uid)
            intent.putExtra("farmerid",farmerid)
            holder.itemView.context.startActivity(intent)
        }
    }
    fun update_list(scientist_list: ArrayList<scientist_registration_details>?){
        scientistlist.clear()
        if (scientist_list != null) {
            scientistlist.addAll(scientist_list)
        }
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return scientistlist.size
    }


}