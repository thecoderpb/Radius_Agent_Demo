package com.assignment.radiusagentdemo.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// API: https://my-json-server.typicode.com/iranjith4/ad-assignment/db
private const val BASE_URL = "https://my-json-server.typicode.com"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(
    BASE_URL
).build()

interface FacilityAPIService{

    @GET("/iranjith4/ad-assignment/db")
    fun getDataFromAPI(): Call<ResponseData>

}

object Api {
    val retrofitService: FacilityAPIService by lazy{ retrofit.create(FacilityAPIService::class.java)}
}