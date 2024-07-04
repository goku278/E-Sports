package com.example.esport.model

import com.google.gson.annotations.SerializedName

data class SlotRequest(

    @SerializedName("turf_id") var turfId: Int? = null,
    @SerializedName("start_time") var startTime: String? = null,
    @SerializedName("end_time") var endTime: String? = null

)