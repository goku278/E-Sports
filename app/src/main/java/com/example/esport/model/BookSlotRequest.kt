package com.example.esport.model

import com.example.esport.model.dto.SlotDto
import com.google.gson.annotations.SerializedName

data class BookSlotRequest(

    @SerializedName("slots") var slots: ArrayList<SlotRequest> = arrayListOf(),
    @SerializedName("user_first_name") var userFirstName: String? = null,
    @SerializedName("user_last_name") var userLastName: String? = null,
    @SerializedName("user_email") var userEmail: String? = null,
    @SerializedName("user_dial_code") var userDialCode: String? = null,
    @SerializedName("user_mobile_no") var userMobileNo: String? = null

)
