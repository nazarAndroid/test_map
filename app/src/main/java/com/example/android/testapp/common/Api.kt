package com.example.android.testapp.common

import com.example.android.testapp.model.LocationApiModel
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("lat_lng.json")
    fun getLocation(): Call<LocationApiModel>
}