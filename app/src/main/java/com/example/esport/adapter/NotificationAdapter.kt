package com.example.esport.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.esport.R
import com.example.esport.model.SlotResponse
import com.example.esport.ui.home.TurfsListener
import com.example.esport.util.SharedPreferencesHelper

class NotificationAdapter(
    private val notifications: List<SlotResponse>,
    private val context: Context,
    private val clickListener: TurfsListener,
) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStartTime: TextView = itemView.findViewById(R.id.tvStartTime)
        val tvEndTime: TextView = itemView.findViewById(R.id.tvEndTime)
        val tvBasePrice: TextView = itemView.findViewById(R.id.tvBasePrice)
        val tvAvailability: TextView = itemView.findViewById(R.id.tvAvailability)
        val status: TextView = itemView.findViewById(R.id.tvStatus)
        val llParent5: LinearLayout = itemView.findViewById(R.id.llParent5)

        private val sharedPreferences = SharedPreferencesHelper(context)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val category = notifications[position]
                    category.turfId = notifications[position].turfId
                    clickListener.onNotificationMessageClicked(category)
                }
                sharedPreferences.saveNotificationCount(notifications.size)
            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        holder.tvStartTime.text = notification.startTime
        holder.tvEndTime.text = notification.endTime
        holder.tvBasePrice.text = notification.basePrice
        holder.tvAvailability.text =
            if (notification.available == true) "Available" else "Not Available"
        if (notification.message != null && !notification.message!!.contains(
                "confirmed",
                true
            ) || notification.available != true
        ) {
            holder.llParent5.background =
                ContextCompat.getDrawable(context, R.drawable.notification_cancelled_background)
        } else {
            holder.llParent5.background =
                ContextCompat.getDrawable(context, R.drawable.notification_confirmed)
        }
        holder.status.text = notification.message
    }

    override fun getItemCount(): Int {
        return notifications.size
    }
}
