package com.example.esport.model

import com.google.gson.annotations.SerializedName


data class BookSlotRequestResponse(
    @SerializedName("code") var code: Int? = null,
    @SerializedName("success") var success: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("response") var response: BookSlotRequestSuccessResponse? = BookSlotRequestSuccessResponse(),
    @SerializedName("metadata") var metadata: Metadata? = Metadata(),
    @SerializedName("requestLocation") var requestLocation: String? = null,
    @SerializedName("error") var error: String? = null,
    var turfId: Int? = null
)