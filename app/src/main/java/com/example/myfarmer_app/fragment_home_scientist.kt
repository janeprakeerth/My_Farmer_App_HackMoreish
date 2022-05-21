package com.example.myfarmer_app

import android.app.DownloadManager
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfarmer_app.Authobj.db_post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class fragment_home_scientist: Fragment(R.layout.fragment_home_scientist) , IPostAdapter  {
    var adater : PostAdapter?  = null
    val city:String = "Chennai"
    val API:String = "8b54022afcf9e3a3181ad2f08fadfe4b"
    lateinit var tv_weather_mode_scientist: TextView
    lateinit var  tv_temperature_scientist: TextView
    lateinit var tv_min_temperature_scientist: TextView
    lateinit var tv_max_temperature_scientist: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View? = inflater.inflate(R.layout.fragment_home_scientist, container , false)
        val recyclerviewPost : RecyclerView = view!!.findViewById(R.id.rv_post_scientist)



        val query = db_post.orderBy("id" , Query.Direction.DESCENDING)

        val recyclervireoptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query , Post::class.java).build()

        adater = PostAdapter(recyclervireoptions , this)

        recyclerviewPost.adapter = adater
        recyclerviewPost.layoutManager = LinearLayoutManager(context)
        tv_weather_mode_scientist = view!!.findViewById(R.id.weather_mode_scientist)
        tv_temperature_scientist= view.findViewById(R.id.temperature_scientist)
        tv_min_temperature_scientist= view.findViewById(R.id.min_temperature_scientist)
        tv_max_temperature_scientist= view.findViewById(R.id.max_temperature_scientist)
        weatherTask().execute()
        return view
    }
    inner class weatherTask() : AsyncTask<String, Void, String>(){
        override fun onPreExecute() {
            super.onPreExecute()


        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&appid=$API").readText(Charsets.UTF_8)
            }catch (e:Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            try{
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val temp = main.getString("temp")
                val temp_min = main.getString("temp_min")
                val temp_max = main.getString("temp_max")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val description =weather.getString("description")
                tv_weather_mode_scientist.text = description
                tv_max_temperature_scientist.text = "Max Temp :$temp_max°C"
                tv_min_temperature_scientist.text = "Min Temp :$temp_min°C"
                tv_temperature_scientist.text = "Temp :$temp°C"
            }catch (e:Exception){
                Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        adater?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adater?.stopListening()
    }

    override fun onLikeClicked(postId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val post  = db_post.document(postId).get().await().toObject(Post::class.java)!!
                val isLiked = post.likedby.contains(Authobj.auth.currentUser!!.uid.toString())


                if(isLiked)
                {
                    post.likedby.remove(Authobj.auth.currentUser!!.uid)
                }
                else{
                    post.likedby.add(Authobj.auth.currentUser!!.uid)
                }

                db_post.document(postId).set(post)

            }catch (e:Exception)
            {   withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
            }
        }

    }
}