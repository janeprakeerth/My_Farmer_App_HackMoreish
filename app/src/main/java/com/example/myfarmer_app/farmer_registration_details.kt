package com.example.myfarmer_app

data class farmer_registration_details(
    val email:String = "",
    val mobileNumber:String = "",
    val password:String = "",
    val confirmPassword:String = "",
    val name:String  = "", val uid: String = "",
    val myquestion: ArrayList<MyquestionModel>? = null,
    val scientistanswer:ArrayList<MyanswerModel>?=null
)
