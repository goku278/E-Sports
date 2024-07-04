package com.example.esport.ui.home

import com.example.esport.model.Response
import com.example.esport.model.SlotResponse
import com.example.esport.model.Turfs

interface TurfsListener {
    fun onTurfClicked(response: Response?) {}
    fun onCategoriesClicked(turf: Turfs) {}
    fun onSlotResponseClicked(slotResponse: SlotResponse) {}
    fun onNotificationMessageClicked(slotResponse: SlotResponse) {}
}