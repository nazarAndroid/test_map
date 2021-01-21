package com.example.android.testapp.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.testapp.common.Api
import com.example.android.testapp.common.Event
import com.example.android.testapp.common.NetworkService
import com.example.android.testapp.model.LocationApiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationDataRepository {
    var api: Api = NetworkService.retrofitService()
    private val locationDataLiveData = MutableLiveData<Event<LocationApiModel?>>()

    fun loadLocation() {
        locationDataLiveData.postValue(Event.loading())

        api.getLocation().enqueue(object : Callback<LocationApiModel> {
            override fun onFailure(call: Call<LocationApiModel?>, t: Throwable) {
                locationDataLiveData.postValue(Event.error(Error(t)))
            }

            override fun onResponse(call: Call<LocationApiModel?>, response: Response<LocationApiModel>) {
                locationDataLiveData.postValue(Event.success(response.body()))
            }
        })
    }
    fun locationData(): LiveData<Event<LocationApiModel?>> = locationDataLiveData

}