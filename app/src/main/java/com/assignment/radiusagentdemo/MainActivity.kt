package com.assignment.radiusagentdemo

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.view.get

import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.assignment.radiusagentdemo.api.Api
import com.assignment.radiusagentdemo.api.ResponseData
import com.assignment.radiusagentdemo.databinding.ActivityMainBinding
import com.assignment.radiusagentdemo.fragments.FirstFragment
import com.assignment.radiusagentdemo.fragments.SecondFragment
import com.assignment.radiusagentdemo.fragments.ThirdFragment
import com.assignment.radiusagentdemo.room.ExclusionItems
import com.assignment.radiusagentdemo.room.FacilityDatabase
import com.assignment.radiusagentdemo.room.FacilityItems
import com.assignment.radiusagentdemo.room.Repository
import com.assignment.radiusagentdemo.ui.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var facilityViewModel: FacilityViewModel
    lateinit var exclusionsViewModel: ExclusionsViewModel
    lateinit var pref : SharedPreferences

    private val PREF_24Hr : Long = 86400000
    private val PREF_NAME : String = "com.assignment.radiusagentdemo"
    private val PREF_SAVED_TIME : String = "saved_time"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewPager = binding.viewPager
        viewPager.adapter = ViewPageAdapter(supportFragmentManager)
        val nxtButton : Button = findViewById(R.id.nextBtn)

        nxtButton.setOnClickListener(){
            var cnt = viewPager.currentItem
            cnt++
            viewPager.setCurrentItem(cnt,false)
            if(cnt == 2){
                nxtButton.text = "Done"
            }
            else if(cnt > 2){
                Toast.makeText(this,"Thanks for taking the survey. Your selections are saved",Toast.LENGTH_SHORT).show()
                Toast.makeText(this,"Property Type:" + FirstFragment.optionSelectedName + "\nRooms: " + SecondFragment.optionSelectedName+"\nOther amenities:"+ThirdFragment.optionSelectedName,Toast.LENGTH_LONG).show()
                onDestroy()
                exitProcess(0)
            }

        }



        pref = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val facilityItems = Repository(FacilityDatabase.invoke(this))

        val facilityViewModelFactory = FacilityViewModelFactory(facilityItems)
        val exclusionsViewModelFactory = ExclusionsViewModelFactory(facilityItems)

        facilityViewModel = ViewModelProvider(this@MainActivity, facilityViewModelFactory)[FacilityViewModel::class.java]
        exclusionsViewModel = ViewModelProvider(this@MainActivity, exclusionsViewModelFactory)[ExclusionsViewModel::class.java]

        facilityViewModel.allFacilityItems().observe(this){
            Log.i("Item count updated to", it.toString())
        }

        getDataFromAPI()
        //checkForNewData()

    }

    private fun checkForNewData(){
        val currentTime : Long
        if(pref.contains(PREF_SAVED_TIME)){
            currentTime = pref.getLong(PREF_SAVED_TIME,-1L)
            if(currentTime == -1L){
                pref.edit().putLong(PREF_SAVED_TIME,System.currentTimeMillis()).apply()
                getDataFromAPI()
            }

            if(System.currentTimeMillis() - currentTime > PREF_24Hr)
                getDataFromAPI()
        }else{
            pref.edit().putLong(PREF_SAVED_TIME,System.currentTimeMillis()).apply()
        }
    }

    private fun getDataFromAPI(){

        Api.retrofitService.getDataFromAPI().enqueue(object: Callback<ResponseData> {

            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                if(response.isSuccessful){

                    facilityViewModel.nukeTable()

                    Log.d("API Content", response.body().toString())
                    val facilitiesCount = response.body()?.facilities!!.size
                    val exclusionsCount = response.body()?.exclusions!!.size

                    for(i in 0 until facilitiesCount){
                        val facilityId : String = response.body()?.facilities!![i].facility_id!!
                        val facilityName : String = response.body()?.facilities!![i].name!!
                        val optionsCount = response.body()?.facilities!![i].options!!.size
                        for( j in 0 until optionsCount){
                            val optionName : String = response.body()?.facilities!![i].options!![j].name!!
                            val optionIcon : String = response.body()?.facilities!![i].options!![j].icon!!
                            val optionId : String = response.body()?.facilities!![i].options!![j].id!!

                            val item = FacilityItems(facilityId,facilityName,optionName,optionIcon,optionId)
                            facilityViewModel.insert(item)
                        }
                    }
                    Log.d("Insterted F succesfully", "Success")
                    exclusionsViewModel.nukeTable()

                    for(i in 0 until exclusionsCount){
                        for(j in 0 until response.body()?.exclusions!![i].size){
                            val exclusionFacilityId : String = response.body()?.exclusions!![i][j].facility_id!!
                            val exclusionOptionId : String = response.body()?.exclusions!![i][j].options_id!!

                            val item = ExclusionItems(exclusionFacilityId,exclusionOptionId)
                            exclusionsViewModel.insert(item)
                        }
                    }
                    Log.d("Insterted E succesfully", "Success")
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
                Log.d("Failed Fetching content",t.message!!)

            }
        })
    }
}