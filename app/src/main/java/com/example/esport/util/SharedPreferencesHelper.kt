package com.example.esport.util

import android.content.Context
import android.content.SharedPreferences
import com.example.esport.model.SlotResponse
import com.google.gson.Gson

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        const val SLOT_RESPONSE_KEY = "SlotResponse_"
        const val NOTIFICATION_COUNT = "notification_count"
        const val NOTIFICATION_TOTAL = "notification_total"
    }

    fun saveNotificationCount(notificationCount: Int?) {
        val editor = sharedPreferences.edit()
        if (notificationCount != null) {
            editor.putInt(NOTIFICATION_COUNT, notificationCount)
        }
        editor.apply()
    }

    fun getNotificationCount(): Int? {
        return sharedPreferences.getInt(NOTIFICATION_COUNT,0)
    }

    fun saveNotificationTotal(notificationTotal: Int?) {
        val editor = sharedPreferences.edit()
        if (notificationTotal != null) {
            editor.putInt(NOTIFICATION_TOTAL, notificationTotal)
        }
        editor.apply()
    }

    fun getNotificationTotal(): Int? {
        return sharedPreferences.getInt(NOTIFICATION_TOTAL, 0)
    }

    fun saveSlotResponse(slotResponse: SlotResponse) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(slotResponse)
        slotResponse.turfId?.let {
            editor.putString("$SLOT_RESPONSE_KEY$it", json)
        }
        editor.apply()
    }

    fun getSlotResponse(turfId: Int): SlotResponse? {
        val json = sharedPreferences.getString("$SLOT_RESPONSE_KEY$turfId", null)
        return if (json != null) {
            gson.fromJson(json, SlotResponse::class.java)
        } else {
            null
        }
    }

    fun removeSlotResponse(turfId: Int) {
        val editor = sharedPreferences.edit()
        editor.remove("$SLOT_RESPONSE_KEY$turfId")
        editor.apply()
    }

    fun clearAllSlotResponses() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}