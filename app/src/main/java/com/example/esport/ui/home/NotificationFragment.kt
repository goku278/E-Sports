package com.example.esport.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esport.R
import com.example.esport.adapter.NotificationAdapter
import com.example.esport.model.SlotResponse
import com.example.esport.util.SharedPreferencesHelper

class NotificationFragment(
    private val viewModel: ESportsViewModel,
    private val context: Context,
    private val toolBarBack: ImageView?,
    private val toolBarTitle: TextView,
    private val tvNotificationCount: TextView
) : Fragment(), TurfsListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferencesHelper
    private lateinit var adapter: NotificationAdapter
    private var notificationList = mutableListOf<SlotResponse>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.notification_fragment, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewNotifications)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferences = SharedPreferencesHelper(requireContext())
        toolBarBack?.visibility = View.VISIBLE
        toolBarTitle.text = "Notification"
        setupRecyclerView()
        loadNotifications()
        setNotificationCount()
    }

    private fun setNotificationCount() {
        sharedPreferences.saveNotificationCount(0)
        tvNotificationCount.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        // Set up the RecyclerView with a LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Create the adapter and pass the notificationList and TurfsListener
        adapter = NotificationAdapter(notificationList, requireContext(), this)

        // Set the adapter for the RecyclerView
        recyclerView.adapter = adapter
    }

    private fun loadNotifications() {
        // Assuming you have a way to get all turfIds
        val turfIds = mutableListOf<Int>(1, 2, 3) // Replace with actual turfIds

        // Add more turfIds as needed
        for (i in 1..500) {
            turfIds.add(i)
        }

        // Retrieve SlotResponse for each turfId and add to notificationList
        for (turfId in turfIds) {
            sharedPreferences.getSlotResponse(turfId)?.let {
                notificationList.add(it)
            }
        }

        // Notify the adapter that data has changed
        adapter.notifyDataSetChanged()
    }

    override fun onNotificationMessageClicked(slotResponse: SlotResponse) {
        viewModel.cancelBookedSlot.observe(
            this,
        ) { cancelBookedSlot ->

            if (cancelBookedSlot?.code == 200) {
                slotResponse.status = "Successfully Cancelled"
            } else if (cancelBookedSlot?.code == 400) {
                slotResponse.status = cancelBookedSlot.error
                Toast.makeText(context, "${cancelBookedSlot.error}", Toast.LENGTH_SHORT).show()
            }
            sharedPreferences.saveSlotResponse(slotResponse)
        }
        viewModel.cancelBookedSlot(slotResponse)
    }
}
