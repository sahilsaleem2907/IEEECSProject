package com.example.ieeecsproj1

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

   lateinit var editText :EditText
   fun getWeather(view: View)
   {
       if(editText.text.toString().isEmpty())
       {
           Toast.makeText(this,"Enter the city ",Toast.LENGTH_SHORT).show()
       }else {
           weatherTask().execute()
       }

   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         editText = findViewById<EditText>(R.id.cityEditText)


    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String? {
            var response:String?
            try{
                response = URL("https://openweathermap.org/data/2.5/weather?q="+editText.text.toString()+"&appid=439d4b804bc8187953eb36d2a8c26a02").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObj = JSONObject(result)
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val weatherDescription = weather.getString("description")

                findViewById<TextView>(R.id.description_text).text = "The Weather is "+weatherDescription.capitalize()
            }catch (e:Exception)
            {
                //If fails
                e.printStackTrace()
            }
        }

    }
    }
