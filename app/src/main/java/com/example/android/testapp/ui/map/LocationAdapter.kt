package com.example.android.testapp.ui.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.testapp.R
import com.example.android.testapp.model.Location

class LocationAdapter: RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    var allLocation = ArrayList<Location>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.card_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allLocation.size
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(allLocation[position])
    }

    class LocationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val id_loc:TextView = itemView.findViewById(R.id.id_location)
        val name_loc:TextView = itemView.findViewById(R.id.name_location)

        fun bind(location: Location){
            id_loc.text = location.id.toString()
            name_loc.text = location.name
        }
    }
    fun setLocationArray(getAllLocation : ArrayList<Location>){
        this.allLocation = getAllLocation
        notifyDataSetChanged()
    }
}