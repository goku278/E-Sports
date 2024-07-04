package com.example.esport.model

import com.google.gson.annotations.SerializedName

data class TurfList(

    @SerializedName("code") var code: Int? = null,
    @SerializedName("success") var success: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("response") var response: ArrayList<Response> = arrayListOf(),
    @SerializedName("metadata") var metadata: Metadata? = Metadata(),
    @SerializedName("requestLocation") var requestLocation: String? = null

)