package com.example.esport.model

import com.google.gson.annotations.SerializedName

data class Categories(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("venue_property_description") var venuePropertyDescription: String? = null,
    @SerializedName("turfs") var turfs: ArrayList<Turfs> = arrayListOf()

)