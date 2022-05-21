package com.example.myfarmer_app

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.utils.Utils
import com.bumptech.glide.Glide
import com.example.myfarmer_app.Authobj.comments
import com.example.myfarmer_app.Authobj.document
import com.example.myfarmer_app.Authobj.farmer_or_scientist
import com.example.myfarmer_app.Authobj.postid
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.storage.FirebaseStorage


class PostAdapter(options: FirestoreRecyclerOptions<Post>, val listener: IPostAdapter) : FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(
    options
){

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val postText: TextView = itemView.findViewById(R.id.postTitle)
        val userText: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val likeCount: TextView = itemView.findViewById(R.id.likeCount)
        var userImage: ImageView = itemView.findViewById(R.id.userImage)
        val postImage : ImageView = itemView.findViewById(R.id.im_post)
        val likeButton: ImageView = itemView.findViewById(R.id.likeButton)
        val comment:ImageView = itemView.findViewById(R.id.comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder =  PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.each_layout_for_fragment, parent, false))
        viewHolder.likeButton.setOnClickListener{
            listener.onLikeClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        viewHolder.comment.setOnClickListener {
            val intent = Intent(viewHolder.itemView.context,Comment_Activity::class.java)
            document = snapshots.getSnapshot(viewHolder.adapterPosition)
            postid = snapshots.getSnapshot(viewHolder.adapterPosition).id
            viewHolder.itemView.context.startActivity(intent)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.postText.text = model.desciption
        holder.userText.text = model.createdBy
        holder.createdAt.text = model.questio_to_the_community
       // Glide.with(holder.userImage.context).load(model.createdBy.imageUrl).circleCrop().into(holder.userImage)
        holder.likeCount.text = model.likedby.size.toString()
        holder.userImage.setImageDrawable(ContextCompat.getDrawable(holder.userImage.context,R.drawable.person_sample))
        //holder.createdAt.text = Utils.getTimeAgo(model.createdAt)
        var URL_str :String = ""
        try {
            val storageRef = FirebaseStorage.getInstance().reference
            storageRef.child("postImage/${model.id}").downloadUrl.addOnSuccessListener {
                Glide.with(holder.itemView.context).load(it.toString()).into(holder.postImage)
            }.addOnFailureListener {
                Toast.makeText(holder.itemView.context , it.message , Toast.LENGTH_LONG).show()
            }
        }catch (e:Exception){
            Toast.makeText(holder.itemView.context,e.message, Toast.LENGTH_LONG).show()
        }
        val currentUserId = Authobj.auth.currentUser!!.uid
        val isLiked = model.likedby.contains(currentUserId)
        if(isLiked) {
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_liked))
        } else {
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_unliked))
        }

    }
}

interface IPostAdapter {
    fun onLikeClicked(postId: String)
}