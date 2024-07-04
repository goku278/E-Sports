package com.example.esport.model

import com.google.gson.annotations.SerializedName

data class Turfs(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("category_id") var categoryId: Int? = null

)