package com.example.myfarmer_app

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

class UserFarmerDao {

    fun getuserbyID(uid : String) : Task<DocumentSnapshot> {

        return Authobj.db_farmer.document(uid).get()
    }

}