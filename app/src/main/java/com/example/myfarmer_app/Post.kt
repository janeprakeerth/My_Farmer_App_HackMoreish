package com.example.myfarmer_app

data class Post(
    val id: String = "",
    val questio_to_the_community:String = "",
    val desciption: String   = "",
    val createdAt: Long  = 0L,
    val createdBy: String = "",
    val comments: ArrayList<HashMap<String, String>>? = null,
    val likedby: ArrayList<String> = ArrayList()
    )
