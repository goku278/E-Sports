package com.example.esport.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.esport.R
import com.example.esport.model.SlotResponse
import com.example.esport.model.Turfs
import com.example.esport.ui.home.TurfsListener
import org.w3c.dom.Text

class AvailableSlotsAdapter(
    private val context: Context,
    private val slotResponse: List<SlotResponse>,
    private val clickListener: TurfsListener,
    private val turfId: Int?
) : RecyclerView.Adapter<AvailableSlotsAdapter.ResponseViewHolder>() {

    // ViewHolder class
    inner class ResponseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvStartTime: TextView = itemView.findViewById(R.id.tvStartTime)
        private val tvEndTime: TextView = itemView.findViewById(R.id.tvEndTime)
        private val tvBookingFees: TextView = itemView.findViewById(R.id.tvBookingFees)
        private val tvAvailability: TextView = itemView.findViewById(R.id.tvAvailability)
        private val llParent2: LinearLayout = itemView.findViewById(R.id.llParent2)

        private var cardBackground = mutableListOf<Drawable>()

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val category = slotResponse[position]
                    category.turfId = turfId
                    clickListener.onSlotResponseClicked(category)
                }
            }
        }
        // Method to bind the data from a Response object to the views
        fun bind(slotResponse: SlotResponse, position: Int) {
            tvStartTime.text = "Start Time: ${slotResponse.startTime}"
            tvEndTime.text = "End Time: ${slotResponse.endTime}"
            tvBookingFees.text = "Booking Fees: ${slotResponse.basePrice}"
            val availability = if (slotResponse.available.toString().equals("true", ignoreCase = true)) {
                "Slot Available"
            } else {
                "Not Available"
            }
            tvAvailability.text = "Availability: $availability"
            if (slotResponse.available.toString().equals("true", true)) {
                llParent2.background = ContextCompat.getDrawable(context, R.drawable.green_background)
            }
            else {
                if (availability.equals("Not Available", true) || !slotResponse.available.toString().equals("true", true)) {
                    llParent2.background =
                        ContextCompat.getDrawable(context, R.drawable.red_background)
                }
            }
        }
    }

    // Inflates the item layout and creates the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.slot_booking_layout, parent, false)
        return ResponseViewHolder(view)
    }

    // Binds data to the ViewHolder
    override fun onBindViewHolder(holder: ResponseViewHolder, position: Int) {
        holder.bind(slotResponse[position], position)
    }

    // Returns the size of the list
    override fun getItemCount(): Int {
        return slotResponse.size
    }
}