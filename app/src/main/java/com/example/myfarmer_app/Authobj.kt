package com.example.myfarmer_app

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore

import com.google.firebase.ktx.Firebase

object Authobj {
    var auth:FirebaseAuth = FirebaseAuth.getInstance()
    var db_farmer = Firebase.firestore.collection("Farmers")
    var db_scientist = Firebase.firestore.collection("Scientists")
    var farmerId:String?=null
    var scientistId:String?=null
    var db_post = Firebase.firestore.collection("posts")
    var db_chat = Firebase.firestore.collection("chats")
    var farmer_or_scientist = false
    var farmer_name:String = ""
    var farmer_uid:String = ""
    var farmer_email:String = ""
    var name :String = ""
    var comments:ArrayList<each_comment_details>?=null
    var document :DocumentSnapshot ?= null
    var postid:String?=null
}