package com.assignment.radiusagentdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.assignment.radiusagentdemo.api.Api
import com.assignment.radiusagentdemo.api.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getDataFromAPI()

    }

    private fun getDataFromAPI(){

        Api.retrofitService.getDataFromAPI().enqueue(object: Callback<ResponseData> {

            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                if(response.isSuccessful){

                    Log.d("API Content", response.body().toString())

                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
                Log.d("Failed Fetching content","null")

            }
        })
    }
}