package com.example.myfarmer_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommentAdapter:RecyclerView.Adapter<CommentAdapter.commentviewholder>() {
    var comments_user  = arrayListOf(hashMapOf(
        "name" to "",
        "comment" to ""
    ))
    class commentviewholder(view: View):RecyclerView.ViewHolder(view){
        var name :TextView= view.findViewById(R.id.tv_name_comment)
        var comment :TextView = view.findViewById(R.id.comment_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): commentviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_comment_item , parent , false )
        val holder = commentviewholder(view)
        return holder
    }

    override fun onBindViewHolder(holder: commentviewholder, position: Int) {
        val current_comment = comments_user[position]
        holder.comment.text = current_comment["comment"].toString()
        holder.name.text = current_comment["name"].toString()
    }
    fun update_list(comments_list: ArrayList<HashMap<String, String>>?){
        comments_user.clear()
        if (comments_list != null) {
            comments_user.addAll(comments_list)
        }
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return comments_user.size
    }
}