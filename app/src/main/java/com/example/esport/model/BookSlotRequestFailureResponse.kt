package com.example.esport.model

import com.google.gson.annotations.SerializedName

data class BookSlotRequestFailureResponse(

    @SerializedName("code") var code: Int? = null,
    @SerializedName("success") var success: Boolean? = null,
    @SerializedName("error") var error: String? = null,
    @SerializedName("response") var response: ArrayList<String> = arrayListOf(),
    @SerializedName("metadata") var metadata: ArrayList<String> = arrayListOf(),
    @SerializedName("requestLocation") var requestLocation: String? = null

)