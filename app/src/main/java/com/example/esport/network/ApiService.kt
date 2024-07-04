package com.example.esport.network

import com.example.esport.model.BookSlotRequest
import com.example.esport.model.BookSlotRequestResponse
import com.example.esport.model.Slot
import com.example.esport.model.TurfList
import com.example.esport.model.dao.DeleteResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("vendor/venue/v1/turfs")
    fun fetchAllTurfs(@Header("X-API-Key") apiKey: String): Call<TurfList>

    @GET("vendor/venue/v1/slots/")
    fun fetchRelevantSlotsByTurfIdAndDate(
        @Query("turf_id") turfId: Int,
        @Query("date") date: String,
        @Header("X-API-Key") apiKey: String
    ): Call<Slot>

    @POST("vendor/venue/v1/bookslot")
    fun bookSlot(
        @Header("X-API-Key") apiKey: String,
        @Body request: BookSlotRequest
    ): Call<BookSlotRequestResponse>

    /*@DELETE("vendor/venue/v1/cancelbooking")
    fun cancelBooking(
        @Query("id") id: Int,
        @Header("X-API-Key") apiKey: String
    ): Call<DeleteResponse>*/

    @DELETE("vendor/venue/v1/cancelbooking")
    fun cancelBooking(
        @Header("X-API-Key") apiKey: String,
        @Query("id") id: Int
    ): Call<DeleteResponse>

}
