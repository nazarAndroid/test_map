package com.example.android.testapp.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.android.testapp.common.Event
import com.example.android.testapp.model.LocationApiModel


class LocationViewModel : ViewModel() {
    private val repository: LocationDataRepository =
        LocationDataRepository()
    fun loadLocation(){
        repository.loadLocation()
    }
    fun locationData(): LiveData<Event<LocationApiModel?>> = repository.locationData()
}