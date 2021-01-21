package com.example.android.testapp.ui.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.testapp.R
import com.example.android.testapp.common.Status
import com.example.android.testapp.model.Location
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.activity_map_screen.*


class MapScreen : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private lateinit var viewModel: LocationViewModel
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    var adapter = LocationAdapter()

    private var mapView: MapView? = null
lateinit var mapbox: Mapbox
lateinit var mapboxMap: MapboxMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapbox = Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
        setContentView(R.layout.activity_map_screen)
        mapView = findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)


        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth!!.currentUser
        getEmail.text = currentUser?.email
        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        recycler_location.adapter = adapter
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("location")

        viewModel.locationData().observe(this, Observer { event ->
            when (event.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    showLocation(event.data?.places!!)
                }
                Status.ERROR -> {
                    event.error
                }
            }
        })
        viewModel.loadLocation()
    }

    fun showLocation(allLocation: ArrayList<Location>) {
        adapter.setLocationArray(allLocation)
        val position = CameraPosition.Builder()
            .target(LatLng(allLocation.get(1).lat, allLocation.get(1).lng))
            .zoom(13.0)
            .tilt(20.0)
            .build()
        mapView?.getMapAsync { mapboxMap ->
            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000)
            mapboxMap.setStyle(Style.MAPBOX_STREETS) { style->
                allLocation.forEach {
                    mapboxMap?.addMarker(MarkerOptions()
                        .position(LatLng(it.lat, it.lng))
                        .title(it.id.toString() + " " + it.name))
                }
            }
        }


    }
    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }



}