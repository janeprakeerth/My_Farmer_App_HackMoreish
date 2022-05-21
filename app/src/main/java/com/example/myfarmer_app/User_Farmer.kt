package com.example.myfarmer_app

import android.provider.ContactsContract

class User_Farmer {
    var name:String?=null
    var email:String?=null
    var uid:String?=null
    constructor(){}
    constructor(name:String?,email:String?,uid:String?){
        this.name=name
        this.email = email
        this.uid = uid
    }
}