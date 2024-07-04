package com.example.esport.model

import com.google.gson.annotations.SerializedName

data class BookSlotRequestSuccessResponse(

    @SerializedName("order_id") var orderId: String? = null

)
