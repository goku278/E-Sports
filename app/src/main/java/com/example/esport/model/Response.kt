package com.example.esport.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Response(

    @SerializedName("venue_id") var venueId: Int? = null,
    @SerializedName("venture_id") var ventureId: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("icon") var icon: String? = null,
    @SerializedName("categories") var categories: ArrayList<Categories> = arrayListOf()

) : Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }
}