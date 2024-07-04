package com.example.esport.model

import com.google.gson.annotations.SerializedName

data class SlotResponse(
    @SerializedName("start_time") var startTime: String? = null,
    @SerializedName("end_time") var endTime: String? = null,
    @SerializedName("base_price") var basePrice: String? = null,
    @SerializedName("available") var available: Boolean? = null,
    var turfId: Int? = null,
    var message: String? = null,
    var status: String? = null
)