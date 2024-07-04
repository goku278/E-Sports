package com.example.esport.model

import com.google.gson.annotations.SerializedName

data class Response(

    @SerializedName("venue_id") var venueId: Int? = null,
    @SerializedName("venture_id") var ventureId: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("icon") var icon: String? = null,
    @SerializedName("categories") var categories: ArrayList<Categories> = arrayListOf()

)