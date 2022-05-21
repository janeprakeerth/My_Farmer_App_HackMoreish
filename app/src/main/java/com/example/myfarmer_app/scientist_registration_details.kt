package com.example.myfarmer_app

data class scientist_registration_details(val email:String="",val mobileNumber:String="",val password:String="",val confirmPassword:String="",val name:String="",val uid : String = "",val questionbyfarmers:ArrayList<MyquestionModel>?=null,val specialization:String = "")
